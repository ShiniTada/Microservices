package com.epam.msa.model;

import java.io.InputStream;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicFile {

  private Long id;
  private String fileName;
  private Long contentLength;
  private InputStream inputStream;
}
