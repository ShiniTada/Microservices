package com.epam.msa.service;

import org.springframework.web.multipart.MultipartFile;

import com.epam.msa.domain.AudioFile;

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
   * @param id - name of searched file
   * @return musicFile
   */
  AudioFile download(String id);

  /**
   * Delete multipart file from S3 bucket
   *
   * @param filename - name of searched file
   */
  void delete(String filename);
}
