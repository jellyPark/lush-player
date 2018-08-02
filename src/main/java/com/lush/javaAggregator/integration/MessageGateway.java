package com.lush.javaAggregator.integration;

import java.util.List;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {

  @Gateway(requestChannel = "inputChennel")
  void sendMessage(List<Object> payloads);
}
