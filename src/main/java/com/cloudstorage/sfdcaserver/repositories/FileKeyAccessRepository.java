package com.cloudstorage.sfdcaserver.repositories;

import com.cloudstorage.sfdcaserver.entities.FileKeyAccess;
import com.cloudstorage.sfdcaserver.entities.FileKeyAccessId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileKeyAccessRepository extends JpaRepository<FileKeyAccess, FileKeyAccessId> {

    // For Retrieving AES keys and for future file sharing
    Optional<FileKeyAccess> findById_FileIdAndId_UserId(Integer fileId, Integer userId);

    // Permission Check
    List<FileKeyAccess> findById_UserId(Integer userId);
}
