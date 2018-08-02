package com.lush.javaAggregator.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.stream.CharacterStreamReadingMessageSource;
import org.springframework.integration.transformer.ObjectToMapTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;

@Configuration
@IntegrationComponentScan
@EnableIntegration
public class g {

  @Bean
  public PollableChannel bridgeFromInput() {
    return new QueueChannel();
  }

  @Bean
  @BridgeFrom(value = "bridgeFromInput", poller = @Poller(fixedDelay = "1000"))
  public MessageChannel bridgeFromOutput() {
    return new DirectChannel();
  }

  @Bean
  public QueueChannel bridgeToOutput() {
    return new QueueChannel();
  }

  @Bean
  @BridgeTo("bridgeToOutput")
  public MessageChannel bridgeToInput() {
    return new DirectChannel();
  }

  @Bean
  @InboundChannelAdapter(value = "inputChannel", poller = @Poller(fixedDelay = "1000"))
  public MessageSource<String> consoleSource() {
    return CharacterStreamReadingMessageSource.stdin();
  }

  @Bean
  @Transformer(inputChannel = "inputChannel", outputChannel = "httpChannel")
  public ObjectToMapTransformer toMapTransformer() {
    return new ObjectToMapTransformer();
  }

  @Bean
  @ServiceActivator(inputChannel = "httpChannel")
  public MessageHandler httpHandler() {
    HttpRequestExecutingMessageHandler handler = new HttpRequestExecutingMessageHandler(
        "http://foo/service");
    handler.setExpectedResponseType(String.class);
    handler.setOutputChannelName("outputChannel");
    return handler;
  }

  @Bean
  @ServiceActivator(inputChannel = "outputChannel")
  public LoggingHandler loggingHandler() {
    return new LoggingHandler("info");
  }
}
