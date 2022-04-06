package com.epam.msa.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.epam.msa.model.MusicFile;

public interface MusicFileService {
  MusicFile download(Long id);

  Long upload(MultipartFile file);

  List<Long> deleteAllById(List<Long> ids);
}
