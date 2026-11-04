// Purpose
// This class is only to upload and retrieve files for S3 Bucket
// Any other needed functions will be handles else where


package com.cloudstorage.sfdcaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    // Upload File method
    // Return PutObjectResponse to be handled in another method
    // If PutObjectResponse not successful, server will not upload file details to DB
    public PutObjectResponse uploadFileToS3(MultipartFile file, String s3Key) throws IOException {
        return s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .contentType(file.getContentType())
                .build(),
                RequestBody.fromBytes(file.getBytes()));
    }

    // Download File method
    public byte[] downloadFileToS3(String s3Key) {
        ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(GetObjectRequest.builder().
                bucket(bucket).
                key(s3Key)
                .build());

        return response.asByteArray();

    }
}
