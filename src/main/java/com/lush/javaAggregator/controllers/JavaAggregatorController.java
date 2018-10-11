package com.lush.javaAggregator.controllers;


import com.lush.javaAggregator.aggregator.ServiceNameAggregator;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.utils.HttpUtil;
import com.lush.util.Util;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JavaAggregatorController {

  static final Logger logger = LoggerFactory.getLogger(JavaAggregatorController.class);

  @Autowired
  private Util util;

  @Autowired
  private MessageSource messageSource;

  /**
   * Define Utils for get response headers.
   */
  @Autowired
  private HttpUtil httpUtil;

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private ServiceNameAggregator serviceNameAggregator;

  /**
   * Get service.
   *
   * @return ResponseEntity
   */
  @GetMapping(value = "/get")
  public ResponseEntity<Object> get() {

    Response response = new Response();

    //get url
    String url = util.getUrl(request);
//    String url = util.setServiceURL("/podcasts/podcasts"); // test
    System.out.println("####  url :  " + url);

    //get serviceName
    String serviceName = util.getServiceName(url);
    System.out.println("#### serviceName  :  " + serviceName);
    Map<String, Object> serviceResponse = serviceNameAggregator.serviceNameAggregator("GET",
         url, serviceName, new HashMap<String, Object>(), request);

    response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  /**
   * Post service.
   *
   * @param params
   * @return ResponseEntity
   */
  @PostMapping("/post")
  public ResponseEntity<Object> post(@RequestBody Map<String, Object> params) throws Exception{

    Response response = new Response();

    //get url
    String url = util.getUrl(request);
//    String url = util.setServiceURL("/podcasts/podcasts"); //test

    //get serviceName
    String serviceName = "";

      Map<String, Object> serviceResponse = serviceNameAggregator.serviceNameAggregator("POST",
         url, serviceName, params, request);

      response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);

  }

  @PutMapping("/put")
  public ResponseEntity<Object> put(@RequestBody Map<String, Object> params) {

    Response response = new Response();

    //get url
    String url = util.getUrl(request);
//    String url = util.setServiceURL("/podcasts/podcasts/41"); //test

    //get serviceName
    String serviceName = "";

      Map<String, Object> serviceResponse = serviceNameAggregator.serviceNameAggregator("PUT",
         url, serviceName, params, request);

      response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  @DeleteMapping("delete")
  public ResponseEntity<Object> delete() {

    Response response = new Response();

    //get url
    String url = util.getUrl(request);
//    String url = util.setServiceURL("/podcasts/podcasts/41");  //test

    //get serviceName
    String serviceName = "";

      Map<String, Object> serviceResponse = serviceNameAggregator.serviceNameAggregator("DELETE",
         url, serviceName, new HashMap<String, Object>(), request);

      response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }


}
