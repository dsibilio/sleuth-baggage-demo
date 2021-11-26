package com.example.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);
  private final SubscribableChannel inputChannel;

  public DemoController(SubscribableChannel inputChannel) {
    this.inputChannel = inputChannel;
  }

  @GetMapping("/demo")
  public String httpEndpoint() {
    MDC.getCopyOfContextMap().forEach((key, value) -> LOG.info("HTTP MDC: {} => {}", key, value));
    inputChannel.send(MessageBuilder.withPayload("demo-event")
        .setHeader("event-custom-header", "test-event-header")
        .build());
    return "Just a demo endpoint";
  }

  @ServiceActivator(inputChannel = "inputChannel")
  public void eventEndpoint(Message<String> message) {
    MDC.getCopyOfContextMap().forEach((key, value) -> LOG.info("Event MDC: {} => {}", key, value));
  }

}
