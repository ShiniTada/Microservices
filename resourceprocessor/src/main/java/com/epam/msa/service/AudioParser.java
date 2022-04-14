package com.epam.msa.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.msa.domain.SongDto;

@Service
public class AudioParser {

  private static final String PROPERTY_TITLE = "dc:title";
  private static final String PROPERTY_ARTIST = "dc:creator";
  private static final String PROPERTY_ALBUM = "xmpDM:album";
  private static final String PROPERTY_DURATION_MS = "xmpDM:duration";
  private static final String PROPERTY_YEAR = "xmpDM:releaseDate";

  public static final String DEFAULT_UNKNOWN = "unknown";
  public static final String DEFAULT_LENGTH = "00:00";
  public static final int DEFAULT_YEAR = 1900;

  private static final Logger logger = LoggerFactory.getLogger(AudioParser.class);

  public SongDto parseAudioMetadata(byte[] audioFile) {
    try {
      InputStream input = new ByteArrayInputStream(audioFile);
      ContentHandler handler = new DefaultHandler();
      Metadata metadata = new Metadata();
      Parser parser = new Mp3Parser();
      ParseContext parseCtx = new ParseContext();

      parser.parse(input, handler, metadata, parseCtx);
      input.close();

      String duration = parseDuration(metadata.get(PROPERTY_DURATION_MS));
      String sYear = metadata.get(PROPERTY_YEAR);
      int year = sYear != null ? Integer.parseInt(sYear) : 0;

      SongDto songDto =
          SongDto.builder()
              .name(metadata.get(PROPERTY_TITLE))
              .artist(metadata.get(PROPERTY_ARTIST))
              .album(metadata.get(PROPERTY_ALBUM))
              .length(duration)
              .year(year)
              .build();
      setupDefaultValues(songDto);
      return songDto;
    } catch (IOException | SAXException | TikaException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      SongDto songDto = new SongDto();
      setupDefaultValues(songDto);
      return songDto;
    }
  }

  private String parseDuration(String duration) {
    Double millisDouble = duration != null ? Double.parseDouble(duration) : 0;
    Long millis = millisDouble.longValue();
    return String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
  }

  protected void setupDefaultValues(SongDto songDto) {
    if (songDto.getName() == null || songDto.getName().equals("")) {
      songDto.setName(DEFAULT_UNKNOWN);
    }
    if (songDto.getArtist() == null || songDto.getArtist().equals("")) {
      songDto.setArtist(DEFAULT_UNKNOWN);
    }
    if (songDto.getAlbum() == null || songDto.getAlbum().equals("")) {
      songDto.setAlbum(DEFAULT_UNKNOWN);
    }
    if (songDto.getLength() == null || songDto.getLength().equals("")) {
      songDto.setLength(DEFAULT_LENGTH);
    }
    if (songDto.getYear() == 0) {
      songDto.setYear(DEFAULT_YEAR);
    }
  }
}
