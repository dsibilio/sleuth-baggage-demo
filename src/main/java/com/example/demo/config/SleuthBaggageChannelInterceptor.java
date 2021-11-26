package com.example.demo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.autoconfig.SleuthBaggageProperties;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import brave.baggage.BaggageField;

@Component
@GlobalChannelInterceptor
@EnableConfigurationProperties(SleuthBaggageProperties.class)
public class SleuthBaggageChannelInterceptor implements ChannelInterceptor {

  private final SleuthBaggageProperties baggageProperties;

  public SleuthBaggageChannelInterceptor(SleuthBaggageProperties baggageProperties) {
    this.baggageProperties = baggageProperties;
  }

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    baggageProperties.getLocalFields().forEach(key -> {
      if(baggageProperties.getCorrelationFields().contains(key) && message.getHeaders().get(key, String.class) != null) {
        BaggageField.getByName(key).updateValue(message.getHeaders().get(key, String.class));
      }
    });
    
    return message;
  }

}
