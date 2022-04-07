package com.epam.msa.service;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.msa.domain.Resource;
import com.epam.msa.domain.SongDto;

@Service
public class ConsumerServiceImpl implements ConsumerService<Resource> {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);
  private final AudioParser audioParser;
  private final SongServiceFeignClient songServiceFeignClient;
  private final S3StorageService storageService;

  @Autowired
  public ConsumerServiceImpl(
      AudioParser audioParser,
      SongServiceFeignClient songServiceFeignClient,
      S3StorageService storageService) {
    this.audioParser = audioParser;
    this.songServiceFeignClient = songServiceFeignClient;
    this.storageService = storageService;
  }

  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  @Override
  public void receivedMessage(Resource resource) {
    Long resourceId = resource.getId();
    logger.info("Received file with resource id : " + resourceId);

    InputStream audioInputStream = storageService.download(resource.getFilename());
    SongDto metadata = audioParser.parseAudioMetadata(audioInputStream);
    metadata.setResourceId(resourceId);

    logger.info(String.format("Send audio metadata with resource id  %s", resourceId));
    songServiceFeignClient.create(metadata);
  }
}
