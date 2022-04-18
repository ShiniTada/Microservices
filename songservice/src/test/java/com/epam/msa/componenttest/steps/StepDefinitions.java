package com.epam.msa.componenttest.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.epam.msa.componenttest.api.SongsHttpClient;
import com.epam.msa.componenttest.stepcontext.DeleteScenarioContext;
import com.epam.msa.componenttest.stepcontext.GetByIdScenarioContext;
import com.epam.msa.componenttest.stepcontext.PostScenarioContext;
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
    var songDto =
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
    var response = songsHttpClient.post(postScenarioContext.getRequest());
    postScenarioContext.setResponse(response);
  }

  @Then("save song")
  public void isSongSaved() {
    var id = postScenarioContext.getResponse().getBody().get("id");
    assertNotNull(songsHttpClient.getById(id).getBody().getId());
  }

  @Then("return song id")
  public void getSongId() {
    assertEquals(HttpStatus.OK, postScenarioContext.getResponse().getStatusCode());
    assertNotNull(postScenarioContext.getResponse().getBody().get("id"));
  }

  @Given("pre-saved song")
  public void getSavedSong() {
     var songDto =
        SongDto.builder()
            .name("song_name")
            .artist("artist")
            .album("album")
            .length("03:12")
            .resourceId(2L)
            .year(2022)
            .build();
    var response = songsHttpClient.post(songDto);
    var id = Long.valueOf((Integer) response.getBody().get("id"));
    songDto.setId(id);
    getByIdScenarioContext.setExistedSong(songDto);
  }

  @When("user requests song by id")
  public void requestSongById() {
    var id = getByIdScenarioContext.getExistedSong().getId();
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
    var songDto =
        SongDto.builder()
            .name("song_name")
            .artist("artist")
            .album("album")
            .length("03:12")
            .resourceId(2L)
            .year(2022)
            .build();
    var songDto2 =
        SongDto.builder()
            .name("song_name2")
            .artist("artist2")
            .album("album2")
            .length("03:12")
            .resourceId(2L)
            .year(2022)
            .build();
    var response = songsHttpClient.post(songDto);
    var id = Long.valueOf((Integer) response.getBody().get("id"));
    var response2 = songsHttpClient.post(songDto2);
    var id2 = Long.valueOf((Integer) response2.getBody().get("id"));
    deleteScenarioContext.setExistedSongIds(List.of(id, id2));
  }

  @When("user request delete songs")
  public void deleteSongs() {
    var ids = deleteScenarioContext.getExistedSongIds();
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
