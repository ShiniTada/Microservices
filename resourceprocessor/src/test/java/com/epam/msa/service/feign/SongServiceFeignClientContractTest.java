package com.epam.msa.service.feign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import com.epam.msa.domain.SongDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
    ids = "com.epam.msa:songservice:+:stubs:8081",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class SongServiceFeignClientContractTest {

  @Autowired private SongServiceFeignClient songClient;

  @Test
  public void createSong() {
    var songDto =
        SongDto.builder()
            .name("We Are the Champions")
            .artist("Queen")
            .album("News of the World")
            .length("3:14")
            .resourceId(7L)
            .year(1977)
            .build();
    var response = songClient.create(songDto);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1L, response.getBody().get("id"));
  }

}
