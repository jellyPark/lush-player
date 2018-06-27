package com.lush.transport.model;

import lombok.Data;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@Data
public class RemoteResource {

  private Communication service;

  private Request request;
}
