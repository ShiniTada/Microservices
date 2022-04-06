package com.epam.msa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epam.msa.model.Resource;
import com.epam.msa.web.MusicFile;

@Service
public class MusicFileServiceImpl implements MusicFileService {

  private final StorageService storageService;
  private final ResourceService resourceService;

  @Autowired
  public MusicFileServiceImpl(StorageService storageService, ResourceService resourceService) {
    this.storageService = storageService;
    this.resourceService = resourceService;
  }

  @Override
  public MusicFile download(Long id) {
    Resource resource = resourceService.findById(id);
    MusicFile musicFile = storageService.download(resource.getFilename());
    musicFile.setId(id);
    return musicFile;
  }

  @Override
  public Long upload(MultipartFile file) {
    String filename = storageService.upload(file);
    Resource resource = new Resource();
    resource.setFilename(filename);
    return resourceService.createResource(resource);
  }

  public List<Long> deleteAllById(List<Long> ids) {
    ids.stream()
        .map(resourceService::findById)
        .forEach(r -> storageService.delete(r.getFilename()));
    return resourceService.deleteAllById(ids);
  }
}
