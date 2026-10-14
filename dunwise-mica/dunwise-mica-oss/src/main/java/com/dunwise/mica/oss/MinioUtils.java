package com.dunwise.mica.oss;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * MinIO 对象存储工具类
 */
public class MinioUtils {

    private final MinioClient minioClient;

    private final String endpoint;

    private final String bucketName;

    public MinioUtils(MinioClient minioClient, String endpoint, String bucketName) {
        this.minioClient = minioClient;
        this.endpoint = endpoint;
        this.bucketName = bucketName;
    }

    public String putObject(String objectName, InputStream inputStream, String contentType, long size) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, size, -1)
                    .contentType(contentType)
                    .build());
            return getObjectUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to put object: " + objectName, e);
        }
    }

    public String putObject(String objectName, MultipartFile file) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            return getObjectUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to put object: " + objectName, e);
        }
    }

    public InputStream getObject(String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get object: " + objectName, e);
        }
    }

    public String getObjectUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .method(Method.GET)
                    .expiry(7, TimeUnit.DAYS)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get object url: " + objectName, e);
        }
    }

    public void deleteObject(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete object: " + objectName, e);
        }
    }

    public void deleteObjects(Iterable<String> objectNames) {
        try {
            minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(bucketName)
                    .objects(java.util.stream.StreamSupport.stream(objectNames.spliterator(), false)
                            .toList())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete objects", e);
        }
    }

    public boolean objectExists(String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            return true;
        } catch (MinioException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                return false;
            }
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to check object existence: " + objectName, e);
        }
    }

    public Map<String, Object> getObjectMetadata(String objectName) {
        try {
            StatObjectResponse response = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("size", response.size());
            metadata.put("contentType", response.contentType());
            metadata.put("lastModified", response.lastModified().toString());
            return metadata;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get object metadata: " + objectName, e);
        }
    }
}
