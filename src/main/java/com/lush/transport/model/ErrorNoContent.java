package com.lush.transport.model;

import com.lush.transport.Transport;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Data
public class ErrorNoContent {

  private Transport service;

}
