package com.lush.transport;

import com.lush.transport.model.Request;

/**
 * Interface responsible for communication.
 */
public interface Transport {

  /**
   * Do the current service request.
   */
//  HttpResponse Call();

  /**
   * Create a request to a service resource.
   *
   * @return
   * @throws Exception
   */
  Request Dial() throws Exception;

  /**
   * Get the name of the service.
   */
  String getName();
}
