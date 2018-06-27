package com.lush.javaAggregator.resources;

import com.lush.transport.model.Communication;
import org.springframework.context.annotation.ComponentScan;

/**
 * RemoteResource type representing a service and it's requests
 * it is ok to add more fields and requests.
 */
@ComponentScan
public class RemoteResource {

  private Communication communication;
}
