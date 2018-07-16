package com.lush.javaAggregator.utils;

import com.lush.javaAggregator.eums.ResponseStatusType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * Class for http protocol util.
 */
@Component
public class HttpUtil {

  /**
   * Get http headers.
   *
   * @return HttpHeaders
   */
  public HttpHeaders getResponseHeaders() {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

    return responseHeaders;
  }

  /**
   * Method name : responseValidation.
   * Description : Request Data Validation Check to Response.
   *
   * @return ResponseEntity
   */
  public ResponseEntity<Object> responseValidation(BindingResult result)
      throws Exception {
    Response response =
        new Response(ResponseStatusType.FAIL,
            HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getAllErrors().get(0)
            .getDefaultMessage());

    return new ResponseEntity<>(response, getResponseHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  /**
   * Method name : responseValidation.
   * Description : Request Data Validation Check to Response (User Message).
   *
   * @return ResponseEntity
   */
  public ResponseEntity<Object> responseValidation(BindingResult result, String message)
      throws Exception {
    Response response =
        new Response(ResponseStatusType.FAIL,
            HttpStatus.UNPROCESSABLE_ENTITY.value(), message);

    return new ResponseEntity<>(response, getResponseHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

}