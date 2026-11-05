package com.cloudstorage.sfdcaserver.repositories;

import com.cloudstorage.sfdcaserver.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {

    // For Downloading a file and Listing files in a folder
    List<File> findByFolderId(Integer folderId);

    // For Finding a file for download
    List<File> findByOwnerId(Integer ownerId);
}
