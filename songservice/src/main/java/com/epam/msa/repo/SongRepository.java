package com.epam.msa.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.epam.msa.model.Song;

@RepositoryRestResource(exported = false) // restrict access from the outside world
public interface SongRepository extends PagingAndSortingRepository<Song, Long> {}
