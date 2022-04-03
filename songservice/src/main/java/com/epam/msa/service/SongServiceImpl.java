package com.epam.msa.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.msa.model.Song;
import com.epam.msa.repo.SongRepository;

@Service
public class SongServiceImpl implements SongService {
  private final SongRepository songRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public SongServiceImpl(SongRepository songRepository, ModelMapper modelMapper) {
    this.songRepository = songRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public Long createSong(Song song) {
    return songRepository.save(song).getId();
  }

  public Song findById(Long id) throws NoSuchElementException {
    return songRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
        String.format("Song with id = %s doesn't exist", id)));
  }

  @Override
  public Page<Song> findAll(Pageable pageable) {
    return songRepository.findAll(pageable);
  }

  @Override
  public List<Long> deleteAllById(List<Long> ids) {
    ids.forEach(this::findById);
    songRepository.deleteAllById(ids);
    return ids;
  }

}
