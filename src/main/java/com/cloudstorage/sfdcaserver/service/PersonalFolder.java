package com.cloudstorage.sfdcaserver.service;

// This class's purpose is to create a dedicated personal folder within the "personal" directory

import com.cloudstorage.sfdcaserver.entities.Folder;
import com.cloudstorage.sfdcaserver.entities.User;
import com.cloudstorage.sfdcaserver.repositories.FolderRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonalFolder {

    private final FolderRepository folderRepository;

    public PersonalFolder(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public void CreateUserPersonalFolder(User newUser) {
        // Parent Folder is "personal" with the id of 2
        Folder parentFolder = folderRepository.findByFolderId(2);
        // Folder type is private
        String folderType = "Private";

        // Create new folder and save folder to DB
        Folder folder = new Folder();
        folder.setFolderName(newUser.getEmail());
        folder.setOwnerId(newUser);
        folder.setParentFolderId(parentFolder);
        folder.setFolderType(folderType);
        folderRepository.save(folder);

    }
}
