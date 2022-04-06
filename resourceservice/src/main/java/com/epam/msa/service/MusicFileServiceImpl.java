package com.epam.msa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epam.msa.model.MusicFile;
import com.epam.msa.model.Resource;

@Service
public class MusicFileServiceImpl implements MusicFileService {

  private static final Logger logger = LoggerFactory.getLogger(MusicFileServiceImpl.class);

  private final StorageService storageService;
  private final ResourceService resourceService;
  private final ProducerService producerService;

  @Autowired
  public MusicFileServiceImpl(
      StorageService storageService,
      ResourceService resourceService,
      ProducerService producerService) {
    this.storageService = storageService;
    this.resourceService = resourceService;
    this.producerService = producerService;
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
    Long id = resourceService.createResource(resource);
    logger.info(String.format("Resource[%s] saved", id));

    producerService.sendMessage(id.toString());
    logger.info(String.format("Send message with id of Resource[%s]", id));
    return id;
  }

  public List<Long> deleteAllById(List<Long> ids) {
    ids.stream()
        .map(resourceService::findById)
        .forEach(r -> storageService.delete(r.getFilename()));
    return resourceService.deleteAllById(ids);
  }
}
