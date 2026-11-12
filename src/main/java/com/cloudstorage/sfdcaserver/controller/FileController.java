package com.cloudstorage.sfdcaserver.controller;

import com.cloudstorage.sfdcaserver.dtos.FileDownloadDto;
import com.cloudstorage.sfdcaserver.dtos.FileUploadDto;
import com.cloudstorage.sfdcaserver.dtos.RequestFileDownloadDto;
import com.cloudstorage.sfdcaserver.entities.*;
import com.cloudstorage.sfdcaserver.repositories.FileKeyAccessRepository;
import com.cloudstorage.sfdcaserver.repositories.FileRepository;
import com.cloudstorage.sfdcaserver.repositories.FolderRepository;
import com.cloudstorage.sfdcaserver.repositories.UserRepository;
import com.cloudstorage.sfdcaserver.service.FilePathConstructorService;
import com.cloudstorage.sfdcaserver.service.S3Service;
import com.cloudstorage.sfdcaserver.service.UuidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/file")
public class FileController {

    private final FileRepository fileRepository;

    private final FileKeyAccessRepository fileKeyAccessRepository;

    private final S3Service s3Service;
    private final UuidService uuidService;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final FilePathConstructorService filePathConstructorService;

    public FileController(FileRepository fileRepository, FileKeyAccessRepository fileKeyAccessRepository, S3Service s3Service, UuidService uuidService, UserRepository userRepository, FolderRepository folderRepository, FilePathConstructorService filePathConstructorService) {
        this.fileRepository = fileRepository;
        this.fileKeyAccessRepository = fileKeyAccessRepository;
        this.s3Service = s3Service;
        this.uuidService = uuidService;
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.filePathConstructorService = filePathConstructorService;
    }

    // Upload file method
    // Small issue: If save to database fails we end up with an orphaned file in the S3 - may fix time permits.
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @ModelAttribute FileUploadDto fileUploadDto) throws IOException {
        // Generate Custom UUID for file and s3Key
        String s3Key = filePathConstructorService.generateFilePath(fileUploadDto.getFolderId())
                + uuidService.generateUuid()
                + "_"
                + file.getOriginalFilename();

        // Upload File to S3 Bucket
        PutObjectResponse putObjectResponse = s3Service.uploadFileToS3(file, s3Key);

        // If it fails return error 500 and terminate method early
        if(!putObjectResponse.sdkHttpResponse().isSuccessful()) {
            return ResponseEntity.internalServerError().build();
        } else {
            // If successful create a new file entity to be stored in database
            User user = userRepository.findById(fileUploadDto.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Folder folder = folderRepository.findByFolderId(fileUploadDto.getFolderId());

            File fileToUpload = new File();
            fileToUpload.setOwnerId(user);
            fileToUpload.setFileName(file.getOriginalFilename());
            fileToUpload.setFolder(folder);
            fileToUpload.setS3Path(s3Key);

            fileRepository.save(fileToUpload);

            // Save the encrypted aes key
            FileKeyAccessId id  = new FileKeyAccessId();
            id.setFileId(fileToUpload.getFileId());
            id.setUserId(user.getUserId());

            FileKeyAccess fileKeyAccess = new FileKeyAccess();
            fileKeyAccess.setId(id);
            fileKeyAccess.setFile(fileToUpload);
            fileKeyAccess.setUser(user);
            fileKeyAccess.setEncryptedAesKey(fileUploadDto.getEncryptedAesKey());

            fileKeyAccessRepository.save(fileKeyAccess);

        }

        return ResponseEntity.ok("File uploaded successfully");
    }

    @PostMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestBody RequestFileDownloadDto requestFileDownloadDto) {
        // Retrieve requested file
        Optional<File> requestedFile = fileRepository.findByFileId(requestFileDownloadDto.getFileId());
        // If not
        if(requestedFile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Check if Owner has an Access Key to decrypt file
        Optional<FileKeyAccess> fileKeyAccess =
                fileKeyAccessRepository.findById_FileIdAndId_UserId(
                        requestFileDownloadDto.getFileId(),
                        requestFileDownloadDto.getUserId()
                );
        if(fileKeyAccess.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Authorized to access file");
        }

        // If all looks good construct FileDownloadDto
        FileDownloadDto fileDownloadDto = new FileDownloadDto();
        fileDownloadDto.setFileName(requestedFile.get().getFileName());
        fileDownloadDto.setEncryptedAesKey(fileKeyAccess.get().getEncryptedAesKey());


        // Download File from S3 Bucket
        String s3Key = requestedFile.get().getS3Path();
        byte[] downloadedFileFromS3 = s3Service.downloadFileToS3(s3Key);
        fileDownloadDto.setEncryptedFile(downloadedFileFromS3);


        // Send FileDownloadDto to user
        return ResponseEntity.ok(fileDownloadDto);
    }
}
