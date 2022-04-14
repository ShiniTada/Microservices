package com.epam.msa.cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import com.epam.msa.cucumber.SongsHttpClient;
import com.epam.msa.web.dto.SongDto;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

  @Autowired
  private SongsHttpClient songsHttpClient;
  @Autowired
  private PostScenarioContext postScenarioContext;
  @Autowired
  private GetByIdScenarioContext getByIdScenarioContext;
  @Autowired
  private DeleteScenarioContext deleteScenarioContext;

  @Given("user's song")
  public void generatePostRequestBody() {
    SongDto songDto =
        SongDto.builder()
            .name("song_name")
            .artist("artist")
            .album("album")
            .length("03:12")
            .resourceId(2L)
            .year(2022)
            .build();
    postScenarioContext.setRequest(songDto);
  }

  @When("user uploads song")
  public void uploadSong() {
    ResponseEntity<Map> response = songsHttpClient.post(postScenarioContext.getRequest());
    postScenarioContext.setResponse(response);
  }

  @Then("save song")
  public void isSongSaved() {
    Object id = postScenarioContext.getResponse().getBody().get("id");
    assertNotNull(songsHttpClient.getById(id).getBody().getId());
  }

  @Then("return song id")
  public void getSongId() {
    assertEquals(HttpStatus.OK, postScenarioContext.getResponse().getStatusCode());
    assertNotNull(postScenarioContext.getResponse().getBody().get("id"));
  }

  @Given("pre-saved song")
  public void getSavedSong() {
    SongDto songDto =
        SongDto.builder()
            .name("song_name")
            .artist("artist")
            .album("album")
            .length("03:12")
            .resourceId(2L)
            .year(2022)
            .build();
    ResponseEntity<Map> response = songsHttpClient.post(songDto);
    Long id = Long.valueOf((Integer) response.getBody().get("id"));
    songDto.setId(id);
    getByIdScenarioContext.setExistedSong(songDto);
  }

  @When("user requests song by id")
  public void requestSongById() {
    Long id = getByIdScenarioContext.getExistedSong().getId();
    getByIdScenarioContext.setResponse(songsHttpClient.getById(id));
  }

  @Then("return song")
  public void getSong() {
    assertEquals(HttpStatus.OK, getByIdScenarioContext.getResponse().getStatusCode());
    assertEquals(
        getByIdScenarioContext.getExistedSong(), getByIdScenarioContext.getResponse().getBody());
  }

  @Given("pre-saved songs")
  public void getSavedSongs() {
    SongDto songDto =
        SongDto.builder()
            .name("song_name")
            .artist("artist")
            .album("album")
            .length("03:12")
            .resourceId(2L)
            .year(2022)
            .build();
    SongDto songDto2 =
        SongDto.builder()
            .name("song_name2")
            .artist("artist2")
            .album("album2")
            .length("03:12")
            .resourceId(2L)
            .year(2022)
            .build();
    ResponseEntity<Map> response = songsHttpClient.post(songDto);
    Long id = Long.valueOf((Integer) response.getBody().get("id"));
    ResponseEntity<Map> response2 = songsHttpClient.post(songDto2);
    Long id2 = Long.valueOf((Integer) response2.getBody().get("id"));
    deleteScenarioContext.setExistedSongIds(List.of(id, id2));
  }

  @When("user request delete songs")
  public void deleteSongs() {
    List<Long> ids = deleteScenarioContext.getExistedSongIds();
    try {
      songsHttpClient.delete(ids);
      ResponseEntity<Map> response = new ResponseEntity<>(Map.of("ids", ids), HttpStatus.NO_CONTENT);
      deleteScenarioContext.setResponse(response);
    } catch (HttpClientErrorException ex) {
      String message = ex.getResponseBodyAsString();
      ResponseEntity<Map> response = new ResponseEntity<>(Map.of("message", message), HttpStatus.NO_CONTENT);
      deleteScenarioContext.setResponse(response);
    }
  }

  @Then("return deleted songs ids")
  public void getDeletedSongIds() {
    assertEquals(HttpStatus.NO_CONTENT, deleteScenarioContext.getResponse().getStatusCode());
    assertNotNull(deleteScenarioContext.getResponse().getBody().get("ids"));
  }
}
