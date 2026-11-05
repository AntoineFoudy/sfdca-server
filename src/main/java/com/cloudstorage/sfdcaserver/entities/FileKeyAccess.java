package com.cloudstorage.sfdcaserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "file_key_access", schema = "sf-d-database")
public class FileKeyAccess {
    @EmbeddedId
    private FileKeyAccessId id;

    @MapsId("fileId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(name = "encrypted_aes_key", nullable = false)
    private String encryptedAesKey;


}