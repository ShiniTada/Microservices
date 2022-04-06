package com.epam.msa.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.msa.domain.SongDto;

@FeignClient(name = "songservice", url = "${webclient.songservice.url}")
public interface SongServiceFeignClient {

  @PostMapping(value = "/songs")
  Map<String, Long> create(@RequestBody @Validated SongDto songDto);
}
