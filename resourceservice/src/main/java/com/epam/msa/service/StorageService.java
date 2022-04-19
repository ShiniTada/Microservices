package com.epam.msa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.epam.msa.domain.AudioFile;
import com.epam.msa.domain.Resource;

public interface StorageService {

  /**
   * Upload multipart file to S3 bucket
   *
   * @param multipartFile - file to upload
   * @return file name
   */
  String upload(MultipartFile multipartFile);

  /**
   * Download music file from S3 bucket
   *
   * @param resource - info about searching file
   * @return musicFile
   */
  AudioFile download(Resource resource);

  /**
   * Delete multipart file from S3 bucket
   *
   * @param filename - name of searched file
   */
  void delete(String filename);
}
