package com.epam.msa.cucumber.steps;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.epam.msa.web.dto.SongDto;

@Component
public class PostScenarioContext {
  private SongDto request;
  private ResponseEntity<Map> response;

  public SongDto getRequest() {
    return request;
  }

  public void setRequest(SongDto request) {
    this.request = request;
  }

  public ResponseEntity<Map> getResponse() {
    return response;
  }

  public void setResponse(ResponseEntity<Map> response) {
    this.response = response;
  }
}
