package com.epam.msa.service;

import static com.epam.msa.service.AudioParser.DEFAULT_LENGTH;
import static com.epam.msa.service.AudioParser.DEFAULT_UNKNOWN;
import static com.epam.msa.service.AudioParser.DEFAULT_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.epam.msa.domain.SongDto;

class AudioParserTest {

  private AudioParser audioParser;

  @BeforeEach
  void setUp() {
    audioParser = new AudioParser();
  }

  @Test
  @DisplayName("Check default setting values")
  void setupDefaultValues() {
    // given
    SongDto songDto = new SongDto();
    SongDto songDtoWithDefaultValues =
        SongDto.builder()
            .name(DEFAULT_UNKNOWN)
            .artist(DEFAULT_UNKNOWN)
            .album(DEFAULT_UNKNOWN)
            .length(DEFAULT_LENGTH)
            .year(DEFAULT_YEAR)
            .build();

    // when
    audioParser.setupDefaultValues(songDto);

    // then
    assertEquals(songDtoWithDefaultValues, songDto, "Default values weren't set");
  }
}
