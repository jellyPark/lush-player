package com.lush.javaAggregator.aggregator;

import com.lush.util.Util;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ServiceNameAggregator {

  static final Logger logger = LoggerFactory.getLogger(ServiceNameAggregator.class);

  @Autowired
  private Util util;

  public Map<String, Object> serviceNameAggregator(String requestMethod, String url,
      String serviceName, Map<String, Object> param, HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();

    //service1 호출
    response = util.callService(requestMethod, url, serviceName, param, request);

    //service2 호출
    //Map<String, Object> serviceNameResponse2 = util.callService(url, serviceName,
    //                                      new HashMap<String, Object>(), request);

    //최종 가공.

    return response;
  }

}
