package com.lush.javaAggregator.controllers;

import com.lush.util.Util;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class JavaAggregatorController {

  @Autowired
  private Util util;

  @GetMapping("/findmethodtype")
  public String findMethodType() {

    return util.getMethodType();
  }

  @RequestMapping(value = "/sample", method = RequestMethod.GET)
  public String sample() throws Exception {

//    Enumeration headerNames = request.getHeaderNames();
//
//    while (headerNames.hasMoreElements()) {
//      String key = (String) headerNames.nextElement();
//      String value = request.getHeader(key);
//      System.out.println("Test :: " + key + " : " + value);
//    }

    return util.getMethodType();

  }

  @PostMapping(value = "/test")
  public String test() {
    return util.getMethodType();
  }

  @PutMapping(value = "/puttest")
  public String puttest() {
    return util.getMethodType();
  }

  @DeleteMapping(value = "/deleteTest")
  public String deleteTest() {
    return util.getMethodType();
  }

  @PatchMapping(value = "/pathcTest")
  public String pathcTest() {
    return util.getMethodType();
  }

  @GetMapping("/findserviceuri/2")
  public String findServiceUri() {
    new JavaAggregatorController().d();
    return "done";
  }

  void d() {
    e();
  }

  public void e() {
    String baseEnvLinkURL = null;
    HttpServletRequest currentRequest =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    System.out.println("URI   :   " + currentRequest.getRequestURI());
  }


}
