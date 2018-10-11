package com.lush.javaAggregator.controllers;


import com.lush.javaAggregator.aggregator.ServiceNameAggregator;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.utils.HttpUtil;
import com.lush.util.Util;
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
public class JavaAggregatorController {

  static final Logger logger = LoggerFactory.getLogger(JavaAggregatorController.class);

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
  private ServiceNameAggregator serviceNameAggregator;

  /**
   * Get service.
   *
   * @return ResponseEntity
   */
  @GetMapping
  public ResponseEntity<Object> get() {

    Response response = new Response();

    //get url
    String url = util.getUrl(request);
    System.out.println("## getUrl : " + url);

    //get serviceName
    String serviceName = util.getServiceName(url);
    System.out.println("## getServiceName : " + serviceName);

    //      Map<String, Object> serviceResponse = serviceNameAggregator.callServiceNameAggregator("GET",
//         url, serviceName, new HashMap<String, Object>(), HttpServletRequest request);
    System.out.println("####### Call Aggregator #######");

//      response = util.bindingResponse(serviceResponse);
    System.out.println("####### return responseEntity #######");
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

    //get url
    String url = util.getUrl(request);

    //get serviceName
    String serviceName = util.getServiceName(url);

//      Map<String, Object> serviceResponse = serviceNameAggregator.callServiceNameAggregator("POST",
//         url, serviceName, params, HttpServletRequest request);

//      response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);

  }

  @PutMapping
  public ResponseEntity<Object> put(@RequestBody Map<String, Object> params) {

    Response response = new Response();

    //get url
    String url = util.getUrl(request);

    //get serviceName
    String serviceName = util.getServiceName(url);

//      Map<String, Object> serviceResponse = serviceNameAggregator.callServiceNameAggregator("PUT",
//         url, serviceName, params, HttpServletRequest request);

//      response = util.bindingResponse(serviceResponse);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<Object> delete() {

    Response response = new Response();

    //get url
    String url = util.getUrl(request);

    //get serviceName
    String serviceName = util.getServiceName(url);

//      Map<String, Object> serviceResponse = serviceNameAggregator.callServiceNameAggregator("DELETE",
//         url, serviceName, new HashMap<String, Object>(), HttpServletRequest request);

//      response = util.bindingResponse(serviceResponse);
    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }


}
