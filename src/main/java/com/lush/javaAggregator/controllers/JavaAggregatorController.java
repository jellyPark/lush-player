package com.lush.javaAggregator.controllers;


import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.utils.HttpUtil;
import com.lush.util.Util;
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

  /**
   * Get service.
   *
   * @return ResponseEntity
   */
  @GetMapping
  public ResponseEntity<Object> get() {

    Response response = new Response();

    //setRequest(토큰추출 및 리퀘스트가공)
    //Map<String, Object> setRequest = util.setRequest(request);
    //get url
    String url = util.getUrl(request);
    System.out.println("####  utl :  " + url);

    //get serviceName
    String serviceName = util.getServiceName(url);
    System.out.println("#### serviceName  :  " + serviceName);
//      Map<String, Object> serviceResponse = serviceNameAggregator.serviceNameAggregator("GET",
//         url, serviceName, new HashMap<String, Object>(), HttpServletRequest request);

//      response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  /**
   * Post service.
   *
   * @param params
   * @return ResponseEntity
   */
  @PostMapping
  public ResponseEntity<Object> post(@RequestBody Map<String, Object> params) {

    Response response = new Response();

    //get uri
    String uri = "";

    //get serviceName
    String serviceName = "";

//      Map<String, Object> serviceResponse = serviceNameAggregator.serviceNameAggregator("POST",
//         url, serviceName, params, HttpServletRequest request);

//      response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);

  }

  @PutMapping
  public ResponseEntity<Object> put(@RequestBody Map<String, Object> params) {

    Response response = new Response();

    //get uri
    String uri = "";

    //get serviceName
    String serviceName = "";

//      Map<String, Object> serviceResponse = serviceNameAggregator.serviceNameAggregator("PUT",
//         url, serviceName, params, HttpServletRequest request);

//      response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<Object> delete() {

    Response response = new Response();

    //get uri
    String uri = "";

    //get serviceName
    String serviceName = "";

//      Map<String, Object> serviceResponse = serviceNameAggregator.serviceNameAggregator("DELETE",
//         url, serviceName, new HashMap<String, Object>(), HttpServletRequest request);

//      response = util.bindingResponse(serviceResponse);
    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }


}
