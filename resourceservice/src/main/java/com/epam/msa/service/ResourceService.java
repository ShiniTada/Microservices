package com.epam.msa.service;

import java.util.List;

import com.epam.msa.model.Resource;

public interface ResourceService {
  Long createResource(Resource resource);

  Resource findById(Long id);

  List<Long> deleteAllById(List<Long> ids);
}
