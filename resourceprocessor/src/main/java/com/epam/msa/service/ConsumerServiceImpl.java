package com.epam.msa.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epam.msa.domain.SongDto;
import com.epam.msa.service.feign.ResourceServiceFeignClient;
import com.epam.msa.service.feign.SongServiceFeignClient;

import lombok.SneakyThrows;

@Service
public class ConsumerServiceImpl implements ConsumerService<String> {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);
  private final AudioParser audioParser;
  private final SongServiceFeignClient songServiceFeignClient;
  private final ResourceServiceFeignClient resourceServiceFeignClient;

  @Autowired
  public ConsumerServiceImpl(
      AudioParser audioParser,
      SongServiceFeignClient songServiceFeignClient,
      ResourceServiceFeignClient resourceServiceFeignClient) {
    this.audioParser = audioParser;
    this.songServiceFeignClient = songServiceFeignClient;
    this.resourceServiceFeignClient = resourceServiceFeignClient;
  }

  @SneakyThrows
  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  @Override
  public void receivedMessage(String message) {
    logger.info("Received message with resource id : " + message);
    Long resourceId = Long.parseLong(message);

    ResponseEntity<Resource> response = resourceServiceFeignClient.download(resourceId);
    logger.info("Received file with resource id : " + message);
    byte[] audioFile = Objects.requireNonNull(response.getBody()).getInputStream().readAllBytes();

    SongDto metadata = audioParser.parseAudioMetadata(audioFile);
    metadata.setResourceId(resourceId);

    songServiceFeignClient.create(metadata);
    logger.info(String.format("Send audio metadata with resource id  %s further", resourceId));
    //    webClientService.saveSongMetadata(audioMetadata);
  }
}
