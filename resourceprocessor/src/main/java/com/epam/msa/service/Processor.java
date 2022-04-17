package com.epam.msa.service;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epam.msa.domain.SongDto;
import com.epam.msa.service.feign.ResourceServiceFeignClient;
import com.epam.msa.service.feign.SongServiceFeignClient;

@Service
public class Processor {

  private static final Logger logger = LoggerFactory.getLogger(Processor.class);
  private final AudioParser audioParser;
  private final SongServiceFeignClient songServiceFeignClient;
  private final ResourceServiceFeignClient resourceServiceFeignClient;

  @Autowired
  public Processor(
      AudioParser audioParser,
      SongServiceFeignClient songServiceFeignClient,
      ResourceServiceFeignClient resourceServiceFeignClient) {
    this.audioParser = audioParser;
    this.songServiceFeignClient = songServiceFeignClient;
    this.resourceServiceFeignClient = resourceServiceFeignClient;
  }

  /***
   * Get resource by id, parse received object metadata and sent it to song service
   * @param resourceId
   * @return resource id
   */
  public Long process(Long resourceId) {
    ResponseEntity<Map<String, byte[]>> response = resourceServiceFeignClient.getById(resourceId);
    logger.info("Received file with resource id : " + resourceId);
    byte[] audioFile = Objects.requireNonNull(response.getBody().get("file"));

    SongDto metadata = audioParser.parseAudioMetadata(audioFile);
    metadata.setResourceId(resourceId);

    songServiceFeignClient.create(metadata);
    logger.info(String.format("Send audio metadata with resource id  %s further", resourceId));
    return resourceId;
  }
}
