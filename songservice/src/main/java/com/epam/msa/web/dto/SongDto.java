package com.epam.msa.web.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

  private Long id;

  @NotNull
  @Size(min = 2, max = 50)
  private String name;

  @NotNull
  @Size(min = 2, max = 50)
  private String artist;

  @NotNull
  @Size(min = 2, max = 50)
  private String album;

  //  @Pattern(regexp = "")
  private String length;

  @NotNull private Long resourceId;

  @Min(1900)
  @Max(2022)
  private int year;
}
