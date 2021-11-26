package com.example.demo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.autoconfig.SleuthBaggageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.baggage.BaggageField;
import brave.baggage.BaggageFields;
import brave.baggage.BaggagePropagationConfig.SingleBaggageField;
import brave.baggage.BaggagePropagationCustomizer;
import brave.baggage.CorrelationScopeConfig.SingleCorrelationField;
import brave.baggage.CorrelationScopeCustomizer;
import brave.baggage.CorrelationScopeDecorator.Builder;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext.ScopeDecorator;

@Configuration
@EnableConfigurationProperties(SleuthBaggageProperties.class)
public class SleuthBaggageConfiguration {

  private final SleuthBaggageProperties baggageProperties;

  public SleuthBaggageConfiguration(SleuthBaggageProperties baggageProperties) {
    this.baggageProperties = baggageProperties;
  }

  @Bean
  BaggagePropagationCustomizer baggagePropagationCustomizer() {
    BaggageField buildNumber = BaggageFields.constant("build-number", "12345");
    return builder -> builder.add(SingleBaggageField.remote(buildNumber));
  }

  @Bean
  CorrelationScopeCustomizer correlationScopeCustomizer() {
    BaggageField buildNumber = BaggageFields.constant("build-number", "12345");
    return builder -> builder.add(SingleCorrelationField.create(buildNumber));
  }

  @Bean
  ScopeDecorator mdcScopeDecorator() {
    Builder scopeDecoratorBuilder = MDCScopeDecorator.newBuilder().clear();

    for (String localField : baggageProperties.getLocalFields()) {
      if(baggageProperties.getCorrelationFields().contains(localField)) {
        scopeDecoratorBuilder.add(SingleCorrelationField.newBuilder(BaggageField.create(localField))
            .flushOnUpdate()
            .build());
      }
    }

    return scopeDecoratorBuilder.build();
  }

}
