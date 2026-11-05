package com.cloudstorage.sfdcaserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpDto {
    private String email;
    private String hashedPassword;
    private String role;
    private String sector;
    private String publicKey;
    private String privateKey;
}
