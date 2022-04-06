package com.epam.msa.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.msa.domain.Resource;
import com.epam.msa.repo.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService {
  private final ResourceRepository resourceRepository;

  @Autowired
  public ResourceServiceImpl(ResourceRepository resourceRepository) {
    this.resourceRepository = resourceRepository;
  }

  @Override
  public Long createResource(Resource Resource) {
    return resourceRepository.save(Resource).getId();
  }

  @Override
  public Resource findById(Long id) throws NoSuchElementException {
    return resourceRepository
        .findById(id)
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    String.format("Resource with id = %s doesn't exist", id)));
  }

  @Override
  public List<Long> deleteAllById(List<Long> ids) {
    ids.forEach(this::findById);
    resourceRepository.deleteAllById(ids);
    return ids;
  }
}
