package com.epam.msa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.Data;

@Data
@Configuration
public class AwsS3Config {

  @Value("${aws.region}")
  private String region;

  @Value("${aws.s3.url}")
  private String s3EndpointUrl;
  //
  //  @Value("${aws.s3.access-key}")
  //  private String accessKey;
  //
  //  @Value("${aws.s3.secret-key}")
  //  private String secretKey;

  @Bean
  AmazonS3 amazonS3() {
    AwsClientBuilder.EndpointConfiguration endpointConfiguration =
        new AwsClientBuilder.EndpointConfiguration(s3EndpointUrl, region);
    return AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(endpointConfiguration)
        .withPathStyleAccessEnabled(true)
        .build();
  }

  //  @Bean(name = "amazonS3")
  //  public AmazonS3 amazonS3() {
  //    return AmazonS3ClientBuilder.standard()
  //        .withCredentials(getCredentialsProvider())
  //        .withEndpointConfiguration(getEndpointConfiguration(s3EndpointUrl))
  //        .withPathStyleAccessEnabled(true)
  //        .build();
  //  }

  //  private EndpointConfiguration getEndpointConfiguration(String url) {
  //    return new EndpointConfiguration(url, region);
  //  }
  //
  //  private AWSStaticCredentialsProvider getCredentialsProvider() {
  //    return new AWSStaticCredentialsProvider(getBasicAWSCredentials());
  //  }
  //
  //  private BasicAWSCredentials getBasicAWSCredentials() {
  //    return new BasicAWSCredentials(accessKey, secretKey);
  //  }
}
