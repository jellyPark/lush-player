package com.lush.core.model;

import org.springframework.stereotype.Component;

/**
 * A standardised response format for a microservice.
 */
@Component
public class ResponseDto {

  /**
   * Can be 'ok' or 'fail'.
   * (json:"status")
   */
  private String status;

  /**
   * Any valid HTTP response code.
   * (json:"code")
   */
  private Integer code;

  /**
   * Any relevant message (optional).
   * (json:"message")
   */
  private String message;

  /**
   * Data to pass along to the response (optional).
   * (json:"data,omitempty")
   */
  private Object data;
}
