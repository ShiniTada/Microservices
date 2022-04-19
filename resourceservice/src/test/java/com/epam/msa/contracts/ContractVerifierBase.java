package com.epam.msa.contracts;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

import com.epam.msa.ResourceServiceApplication;
import com.epam.msa.domain.AudioFile;
import com.epam.msa.service.AudioFileService;
import com.epam.msa.service.ProducerService;
import com.epam.msa.web.AudioFileController;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(classes = ResourceServiceApplication.class)
@AutoConfigureMessageVerifier
public class ContractVerifierBase {

  @Autowired AudioFileController audioFileController;
  @MockBean AudioFileService audioFileService;

  @Autowired private ProducerService producerService;

  @BeforeEach
  public void setup() {
    RestAssuredMockMvc.standaloneSetup(audioFileController);
    var bytes = "someBytes".getBytes(StandardCharsets.UTF_8);
    Mockito.when(audioFileService.download(1L))
        .thenReturn(new AudioFile(1L, "Test Resource 1", 10000L, new ByteArrayInputStream(bytes)));
  }

  public void emitResourceCreatedEvent() {
    producerService.sendMessage(123L);
  }
}
