package com.cloudstorage.sfdcaserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileUploadDto {
    private int ownerId;
    private String fileName;
    private int folderId;
    private String encryptedAesKey;

}
