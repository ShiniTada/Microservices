package com.epam.msa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.epam.msa.model.Song;

public interface SongService {
  Long createSong(Song song);

  Song findById(Long id);

  Page<Song> findAll(Pageable pageable);

  List<Long> deleteAllById(List<Long> ids);
}
