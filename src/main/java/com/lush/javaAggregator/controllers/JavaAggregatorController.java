package com.lush.javaAggregator.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lush.javaAggregator.enums.ExceptionType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.utils.HttpUtil;
import com.lush.util.Util;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaAggregatorController {

  static final Logger logger = LoggerFactory.getLogger(JavaAggregatorController.class);

  @Autowired
  private Util util;


  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private MessageSource messageSource;

  /**
   * Define Utils for get response headers.
   */
  @Autowired
  private HttpUtil httpUtil;


  @GetMapping(value = "/sampleGetUri")
  public String sampleGetUri() {
    return util.getUri();
  }

  @GetMapping(value = "/validation/{id}")
  public ResponseEntity<Object> validation(@PathVariable long id) throws Exception {

    if (id < 1) {
      /* Error Log Example */
      logger.info("Error  : " + ExceptionType.INVALID_ID_VALUE.getMassage());
      throw new BaseException().setCommonExceptoin(ExceptionType.INVALID_ID_VALUE);
    }

    if (util.checkPageNum()) {
    } else {
      throw new BaseException().setCommonExceptoin(ExceptionType.INVALID_ID_VALUE);
      //추후에 페이지관련 ExceptionType 추가...
    }

    Response response = new Response();
    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

//  @PostMapping(value = "/{targetService}")
//  public ResponseEntity<Object> postTest(@PathVariable String targetService,
//      @RequestBody Map<String, Object> param) throws Exception {
//
//    logger.info("=============== " + targetService + "==================");
//    /* Service Log Example :  util.getMethodType  */
//    String targetMethodType = util.getMethodType();
//    /* Request Log Example */
//    logger.info("RequestParams : " + param);
//    StringBuffer stringParamBuffer = new StringBuffer();
//
//    for (String key : param.keySet()) {
//      logger.info("key : " + key + "/ value : " + param.get(key));
//      stringParamBuffer.append(param.get(key));
//    }
//
//    String stringParam = stringParamBuffer.toString();
//
//    Response response = util.callService(targetMethodType, stringParam);
//
//    /* Response Log Example */
//    logger.info("Response :  " + response.toString());
//
//    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
//
//  }

  @GetMapping(value = "/{targetService}/{endpoint}")
  public ResponseEntity<Object> getTest(@PathVariable String targetService,
      @PathVariable String endpoint) {

    String targetMethodType = util.getMethodType();
    Response response = util.callService(targetMethodType, null);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{targetService}/{endpoint}")
  public ResponseEntity<Object> deleteTest(@PathVariable String targetService,
      @PathVariable long endpoint) {

    logger.info("deleteTest");
    String targetMethodType = util.getMethodType();
    Response response = util.callService(targetMethodType, null);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  @PostMapping(value = "/login")
  public ResponseEntity<Object> signin(@RequestBody Map<String, Object> param) {

    String targetMethodType = util.getMethodType();
    Response response = util.callService(targetMethodType, param);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

}
