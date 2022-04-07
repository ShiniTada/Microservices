package com.epam.msa.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FileWithResourceId implements Serializable {
  private byte[] audioFile;
  private Long resourceId;
}
