package com.epam.msa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.msa.domain.SongDto;

@Service
public class WebClientServiceImpl implements WebClientService {

  private static final Logger logger = LoggerFactory.getLogger(WebClientServiceImpl.class);

  @Value("${webclient.songservice.url}")
  private String songServiceUrl;

  public void saveSongMetadata(SongDto songDto) {
    logger.info("Calling post /songs for resource " + songDto.getResourceId());
    //    Mono<SongDto> response = WebClient.create(songServiceUrl).get()
    //        .uri("/songs/{id}", 1)
    //        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    //        .retrieve()
    //        .bodyToMono(SongDto.class);
    //    logger.info(response.toString());

    //    Mono<String> response =
    //        WebClient.create(songServiceUrl)
    //            .post()
    //            .uri("/songs/")
    //            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    //            .body(Mono.just(songDto), SongDto.class)
    //            .retrieve()
    //            .bodyToMono(String.class);

    //            .body(BodyInserters.fromValue(songDto))
    //            .exchangeToMono(
    //                resp -> {
    //                  if (resp.statusCode().isError()) {
    //                    return resp.createException().flatMap(Mono::error);
    //                  }
    //                  return resp.bodyToMono(String.class);
    //                });
    //    System.out.println(response);
  }
}
