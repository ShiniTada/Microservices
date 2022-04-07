package com.epam.msa.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.msa.domain.FileWithResourceId;

@Service
public class ProducerServiceImpl implements ProducerService {

  private final RabbitTemplate rabbitTemplate;
  @Value("${spring.rabbitmq.exchange}")
  private String exchange;
  @Value("${spring.rabbitmq.routingkey}")
  private String routingkey;

  @Autowired
  public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void sendMessage(FileWithResourceId fileWithResourceId) {
    rabbitTemplate.convertAndSend(exchange, routingkey, fileWithResourceId);
  }
}
