package com.cloudstorage.sfdcaserver.repositories;

import com.cloudstorage.sfdcaserver.entities.File;
import com.cloudstorage.sfdcaserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Integer> {

    // For Downloading a file and Listing files in a folder
    List<File> findByFolder_FolderId(Integer folderId);

    // For Finding a file for download
    List<File> findByOwnerId(User ownerId);

    Optional<File> findByFileId(Integer fileId);

}
