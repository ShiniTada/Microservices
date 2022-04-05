package com.epam.msa.service;

import org.springframework.web.multipart.MultipartFile;

import com.epam.msa.web.MusicFile;

public interface StorageService {

  String upload(MultipartFile multipartFile);

  MusicFile download(String id);

  void delete(String filename);
}
