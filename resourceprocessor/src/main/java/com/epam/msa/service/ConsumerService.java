package com.epam.msa.service;

public interface ConsumerService {

  /**
   * Resieves messages from queue
   * @param msg
   */
  public void receivedMessage(String msg);
}
