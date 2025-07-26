package com.jagt.reader.user.infrastructure.output.aws.adapter;

import com.jagt.reader.user.domain.port.output.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class S3FileStorageAdapter implements FileStorageService {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    private static final Logger LOGGER = LoggerFactory.getLogger(S3FileStorageAdapter.class);

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public String uploadFile(byte[] fileContent, String fileName, String contentType) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(fileContent));

            URL url = s3Client.utilities().getUrl(builder -> builder
                    .bucket(bucketName)
                    .key(fileName));

            LOGGER.info("Uploaded file at {}", url);
            return url.toString();
        } catch (S3Exception e) {
            return "";
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(request);
        } catch (S3Exception e) {

        }
    }

    @Override
    public boolean fileExists(String fileName) {
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.headObject(request);
            return true;
        } catch (S3Exception e) {
            if(e.statusCode() == 404) {
                return false;
            }

            return false;
        }
    }

    @Override
    public String generarePreSignedDownloadUrl(String fileName, Duration duration) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration)
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest request = s3Presigner.presignGetObject(presignRequest);
        URL url = request.url();

        return url.toString();
    }
}
