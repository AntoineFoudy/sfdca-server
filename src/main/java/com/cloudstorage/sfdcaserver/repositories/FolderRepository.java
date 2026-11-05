package com.cloudstorage.sfdcaserver.repositories;

import com.cloudstorage.sfdcaserver.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Integer> {

    // Used to find if Folder is owned by an individual
    List<Folder> findByOwnerId(Integer ownerId);

    // Used to navigate folder's hierarchy
    List<Folder> findByParentFolderId(Integer parentFolderId);

    // Used to find if Folder is Sector/Company or Private
    List<Folder> findByFolderType(String folderType);
}
