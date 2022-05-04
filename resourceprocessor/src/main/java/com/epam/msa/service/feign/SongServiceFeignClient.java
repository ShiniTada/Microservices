package com.epam.msa.service.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.msa.domain.SongDto;

@FeignClient("songservice")
public interface SongServiceFeignClient {

  /**
   * Save song metadata in song service
   *
   * @param songDto what to save
   */
  @PostMapping(value = "/songs")
  ResponseEntity<Map<String, Long>> create(@RequestBody @Validated SongDto songDto);
}
