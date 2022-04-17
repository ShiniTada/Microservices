package com.epam.msa.componenttest.steps;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DeleteScenarioContext {
  List<Long> existedSongIds;
  ResponseEntity<Map> response;

  public void setExistedSongIds(List<Long> existedSongIds) {
    this.existedSongIds = existedSongIds;
  }

  public void setResponse(ResponseEntity<Map> response) {
    this.response = response;
  }

  public List<Long> getExistedSongIds() {
    return existedSongIds;
  }

  public ResponseEntity<Map> getResponse() {
    return response;
  }
}
