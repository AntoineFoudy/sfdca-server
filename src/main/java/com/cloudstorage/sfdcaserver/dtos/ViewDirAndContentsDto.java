package com.cloudstorage.sfdcaserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewDirAndContentsDto {
    private List<FolderDataForUserDto> folders = new ArrayList<>();
    private List<FileDataForUserDto> files = new ArrayList<>();

    public void addFolderToList(FolderDataForUserDto f) {
        folders.add(f);
    }

    public void addFileToList(FileDataForUserDto f) {
        files.add(f);
    }


}
