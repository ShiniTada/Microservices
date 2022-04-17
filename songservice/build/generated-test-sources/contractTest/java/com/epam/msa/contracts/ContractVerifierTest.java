package com.epam.msa.contracts;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

@SuppressWarnings("rawtypes")
public class ContractVerifierTest extends ContractVerifierBase {

  @Test
  public void validate_create_song() throws Exception {
    // given:
    MockMvcRequestSpecification request =
        given()
            .header("Content-Type", "application/json")
            .body(
                "{\"name\":\"We Are the Champions\",\"artist\":\"Queen\",\"album\":\"News of the World\",\"length\":\"3:14\",\"resourceId\":7,\"year\":1977}");

    // when:
    ResponseOptions response = given().spec(request).post("/songs");

    // then:
    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.header("Content-Type")).matches("application/json.*");

    // and:
    DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
    assertThatJson(parsedJson).field("['id']").isEqualTo("1");
  }
}
