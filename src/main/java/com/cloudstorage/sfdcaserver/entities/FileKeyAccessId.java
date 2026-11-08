package com.cloudstorage.sfdcaserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class FileKeyAccessId implements Serializable {
    @Serial
    private static final long serialVersionUID = -401228111291619534L;
    @Column(name = "file_id", nullable = false)
    private Integer fileId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;


}