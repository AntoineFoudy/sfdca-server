package com.cloudstorage.sfdcaserver.controller;


import com.cloudstorage.sfdcaserver.dtos.FileDataForUserDto;
import com.cloudstorage.sfdcaserver.dtos.FolderDataForUserDto;
import com.cloudstorage.sfdcaserver.dtos.ViewDirAndContentsDto;
import com.cloudstorage.sfdcaserver.entities.File;
import com.cloudstorage.sfdcaserver.entities.Folder;
import com.cloudstorage.sfdcaserver.repositories.FileRepository;
import com.cloudstorage.sfdcaserver.repositories.FolderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/api/requestToViewFolderContents")
public class ViewDirAndFilesController {

    private final FolderRepository folderRepository;

    private final FileRepository fileRepository;

    public ViewDirAndFilesController(FolderRepository folderRepository, FileRepository fileRepository) {
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    @GetMapping("{folderId}")
    public ResponseEntity<?> viewFolderContents(@PathVariable("folderId") Integer folderId) {
        // Check if folder exists
        Optional<Folder> requestedFolder = folderRepository.findById(folderId);

        // Folder does not exist, inform client that request was not found
        if(requestedFolder.isEmpty()) {
            ResponseEntity.notFound().build();
        }

        // Create the Response Object
        ViewDirAndContentsDto viewDirAndContentsDto = new ViewDirAndContentsDto();



        // If it does compile the folders content
        // by getting all the folders and files who have the folderId as their parent folder
        List<Folder> childFolder = folderRepository.findByParentFolderId_FolderId(folderId);
        List<File> childFiles = fileRepository.findByFolder_FolderId(folderId);

        // Add the Compiled data into ViewDirAndContentsDto
        for(Folder f : childFolder) {
            FolderDataForUserDto folderDataForUserDto = new FolderDataForUserDto();
            folderDataForUserDto.setFolderId(f.getFolderId());
            folderDataForUserDto.setFolderName(f.getFolderName());

            viewDirAndContentsDto.addFolderToList(folderDataForUserDto);
        }

        for(File f : childFiles) {
            FileDataForUserDto fileDataForUserDto = new FileDataForUserDto();
            fileDataForUserDto.setFileId(f.getFileId());
            fileDataForUserDto.setFileName(f.getFileName());

            viewDirAndContentsDto.addFileToList(fileDataForUserDto);
        }

        // Send the data
        return ResponseEntity.ok(viewDirAndContentsDto);
    }
}
