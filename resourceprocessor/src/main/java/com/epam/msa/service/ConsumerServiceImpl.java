package com.epam.msa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  @Override
  public void receivedMessage(String msg) {
    logger.info("Received message: " + msg);
  }
}
