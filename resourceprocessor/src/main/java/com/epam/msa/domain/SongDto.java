package com.epam.msa.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class SongDto implements Serializable {
  private String name;
  private String artist;
  private String album;
  private String length;
  private Long resourceId;
  private int year;
}
