package com.lush.javaAggregator.resources;

import com.lush.transport.Transport;
import com.lush.transport.model.Communication;
import com.lush.transport.model.Request;

/**
 * RemoteResource type representing a service and it's requests
 * it is ok to add more fields and requests.
 */
public interface RemoteResource extends Transport {

  Communication communication();

  Request request();

}
