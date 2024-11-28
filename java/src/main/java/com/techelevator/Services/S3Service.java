package com.techelevator.Services;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

import static org.springframework.cglib.core.CollectionUtils.bucket;

@Service
public class S3Service {

    private final S3Client s3Client;

    private final String awsBucketName = "contacthubprofilepictures";


    public S3Service(){
        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

    }
    public String uploadFile(String path, MultipartFile file){
        try{
            String fileName =  UUID.randomUUID() + "_" + file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsBucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(putObjectRequest,RequestBody.fromBytes(file.getBytes()));
//            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        return String.format("https://%s.s3.amazonaws.com/%s",
                awsBucketName,
//                s3Client.serviceClientConfiguration().region().id(),
                fileName);

        }
        catch(S3Exception | IOException e){
            throw new RuntimeException("Problem occurred trying to upload the file", e);
        }
    }
}
