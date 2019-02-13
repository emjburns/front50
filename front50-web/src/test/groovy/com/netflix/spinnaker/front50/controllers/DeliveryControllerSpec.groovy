package com.netflix.spinnaker.front50.controllers

import com.netflix.spinnaker.front50.exceptions.InvalidRequestException
import com.netflix.spinnaker.front50.model.delivery.Delivery
import com.netflix.spinnaker.front50.model.delivery.DeliveryRepository
import spock.lang.Specification
import spock.lang.Subject

class DeliveryControllerSpec extends Specification {

  def configRepository = Mock(DeliveryRepository)

  @Subject
  def controller = new DeliveryController(
    deliveryRepository: configRepository
  )

  def "reject deletion if config id is not in the right account"() {
    given:
    def config = new Delivery(
      id: "aaa",
      application: "ooboy",
      artifacts: [],
      environments: []
    )

    when:
    configRepository.findById("aaa") >> config
    controller.deleteConfig("myapp", "aaa")

    then:
    thrown(InvalidRequestException)
  }

  def "reject new config if application doesn't match request url"() {
    given:
    def config = new Delivery(
      id: "aaa",
      application: "ooboy",
      artifacts: [],
      environments: []
    )

    when:
    controller.createConfig("myapp", config)

    then:
    thrown(InvalidRequestException)
  }

  def "reject update to config if application doesn't match request url"() {
    given:
    def config = new Delivery(
      id: "aaa",
      application: "ooboy",
      artifacts: [],
      environments: []
    )

    when:
    controller.upsertConfig("myapp", config)

    then:
    thrown(InvalidRequestException)
  }
}
