package com.lush.javaAggregator.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lush.javaAggregator.enums.ExceptionType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.utils.HttpUtil;
import com.lush.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaAggregatorController {

  @Autowired
  private Util util;


  @Autowired
  private ObjectMapper mapper;


  /**
   * Define Utils for get response headers.
   */
  @Autowired
  private HttpUtil httpUtil;


  @GetMapping(value = "/findmethodtype")
  public String findMethodType() {
    return util.getMethodType();
  }


//  @PostMapping(value = "/testPodcasts")
//  public String createPodcast(@RequestBody @Valid PodcastReq podcastReq) throws Exception {
//
//    HashMap reqMap = mapper.convertValue(podcastReq, HashMap.class);
//    System.out.println("requestParams  :    " + reqMap.toString());
//    return reqMap.toString();
//  }


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

  @GetMapping(value = "/{targetService}/health")
  public ResponseEntity<Object> serverHealth(@PathVariable String targetService) {

    String targetURL = "https://" + targetService + "-staging.platformserviceaccount.com/healthz";
    String targetMethodType = util.getMethodType();
    Response response = util.serverHealth(targetURL, targetMethodType);

    return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

}
