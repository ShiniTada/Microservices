package com.epam.msa.end2endtest.steps;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.epam.msa.end2endtest.api.APIClient;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

  @Autowired private APIClient apiClient;

  private File audioFile;
  private HttpEntity<Map> response;

  @Given("new audio file")
  public void generateNewAudioFile() throws IOException {
    audioFile = new File("src/test/resources/data/we-are-the-champions.mp3");
  }

  @When("user uploads audio file")
  public void uploadAudioFile() throws IOException {
    response = apiClient.uploadFile(audioFile);
  }

  @Then("resource id is returned")
  public void returnResourceId() {
    assertNotNull(response.getBody().get("id"));
  }

  @Then("user can get audio file by id")
  public void getAudioFileById() {
    var id = response.getBody().get("id");
    var getResponse = apiClient.getFileById(id);
    assertNotNull(getResponse.getBody().get("file"));
  }

  @Then("user can get audio metadata by resource id")
  public void getAudioMetadataByResourceId() throws JsonProcessingException, InterruptedException {
    //wait 10 seconds to be sure that song service was invoked
    Thread.sleep(10000);
    var id = response.getBody().get("id");
    var getResponse = apiClient.getSongByResourceId(id);
    assertNotNull(getResponse.getBody());
  }
}
