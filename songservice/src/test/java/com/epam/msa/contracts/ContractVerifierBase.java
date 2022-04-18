package com.epam.msa.contracts;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.epam.msa.SongServiceApplication;
import com.epam.msa.domain.Song;
import com.epam.msa.service.SongServiceImpl;
import com.epam.msa.web.SongController;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(classes = SongServiceApplication.class)
public class ContractVerifierBase {

  @Autowired SongController songController;

  @MockBean SongServiceImpl songService;

  @BeforeEach
  public void setup() {
    RestAssuredMockMvc.standaloneSetup(songController);
    var song =
        Song.builder()
            .name("We Are the Champions")
            .artist("Queen")
            .album("News of the World")
            .length("3:14")
            .resourceId(7L)
            .year(1977)
            .build();
    Mockito.when(songService.createSong(song)).thenReturn(1L);
  }
}
