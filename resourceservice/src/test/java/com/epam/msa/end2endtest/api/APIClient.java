package com.epam.msa.end2endtest.api;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class APIClient {

  private final RestTemplate restTemplate = new RestTemplate();
  @Autowired ObjectMapper objectMapper;
  @Autowired Gson gson;
  @Value("${webclient.resourceservice.endpoint}")
  private String resourceEndpoint;
  @Value("${webclient.songservice.endpoint}")
  private String songEndpoint;

  public HttpEntity<Map> uploadFile(File audioFile) throws IOException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", new FileSystemResource(audioFile));
    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    return restTemplate.postForEntity(resourceEndpoint, requestEntity, Map.class);
  }

  public ResponseEntity<Map> getFileById(Object id) {
    return restTemplate.getForEntity(resourceEndpoint + "/" + id, Map.class);
  }

  public HttpEntity getSongByResourceId(Object id) throws JsonProcessingException {
    var response =
        restTemplate.getForEntity(songEndpoint + "/search/findByResourceId?id=" + id, Map.class);

    var body = response.getBody();
    var embedded = (Map<String, List>) body.get("_embedded");
    var embeddedSongs = embedded.get("songs");
    if (embeddedSongs.isEmpty()) {
      return ResponseEntity.EMPTY;
    }

    var songsFieldValuePairs = (Map<String, ?>) embeddedSongs.get(0);
    var json = objectMapper.writeValueAsString(songsFieldValuePairs);
    var song = gson.fromJson(json, SongDto.class);
    return new ResponseEntity<>(song, response.getStatusCode());
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class SongDto {
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String length;
    private Long resourceId;
    private int year;
  }
}
