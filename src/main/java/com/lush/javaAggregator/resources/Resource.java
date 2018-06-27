package com.lush.javaAggregator.resources;

import com.lush.javaAggregator.modles.Pagination;
import com.lush.javaAggregator.modles.Response;
import com.lush.transport.model.ErrorNoContent;
import com.lush.transport.model.RemoteResource;
import com.lush.transport.model.Request;
import org.springframework.web.servlet.resource.HttpResource;

public class Resource {

  // RPC interface representing the generic behaviour a remote resource should have
  public interface RPC {

    Response Call();
  }

  public Pagination CallPaginated(RemoteResource r) throws Exception {
    return null;
  }

  public void Initialise() {

  }

  /*********
   * Errors *
   **********/

  public interface Transport {

    // Call - Do the current service request.
    HttpResource Call();

    // Dial - Create a request to a service resource.
    Request Dial();

    // getName - Get the name of the service
    String getName();
  }

  // Error to return when service provides no content (Http 204).
  public String Error(ErrorNoContent e) {
    return "service " + e.getService().getName() + "returned no content";
  }

}
