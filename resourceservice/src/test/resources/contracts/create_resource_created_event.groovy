package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should create resource created event"
    label 'user.routingkey'
    input {
        // the contract will be triggered by a method
        triggeredBy('emitResourceCreatedEvent()')
    }
    outputMessage {
        sentTo('user.exchange') //in our case the destination is an exchange name
        body(123L)
    }

}