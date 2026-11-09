package com.cloudstorage.sfdcaserver.service;


import com.cloudstorage.sfdcaserver.entities.Folder;
import com.cloudstorage.sfdcaserver.repositories.FolderRepository;
import org.springframework.stereotype.Service;

// Removes the burden of the client to keep tract of the system directory
// Also allows for the method to be changed without having to touch the FileController Class

@Service
public class FilePathConstructorService {

    private final FolderRepository folderRepository;

    public FilePathConstructorService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public String generateFilePath(int parentFolder) {
        StringBuilder filePath = new StringBuilder();

        Folder currentFolder = folderRepository.findById(parentFolder)
                .orElseThrow();

        while(currentFolder != null) {
            filePath.insert(0, currentFolder.getFolderName() + "/");
            currentFolder = currentFolder.getParentFolderId();
        }

        return filePath.toString();
    }
}
