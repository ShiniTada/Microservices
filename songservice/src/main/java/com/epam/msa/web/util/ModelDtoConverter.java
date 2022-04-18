package com.epam.msa.web.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.msa.domain.Song;
import com.epam.msa.web.dto.SongDto;

@Component
public class ModelDtoConverter {

  private final ModelMapper modelMapper;

  @Autowired
  public ModelDtoConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public SongDto convertToDto(Song model) {
    return modelMapper.map(model, SongDto.class);
  }

  public Song convertToModel(SongDto dto) {
    return modelMapper.map(dto, Song.class);
  }
}
