package com.epam.msa.service;

import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import com.epam.msa.ResourceProcessorApplication;

@SpringBootTest(classes = ResourceProcessorApplication.class)
@AutoConfigureStubRunner(
    ids = "com.epam.msa:resourceservice:+:stubs",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ConsumerServiceContractTest {

  @Autowired
  private StubTrigger stubTrigger;

  @MockBean
  Processor processor;

  @Test
  public void shouldHandleResourceCreatedEvent() {
    Long resourceId = 1L;
    doReturn(resourceId).when(processor).process(resourceId);
    boolean isTriggered = stubTrigger.trigger("user.routingkey");
    assertTrue(isTriggered);
  }
}
