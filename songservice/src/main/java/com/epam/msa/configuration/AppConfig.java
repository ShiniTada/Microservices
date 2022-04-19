package com.epam.msa.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@EnableRetry
@Configuration
public class AppConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public RetryTemplate retryTemplate() {
    var retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(4);
    var backOffPolicy = new FixedBackOffPolicy();
    backOffPolicy.setBackOffPeriod(3000);

    var template = new RetryTemplate();
    template.setRetryPolicy(retryPolicy);
    template.setBackOffPolicy(backOffPolicy);
    return template;
  }
}
