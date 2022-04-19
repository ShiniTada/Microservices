package com.epam.msa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
public class ConsumerServiceImpl implements ConsumerService<Long> {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);
  private final Processor processor;

  @Autowired
  public ConsumerServiceImpl(Processor processor) {
    this.processor = processor;
  }

  @SneakyThrows
  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  @Override
  public void receivedMessage(Long message) {
    logger.info("Received message with resource id : " + message);
    processor.process(message);
  }
}
