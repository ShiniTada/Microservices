package com.epam.msa.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class S3StorageService {
  private final AmazonS3 amazonS3;
  private final String bucketName;

  public S3StorageService(AmazonS3 amazonS3, @Value("${aws.s3.bucket-name}") String bucketName) {
    this.amazonS3 = amazonS3;
    this.bucketName = bucketName;
  }

  public InputStream download(String filename) {
    S3Object s3Object = amazonS3.getObject(bucketName, filename);
    return s3Object.getObjectContent();
  }
}
