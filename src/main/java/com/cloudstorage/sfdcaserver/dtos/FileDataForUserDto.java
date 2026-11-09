package com.cloudstorage.sfdcaserver.dtos;

// This is for the ViewDirAndContentsDto
// Its purpose is to only display File IDs and Names to the user instead of all the information in the database

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDataForUserDto {
    private int FileId;
    private String FileName;
}
