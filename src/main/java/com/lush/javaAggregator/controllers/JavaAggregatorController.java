package com.lush.javaAggregator.controllers;


import com.lush.javaAggregator.exceptions.BaseException;
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


  /**
   * Get service.
   *
   * @return ResponseEntity
   */
  @GetMapping
  public ResponseEntity<Object> get(@RequestBody HttpServletRequest request) {

    Response response = new Response();

    //get uri
    String url = util.getUri();

    //setRequest(가공-토큰추출 및 리퀘스트가공)
    //HttpRequest setRequest = util.setRequest(request);

    //get token value
    String tokenKey = "";
    //String tokenkey = util.getTokenKey(setRequest);

    if (tokenKey != null && !"".equals(tokenKey)) {
//      Map<String, Object> serviceResponse = util.callService(tokenKey, setRequest);
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
  public ResponseEntity<Object> post(@RequestBody HttpServletRequest request,
      Map<String, Object> params) {

    Response response = new Response();
    //setRequest(가공-토큰추출 및 리퀘스트가공)
    //HttpRequest setRequest = util.setRequest(request, params);

    //get token value
    String tokenKey = "";
    //String tokenkey = util.getTokenKey(setRequest);

    if (tokenKey != null && !"".equals(tokenKey)) {
//      Map<String, Object> serviceResponse = util.callService(tokenKey, setRequest);
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

    //setRequest(가공-토큰추출 및 리퀘스트가공)
    //HttpRequest setRequest = util.setRequest(request, params);

    //get token value
    String tokenKey = "";
    //String tokenkey = util.getTokenKey(setRequest);

    if (tokenKey != null && !"".equals(tokenKey)) {
//      Map<String, Object> serviceResponse = util.callService(tokenKey, setRequest);
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

    //setRequest(가공-토큰추출 및 리퀘스트가공)
    //HttpRequest setRequest = util.setRequest(request, params);

    //get token value
    String tokenKey = "";
    //String tokenkey = util.getTokenKey(setRequest);

    if (tokenKey != null && !"".equals(tokenKey)) {
//      Map<String, Object> serviceResponse = util.callService(tokenKey, setRequest);
//
//      response = util.bindingResponse(serviceResponse);

      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }
  }


}
