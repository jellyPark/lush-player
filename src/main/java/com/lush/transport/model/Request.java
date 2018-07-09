package com.lush.transport.model;

import java.net.URL;
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
   * Query string values.
   * Java에서는 Query를 바로 사용할수가 없어서 URL 받음.
   */
  private URL url;

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
