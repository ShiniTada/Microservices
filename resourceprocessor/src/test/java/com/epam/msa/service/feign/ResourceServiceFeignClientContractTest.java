package com.epam.msa.service.feign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
    ids = "com.epam.msa:resourceservice:+:stubs:8082",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ResourceServiceFeignClientContractTest {

  @Autowired private ResourceServiceFeignClient resourceClient;

  @Test
  public void findResourceById() {
    var response = resourceClient.getById(1L);
    var bytes = response.getBody().get("file");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("someBytes", new String(bytes, StandardCharsets.UTF_8));
  }
}
