package com.epam.msa.service;

public interface ProducerService<T> {
  /**
   * Convert song metadata to proper message type and sent it to Exchange
   *
   * @param message - what to send
   */
  void sendMessage(T message);
}
