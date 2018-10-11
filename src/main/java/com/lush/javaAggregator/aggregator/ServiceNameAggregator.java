package com.lush.javaAggregator.aggregator;

import com.lush.util.Util;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class ServiceNameAggregator {

  static final Logger logger = LoggerFactory.getLogger(ServiceNameAggregator.class);

  @Autowired
  private Util util;

  public Map<String, Object> callServiceNameAggregator(String requestMethod, String url,
      String serviceName, Map<String, Object> param, HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();

    //service1 호출
    // Map<String, Object> serviceNameResponse1 = util.callService(url, serviceName,
    //                                      new HashMap<String, Object>(), request);
    System.out.println("####### [Aggregator]Call Service #######");
    //service2 호출
    //Map<String, Object> serviceNameResponse2 = util.callService(url, serviceName,
    //                                      new HashMap<String, Object>(), request);

    //최종 가공.
    System.out.println("####### [Aggregator]Return Make Response data #######");

    return response;
  }

}
