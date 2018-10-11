package com.lush.javaAggregator.controllers;


import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.utils.HttpUtil;
import com.lush.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Scanner;
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

    //get uri
    logger.info("####  request : " + request.getMethod());
    System.out.println("#### TEST");

    //setRequest(토큰추출 및 리퀘스트가공)
    //Map<String, Object> setRequest = util.setRequest(request);
//    String header = request.getHeader("Authorization"); // 토큰 추출
    // Request Parameter 의 내용은 각 서비스에서 그에 맞게 가공 필요


    //setEndpoint(엔드포인트 추출)
    String endpoint = "";
    //endpoint = util.getEndPoint(url);

    //get token value
    String tokenKey = "";
    //String tokenkey = util.getTokenKey(setRequest);

    if (tokenKey != null && !"".equals(tokenKey)) {
//      Map<String, Object> serviceResponse = util.callService(endpoint, tokenKey, setRequest);
//
//      response = util.bindingResponse(serviceResponse);
      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }

  }


  /**
   * Post service.
   *
   * @param params
   * @return ResponseEntity
   */
  @PostMapping
  public ResponseEntity<Object> post(@RequestBody Map<String, Object> params) throws Exception{


    Response response = new Response();

    //get uri
    System.out.println("#### request :  " + request.getMethod());
    System.out.println("#### params :  " + params.toString());
    System.out.println("#### TEST");

    //setRequest(토큰추출 및 리퀘스트가공)
    //Map<String, Object> setRequest = util.setRequest(request);
    Enumeration bodtyParams = request.getParameterNames();
    while(bodtyParams.hasMoreElements()){
      String paramName = (String)bodtyParams.nextElement();
      System.out.println("##############" + paramName + " = " + request.getParameter(paramName));
    }

    //setEndpoint(엔드포인트 추출)
    String endpoint = "";
    //endpoint = util.getEndPoint(url);

    //get token value
    String tokenKey = "";
    //String tokenkey = util.getTokenKey(setRequest);

    if (tokenKey != null && !"".equals(tokenKey)) {
//      Map<String, Object> serviceResponse = util.callService(endpoint, tokenKey, setRequest);
//
//      response = util.bindingResponse(serviceResponse);

      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }

  }

  @PutMapping
  public ResponseEntity<Object> put(@RequestBody Map<String, Object> params) {

    Response response = new Response();

    //get uri
    System.out.println("####  request : " + request.getMethod());
    System.out.println("#### TEST");

    //setRequest(토큰추출 및 리퀘스트가공)
    //Map<String, Object> setRequest = util.setRequest(request);

    //setEndpoint(엔드포인트 추출)
    String endpoint = "";
    //endpoint = util.getEndPoint(url);

    //get token value
    String tokenKey = "";
    //String tokenkey = util.getTokenKey(setRequest);

    if (tokenKey != null && !"".equals(tokenKey)) {
//      Map<String, Object> serviceResponse = util.callService(endpoint, tokenKey, setRequest);
//
//      response = util.bindingResponse(serviceResponse);

      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }
  }

  @DeleteMapping
  public ResponseEntity<Object> delete() {

    Response response = new Response();

    //get uri
    System.out.println("####  request : " + request.getMethod());
    System.out.println("#### TEST");

    //setRequest(토큰추출 및 리퀘스트가공)
    //Map<String, Object> setRequest = util.setRequest(request);

    //setEndpoint(엔드포인트 추출)
    String endpoint = "";
    //endpoint = util.getEndPoint(url);

    //get token value
    String tokenKey = "";
    //String tokenkey = util.getTokenKey(setRequest);

    if (tokenKey != null && !"".equals(tokenKey)) {
//      Map<String, Object> serviceResponse = util.callService(endpoint, tokenKey, setRequest);
//
//      response = util.bindingResponse(serviceResponse);

      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }
  }


}
