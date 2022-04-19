# Command to run:

gradle build java -jar build/libs/resourceservice-0.0.1-SNAPSHOT.jar

# Contract tests
For contract tests you need

**As a producer:** 
1. create gradle tasks. See build.gradle in this microservice
2. create in test/resources/contracts *.groovy files with description of request and response
3. create ContractVerifierBase in tests
4. generate and test contract tests. Commands:
    ./gradlew generateContractTests
    ./gradlew generateContractTests test
    Generated tests would bw in build/generated-test-sources
5. then copy contracts and generate client stubs (see build.gradle tasks)
6. publish stubs in mavenLocal() repository (also see build.gradle)

**As a consumer:**
1. update build.gradle. See resourceprocessor microservice
2. add tests with @AutoConfigureStubRunner (see resourceprocessor microservice)
3. if needed update main/resources/application.yml

### How to create contract tests
sync: https://developer.okta.com/blog/2022/02/01/spring-cloud-contract#create-the-producer-contract

async: https://developer.epages.com/blog/tech-stories/how-to-test-eventbased-services-using-contracts/