package com.cloudstorage.sfdcaserver.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestFileDownloadDto {
    private int userId;
    private int fileId;
}
