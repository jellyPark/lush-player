package com.lush.lushPlayer.aggregator;

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
public class LushPlayerAggregator {

  static final Logger logger = LoggerFactory.getLogger(LushPlayerAggregator.class);

  @Autowired
  private Util util;

  public Map<String, Object> callServiceNameAggregator(String requestMethod, String url,
      String serviceName, Map<String, Object> param, HttpServletRequest request) {

    Map<String, Object> response = new HashMap<String, Object>();

    // Call service.

    response = util.callService(requestMethod, url, serviceName, param, request);

    // Call other service.

    // Data collection and processing.

    return response;
  }

}
