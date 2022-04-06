package com.epam.msa.service;

import com.epam.msa.domain.SongDto;

public interface WebClientService {

  /**
   * Save song metadata in song service
   *
   * @param songDto what to save
   */
  void saveSongMetadata(SongDto songDto);
}
