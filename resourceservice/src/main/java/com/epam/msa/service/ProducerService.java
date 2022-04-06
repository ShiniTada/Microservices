package com.epam.msa.service;

public interface ProducerService {
  /**
   * Convert to proper message type and sent it to Exchange
   *
   * @param msg - message
   */
  void sendMessage(String msg);
}
