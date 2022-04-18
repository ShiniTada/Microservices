package com.epam.msa.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.msa.domain.AudioFile;

import lombok.SneakyThrows;

@Service
public class S3StorageService implements StorageService {

  private static final String FILE_EXTENSION = "fileExtension";

  private final AmazonS3 amazonS3;
  private final String bucketName;

  public S3StorageService(AmazonS3 amazonS3, @Value("${aws.s3.bucket-name}") String bucketName) {
    this.amazonS3 = amazonS3;
    this.bucketName = bucketName;

    initializeBucket();
  }

  @SneakyThrows
  @Override
  public String upload(MultipartFile multipartFile) {
    String key = multipartFile.getOriginalFilename();
    amazonS3.putObject(
        bucketName, key, multipartFile.getInputStream(), extractObjectMetadata(multipartFile));
    return key;
  }

  @Override
  public AudioFile download(String filename) {
    S3Object s3Object = amazonS3.getObject(bucketName, filename);
    Long contentLength = s3Object.getObjectMetadata().getContentLength();

    return AudioFile.builder()
        .fileName(filename)
        .contentLength(contentLength)
        .inputStream(s3Object.getObjectContent())
        .build();
  }

  @Override
  public void delete(String filename) {
    amazonS3.deleteObject(bucketName, filename);
  }

  private ObjectMetadata extractObjectMetadata(MultipartFile file) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(file.getSize());
    objectMetadata.setContentType(file.getContentType());
    objectMetadata
        .getUserMetadata()
        .put(FILE_EXTENSION, FilenameUtils.getExtension(file.getOriginalFilename()));
    return objectMetadata;
  }

  private void initializeBucket() {
    if (!amazonS3.doesBucketExistV2(bucketName)) {
      amazonS3.createBucket(bucketName);
    }
  }
}
