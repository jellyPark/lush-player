package com.lush.transport;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;

/**
 * Interface responsible for communication.
 */
public interface Transport {

  /**
   * Do the current service request.
   *
   * @return
   * @throws Exception
   */
  HttpResponse Call() throws Exception;

  /**
   * Create a request to a service resource.
   *
   * @return
   * @throws Exception
   */
  HttpRequest Dial() throws Exception;

  /**
   * Get the name of the service.
   *
   * @return
   * @throws Exception
   */
  String getName() throws Exception;
}
