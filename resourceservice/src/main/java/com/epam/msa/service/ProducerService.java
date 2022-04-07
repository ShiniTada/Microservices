package com.epam.msa.service;

import com.epam.msa.domain.FileWithResourceId;

public interface ProducerService {
  /**
   * Convert song metadata to proper message type and sent it to Exchange
   *
   * @param fileWithResourceId - what to send
   */
  void sendMessage(FileWithResourceId fileWithResourceId);
}
