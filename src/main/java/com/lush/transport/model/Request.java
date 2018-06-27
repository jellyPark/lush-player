package com.lush.transport.model;

import java.util.Map;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

/**
 * Models a request to a service.
 */
@ComponentScan
@Data
public class Request {

  /**
   * HTTP method/verb for the request.
   */
  private String method;

  /**
   * Endpoint/resource on the requested service.
   */
  private String resource;

  /**
   * Transfer protocol to access the service with.
   */
  private String protocol;

  /**
   * Headers to pass with the request.
   */
  private Map<String, String> headers;
}
