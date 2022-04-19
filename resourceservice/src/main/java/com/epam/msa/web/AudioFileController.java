package com.epam.msa.web;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epam.msa.domain.AudioFile;
import com.epam.msa.service.AudioFileService;

import lombok.SneakyThrows;

@RestController
@RequestMapping(path = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
public class AudioFileController {

  private final AudioFileService audioFileService;

  @Autowired
  public AudioFileController(AudioFileService audioFileService) {
    this.audioFileService = audioFileService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Long> upload(@RequestPart(value = "file") MultipartFile file) {
    Long id = audioFileService.upload(file);
    return Map.of("id", id);
  }

  @GetMapping(path = "/{id}/download")
  public ResponseEntity<org.springframework.core.io.Resource> download(
      @PathVariable("id") @NotNull Long id) {
    AudioFile audioFile = audioFileService.download(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + audioFile.getFileName())
        .contentLength(audioFile.getContentLength())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new InputStreamResource(audioFile.getInputStream()));
  }

  @SneakyThrows
  @GetMapping(path = "/{id}")
  public ResponseEntity<Map<String, byte[]>> getById(@PathVariable("id") @NotNull Long id) {
    AudioFile audioFile = audioFileService.download(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + audioFile.getFileName())
        .body(Map.of("file", audioFile.getInputStream().readAllBytes()));
  }

  @DeleteMapping
  public Map<String, List<Long>> deleteAllById(@RequestParam("ids") @NotNull List<Long> ids) {
    List<Long> deletedIds = audioFileService.deleteAllById(ids);
    return Map.of("ids", deletedIds);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
    return Map.of("message", ex.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
    FieldError fieldError = ex.getFieldError();
    return Map.of("message", fieldError.getField() + " " + fieldError.getDefaultMessage());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Map<String, String> handleValidationException(Exception ex) {
    return Map.of("message", ex.getMessage());
  }
}
