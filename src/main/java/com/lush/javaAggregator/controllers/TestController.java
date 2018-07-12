package com.lush.javaAggregator.controllers;

import com.lush.javaAggregator.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

  @Autowired
  private TestService testService;

  @GetMapping(value = "/pwdencoder")
  public void passwordEncoder() {
//    BCryptPasswordEncoder bcr = new BCryptPasswordEncoder();
//    String result = bcr.encode("1234");
//    System.out.println("암호 === " + result);
  }


}
