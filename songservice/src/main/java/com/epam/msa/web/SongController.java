package com.epam.msa.web;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.msa.domain.Song;
import com.epam.msa.service.SongService;
import com.epam.msa.service.SongServiceImpl;
import com.epam.msa.web.dto.SongDto;
import com.epam.msa.web.util.ModelDtoConverter;

@RestController
@RequestMapping(path = "/songs", produces = MediaType.APPLICATION_JSON_VALUE)
public class SongController {
  private final SongService songService;
  private final ModelDtoConverter modelDtoConverter;

  @Autowired
  public SongController(SongServiceImpl songService, ModelDtoConverter modelDtoConverter) {
    this.songService = songService;
    this.modelDtoConverter = modelDtoConverter;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Long> create(@RequestBody @Validated SongDto songDto) {
    var song = modelDtoConverter.convertToModel(songDto);
    var songId = songService.createSong(song);
    return Map.of("id", songId);
  }

  @GetMapping(path = "/{id}")
  public SongDto getById(@PathVariable("id") @NotNull Long id) {
    var song = songService.findById(id);
    return modelDtoConverter.convertToDto(song);
  }

  @GetMapping
  public Page<SongDto> getAll(Pageable pageable) {
    var songPage = songService.findAll(pageable);
    return new PageImpl<>(
        songPage.get().map(modelDtoConverter::convertToDto).collect(Collectors.toList()),
        pageable,
        songPage.getTotalElements());
  }

  @DeleteMapping
  public Map<String, List<Long>> deleteAllById(@RequestParam("ids") @NotNull List<Long> ids) {
    var deletedIds = songService.deleteAllById(ids);
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
    var fieldError = ex.getFieldError();
    return Map.of("message", fieldError.getField() + " " + fieldError.getDefaultMessage());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Map<String, String> handleValidationException(Exception ex) {
    return Map.of("message", ex.getMessage());
  }
}
