package com.epam.msa.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.epam.msa.web.dto.SongDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class SongControllerTest {

  private static final String GET_BY_ID_URI = "/songs/{id}";

  @MockBean private SongController songController;

  @Autowired private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeAll
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void testGetById_songExist_Ok() throws Exception {
    // given
    SongDto songDto = new SongDto(1L, "song_name", "artist", "album", "03:12", 2L, 2022);
    given(songController.getById(songDto.getId())).willReturn(songDto);

    // when-then
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(GET_BY_ID_URI, 1).accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
  }

  @Test
  public void testGetById_songNotExist_NotFound() throws Exception {
    // given
    given(songController.getById(1L)).willThrow(NoSuchElementException.class);

    // when-then
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(GET_BY_ID_URI, 1).accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetById_internalError_ServerError() throws Exception {
    // given
    given(songController.getById(1L)).willThrow(RuntimeException.class);

    // when-then
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(GET_BY_ID_URI, 1).accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is5xxServerError());
  }
}
