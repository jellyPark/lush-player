package com.lush.transport.model;

import com.lush.javaAggregator.resources.Resource.Transport;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Data
public class ErrorNoContent {

  private Transport service;

}
