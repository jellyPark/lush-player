package com.lush.javaAggregator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lush.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaAggregatorController {

  @Autowired
  private Util util;

  @Autowired
  private ObjectMapper mapper;

  @GetMapping("/findmethodtype")
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

}
