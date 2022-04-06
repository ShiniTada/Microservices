package com.epam.msa.service;

public interface ConsumerService<T> {

  /**
   * Receive messages from queue
   *
   * @param message - message
   */
  public void receivedMessage(T message);
}
