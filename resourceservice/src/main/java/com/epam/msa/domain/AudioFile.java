package com.epam.msa.domain;

import java.io.InputStream;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AudioFile {

  private Long id;
  private String fileName;
  private Long contentLength;
  private InputStream inputStream;
}
