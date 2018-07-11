package com.lush.transport;

import com.lush.transport.model.Config;
import com.lush.transport.model.Request;
import org.springframework.stereotype.Component;

/**
 * Class for http protocol util.
 */
@Component
public class Service {

  /**
   * Method name : call.
   * Description : Do the current service request.
   *
   * @param communication
   * @return
   * @throws Exception
   */
//  public HttpResponse call(Communication communication) throws Exception {
//    //communication.getClient().
//    return null;
//  }

  /**
   * Method name : getProtocol.
   * Description : Get the transfer protocol to use for the service.
   *
   * @param request
   * @return
   */
  public String getProtocol(Request request) {
    Config config = new Config();

    if (request.getProtocol() == config.getProtocolHTTP() || request.getProtocol() == config
        .getProtocolHTTPS()) {
      return request.getProtocol();
    } else {
      return config.getProtocolHTTP();
    }
  }
}