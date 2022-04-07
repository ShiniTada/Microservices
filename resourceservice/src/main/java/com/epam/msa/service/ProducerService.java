package com.epam.msa.service;

import com.epam.msa.domain.Resource;

public interface ProducerService {
  /**
   * Convert song metadata to proper message type and sent it to Exchange
   *
   * @param resource - what to send
   */
  void sendMessage(Resource resource);
}
