package com.lush.javaAggregator.controllers;

import com.lush.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaAggregatorController {

  @Autowired
  private Util util;

  @GetMapping("/findmethodtype")
  public String findMethodType() {
    return util.getMethodType();
  }

  @GetMapping("/sampleGetUri")
  public String sampleGetUri() {
    return util.getUri();
  }
}
