package com.lush.javaAggregator.integration;

//@Configuration
//@IntegrationComponentScan
//@EnableIntegration
public class IntegrationConfig {

//  @Bean
//  public PollableChannel bridgeFromInput() {
//    return new QueueChannel();
//  }
//
//  @Bean
//  @BridgeFrom(value = "bridgeFromInput", poller = @Poller(fixedDelay = "1000"))
//  public MessageChannel bridgeFromOutput() {
//    return new DirectChannel();
//  }
//
//  @Bean
//  public QueueChannel bridgeToOutput() {
//    return new QueueChannel();
//  }
//
//  @Bean
//  @BridgeTo("bridgeToOutput")
//  public MessageChannel bridgeToInput() {
//    return new DirectChannel();
//  }
//
//  @Bean
//  @InboundChannelAdapter(value = "inputChannel", poller = @Poller(fixedDelay = "1000"))
//  public MessageSource<String> consoleSource() {
//    return CharacterStreamReadingMessageSource.stdin();
//  }
//
//  @Bean
//  @Transformer(inputChannel = "inputChannel", outputChannel = "httpChannel")
//  public ObjectToMapTransformer toMapTransformer() {
//    return new ObjectToMapTransformer();
//  }
//
//  @Bean
//  @ServiceActivator(inputChannel = "httpChannel")
//  public MessageHandler httpHandler() {
//    HttpRequestExecutingMessageHandler handler = new HttpRequestExecutingMessageHandler(
//        "http://foo/service");
//    handler.setExpectedResponseType(String.class);
//    handler.setOutputChannelName("outputChannel");
//    return handler;
//  }
//
//  @Bean
//  @ServiceActivator(inputChannel = "outputChannel")
//  public LoggingHandler loggingHandler() {
//    return new LoggingHandler("info");
//  }
}
