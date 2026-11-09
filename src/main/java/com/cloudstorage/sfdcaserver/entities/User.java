package com.cloudstorage.sfdcaserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "sf-d-database")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "role", nullable = false, length = 30)
    private String role;

    @Column(name = "sector", length = 100)
    private String sector;

    @Lob
    @Column(name = "public_key")
    private String publicKey;

    @Lob
    @Column(name = "private_key")
    private String privateKey;


}