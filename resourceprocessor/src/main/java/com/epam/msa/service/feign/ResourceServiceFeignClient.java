package com.epam.msa.service.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("resourceservice")
public interface ResourceServiceFeignClient {

  /**
   * Receive audio file form resource service
   *
   * @param id - id of resource
   * @return audio file
   */
  @GetMapping(value = "/resources/{id}")
  ResponseEntity<Map<String, byte[]>> getById(@PathVariable("id") Long id);
}
