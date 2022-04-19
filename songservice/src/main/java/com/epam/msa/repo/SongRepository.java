package com.epam.msa.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.epam.msa.domain.Song;

@RepositoryRestResource()
public interface SongRepository extends PagingAndSortingRepository<Song, Long> {

  @RestResource(exported = true)
  List<Song> findByResourceId(@Param("id") Long id);

  @Override
  @RestResource(exported = false) // restrict access from the outside world
  Iterable<Song> findAll(Sort sort);

  @Override
  @RestResource(exported = false)
  Page<Song> findAll(Pageable pageable);

  @Override
  @RestResource(exported = false)
  <S extends Song> S save(S entity);

  @Override
  @RestResource(exported = false)
  <S extends Song> Iterable<S> saveAll(Iterable<S> entities);

  @Override
  @RestResource(exported = false)
  Optional<Song> findById(Long aLong);

  @Override
  @RestResource(exported = false)
  boolean existsById(Long aLong);

  @Override
  @RestResource(exported = false)
  Iterable<Song> findAll();

  @Override
  @RestResource(exported = false)
  Iterable<Song> findAllById(Iterable<Long> longs);

  @Override
  @RestResource(exported = false)
  long count();

  @Override
  @RestResource(exported = false)
  void deleteById(Long aLong);

  @Override
  @RestResource(exported = false)
  void delete(Song entity);

  @Override
  @RestResource(exported = false)
  void deleteAllById(Iterable<? extends Long> longs);

  @Override
  @RestResource(exported = false)
  void deleteAll(Iterable<? extends Song> entities);

  @Override
  @RestResource(exported = false)
  void deleteAll();
}
