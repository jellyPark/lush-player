package com.lush.transport.model;

import org.springframework.context.annotation.ComponentScan;

/**
 * Models a request to a service.
 */
@ComponentScan
public class Request {

  /**
   * Endpoint/resource on the requested service.
   */
  private String resource;

  /**
   * Transfer protocol to access the service with.
   */
  private String protocol;
}
