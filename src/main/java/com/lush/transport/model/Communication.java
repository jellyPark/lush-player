package com.lush.transport.model;

import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import sun.net.www.http.HttpClient;

/**
 * Responsible for communication with a service.
 */
@ComponentScan
@Data
public class Communication {

  /**
   * VCS branch the service is built from.
   */
  private String branch;

  /**
   * Current HTTP request being actioned.
   */
  private HttpRequest currentRequest;

  /**
   * CI environment the service operates in.
   */
  private String environment;

  /**
   * Namespace of the service.
   */
  private String namespace;

  /**
   * Name of the service.
   */
  private String name;

  /**
   * Major API version of the service.
   */
  private int version;

  /**
   * http client implementation.
   */
  private HttpClient client;
}
