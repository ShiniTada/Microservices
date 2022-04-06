package com.epam.msa.service;

import java.util.List;

import com.epam.msa.model.Resource;

public interface ResourceService {
  /**
   * Save new resource in database
   *
   * @param resource - new resource
   * @return id of saved resource
   */
  Long createResource(Resource resource);

  /**
   * Get resource from database by id
   *
   * @param id - id of searched resource
   * @return resource
   */
  Resource findById(Long id);

  /**
   * Mass delete resources by ids
   *
   * @param ids - ids of resources
   * @return ids of successfully deleted resources
   */
  List<Long> deleteAllById(List<Long> ids);
}
