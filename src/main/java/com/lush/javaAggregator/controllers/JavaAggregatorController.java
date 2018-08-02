package com.lush.javaAggregator.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.lush.javaAggregator.enums.ExceptionType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.utils.HttpUtil;
import com.lush.util.Util;
import java.net.URI;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@Slf4j
public class JavaAggregatorController {

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


  @GetMapping(value = "/findmethodtype")
  public String findMethodType() {
    return util.getMethodType();
  }

  @GetMapping(value = "/sampleGetUri")
  public String sampleGetUri() {
    return util.getUri();
  }

  @GetMapping(value = "/validation/{id}")
  public ResponseEntity<Object> validation(@PathVariable long id) throws Exception {

    if (id < 1) {
      throw new BaseException().setCommonExceptoin(ExceptionType.INVALID_ID_VALUE);
    }

    Response response = new Response();
    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  @PostMapping(value = "/{targetService}")
  public ResponseEntity<Object> postTest(@PathVariable String targetService,
      @RequestBody Map<String, Object> param) throws Exception {

    String targetMethodType = util.getMethodType();

    log.info("param : " + param);
    StringBuffer stringParamBuffer = new StringBuffer();

    for (String key : param.keySet()) {
      log.info("key : " + param.get(key));
      stringParamBuffer.append(param.get(key));
    }

    String stringParam = stringParamBuffer.toString();

    Response response = util.callService(targetMethodType, stringParam);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);

  }

  @GetMapping(value = "/{targetService}/{endpoint}")
  public ResponseEntity<Object> getTest(@PathVariable String targetService,
      @PathVariable String endpoint) {

    String targetMethodType = util.getMethodType();
    Response response = util.callService(targetMethodType, "");

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{targetService}/{endpoint}")
  public ResponseEntity<Object> deleteTest(@PathVariable String targetService,
      @PathVariable long endpoint) {

    log.info("deleteTest");
    String targetMethodType = util.getMethodType();
    Response response = util.callService(targetMethodType, "");

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  @GetMapping("/login")
  public ResponseEntity<Object> signin() {

    log.info("login");
    String targetMethodType = util.getMethodType();
    Response response = util.callService("POST", "");

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

}
