package com.epam.msa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epam.msa.domain.AudioFile;
import com.epam.msa.domain.Resource;

import lombok.SneakyThrows;

@Service
public class AudioFileServiceImpl implements AudioFileService {

  private static final Logger logger = LoggerFactory.getLogger(AudioFileServiceImpl.class);

  private final StorageService storageService;
  private final ResourceService resourceService;
  private final ProducerService producerService;

  @Autowired
  public AudioFileServiceImpl(
      StorageService storageService,
      ResourceService resourceService,
      ProducerService producerService) {
    this.storageService = storageService;
    this.resourceService = resourceService;
    this.producerService = producerService;
  }

  @Override
  public AudioFile download(Long id) {
    Resource resource = resourceService.findById(id);
    AudioFile audioFile = storageService.download(resource.getFilename());
    audioFile.setId(id);
    return audioFile;
  }

  @SneakyThrows
  @Override
  public Long upload(MultipartFile file) {
    String filename = storageService.upload(file);

    Resource resource = new Resource();
    resource.setFilename(filename);
    Long id = resourceService.createResource(resource);
    logger.info(String.format("Resource[%s] saved", id));

    producerService.sendMessage(id);
    logger.info("Send message: file with resource id ", id);
    return id;
  }

  public List<Long> deleteAllById(List<Long> ids) {
    ids.stream()
        .map(resourceService::findById)
        .forEach(r -> storageService.delete(r.getFilename()));
    return resourceService.deleteAllById(ids);
  }
}
