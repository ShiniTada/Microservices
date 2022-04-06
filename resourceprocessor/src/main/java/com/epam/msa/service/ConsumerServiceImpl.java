package com.epam.msa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.msa.domain.FileWithResourceId;
import com.epam.msa.domain.SongDto;

@Service
public class ConsumerServiceImpl implements ConsumerService<FileWithResourceId> {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);
  private final AudioParser audioParser;
  private final SongServiceFeignClient songServiceFeignClient;

  @Autowired
  public ConsumerServiceImpl(
      AudioParser audioParser, SongServiceFeignClient songServiceFeignClient) {
    this.audioParser = audioParser;
    this.songServiceFeignClient = songServiceFeignClient;
  }

  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  @Override
  public void receivedMessage(FileWithResourceId fileWithResourceId) {
    Long resourceId = fileWithResourceId.getResourceId();
    logger.info("Received file with resource id : " + resourceId);

    SongDto metadata = audioParser.parseAudioMetadata(fileWithResourceId.getAudioFile());
    metadata.setResourceId(resourceId);
    logger.info(String.format("Send audio metadata with resource id  %s further", resourceId));
    songServiceFeignClient.create(metadata);
    //    webClientService.saveSongMetadata(audioMetadata);
  }
}
