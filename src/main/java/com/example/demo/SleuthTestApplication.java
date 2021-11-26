package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
public class SleuthTestApplication {

  @Bean
  SubscribableChannel inputChannel() {
    return new DirectChannel();
  }

  public static void main(String[] args) {
    SpringApplication.run(SleuthTestApplication.class, args);
  }

}