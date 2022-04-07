package com.epam.msa.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resourceservice", url = "${webclient.resourceservice.url}")
public interface ResourceServiceFeignClient {

  /**
   * Receive audio file form resource service
   *
   * @param id - id of resource
   * @return audio file
   */
  @GetMapping(value = "/resources/{id}")
  ResponseEntity<Resource> download(@PathVariable("id") Long id);
}
