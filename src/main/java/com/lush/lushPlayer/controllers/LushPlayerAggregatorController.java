package com.lush.lushPlayer.controllers;


import com.lush.lushPlayer.aggregator.LushPlayerAggregator;
import com.lush.lushPlayer.modles.Response;
import com.lush.lushPlayer.utils.HttpUtil;
import com.lush.util.Util;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LushPlayerAggregatorController {

  static final Logger logger = LoggerFactory.getLogger(LushPlayerAggregatorController.class);

  @Autowired
  private Util util;

  /**
   * Define Utils for get response headers.
   */
  @Autowired
  private HttpUtil httpUtil;

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private LushPlayerAggregator lushPlayerAggregator;

  /**
   * Get service.
   *
   * @return ResponseEntity
   */
  @GetMapping
  public ResponseEntity<Object> get() {

    // Get url.
    String url = util.getUrl(request);

    // Get serviceName.
    String serviceName = util.getServiceName(url);

    // Call service aggregator.
    Map<String, Object> serviceResponse = lushPlayerAggregator.callServiceNameAggregator("GET",
        url, serviceName, new HashMap<String, Object>(), request);

    // Change data map type to response type.
    Response response = new Response();
    response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);

  }

  /**
   * Post service.
   *
   * @param params
   * @return ResponseEntity
   */
  @PostMapping
  public ResponseEntity<Object> post(@RequestBody Map<String, Object> params) throws Exception {

    // Get url.
    String url = util.getUrl(request);

    // Get serviceName.
    String serviceName = util.getServiceName(url);

    // Call service aggregator.
    Map<String, Object> serviceResponse = lushPlayerAggregator.callServiceNameAggregator("POST",
        url, serviceName, params, request);

    // Change data map type to response type.
    Response response = new Response();
    response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);

  }

  /**
   * Put service.
   *
   * @param params
   * @return ResponseEntity
   */
  @PutMapping
  public ResponseEntity<Object> put(@RequestBody Map<String, Object> params) {

    // Get url.
    String url = util.getUrl(request);

    // Get serviceName.
    String serviceName = util.getServiceName(url);

    // Call service aggregator.
    Map<String, Object> serviceResponse = lushPlayerAggregator.callServiceNameAggregator("PUT",
        url, serviceName, params, request);

    // Change data map type to response type.
    Response response = new Response();
    response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);

  }

  /**
   * Delete servcie.
   *
   * @return ResponseEntity
   */
  @DeleteMapping
  public ResponseEntity<Object> delete() {

    // Get url.
    String url = util.getUrl(request);

    // Get serviceName.
    String serviceName = util.getServiceName(url);

    // Call service aggregator.
    Map<String, Object> serviceResponse = lushPlayerAggregator.callServiceNameAggregator("DELETE",
        url, serviceName, new HashMap<String, Object>(), request);

    // Change data map type to response type.
    Response response = new Response();
    response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);

  }

}
