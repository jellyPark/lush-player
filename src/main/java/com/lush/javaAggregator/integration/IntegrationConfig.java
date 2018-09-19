package com.lush.javaAggregator.integration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class IntegrationConfig {

  @Bean
  @Description("Entry to the messaging system through the gateway.")
  public MessageChannel requestChannel() {
    return new DirectChannel();
  }

  @Bean
  @Description("Sends request messages to the web service outbound gateway")
  public MessageChannel invocationChannel() {
    return new DirectChannel();
  }

  @Bean
  @Description("Sends web service responses to both the client and a database")
  public MessageChannel responseChannel() {
    return new PublishSubscribeChannel();
  }

  @Bean
  public MessageChannel httpRequestChannel() {
    return new QueueChannel();
  }

}