package com.epam.msa.componenttest.stepcontext;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.epam.msa.web.dto.SongDto;

@Component
public class GetByIdScenarioContext {
  SongDto existedSong;
  ResponseEntity<SongDto> response;

  public void setExistedSong(SongDto existedSong) {
    this.existedSong = existedSong;
  }

  public SongDto getExistedSong() {
    return existedSong;
  }

  public void setResponse(ResponseEntity<SongDto> response) {
    this.response = response;
  }

  public ResponseEntity<SongDto> getResponse() {
    return response;
  }
}
