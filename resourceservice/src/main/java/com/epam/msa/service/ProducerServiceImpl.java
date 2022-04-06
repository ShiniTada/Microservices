package com.epam.msa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {

  private RabbitTemplate rabbitTemplate;

  @Autowired
  public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Value("${spring.rabbitmq.exchange}")
  private String exchange;

  @Value("${spring.rabbitmq.routingkey}")
  private String routingkey;

  @Override
  public void sendMessage(String msg) {
    rabbitTemplate.convertAndSend(exchange,routingkey, msg);
  }

}