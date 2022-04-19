package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return audio file by id=1"

    request {
        url "/resources/1"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body("file": "c29tZUJ5dGVz")
    }

}