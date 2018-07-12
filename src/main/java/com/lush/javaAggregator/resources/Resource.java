package com.lush.javaAggregator.resources;

import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import org.springframework.http.HttpRequest;

public class Resource {


  // RPC interface representing the generic behaviour a remote resource should have
  public interface RPC {

    Response Call();
  }

  public Response CallPaginated(RemoteResource remoteResource) {

    Response response = new Response();

    try {

      // httpResponse

       /*

	if err := domain.BindJSON(httpResp.Body, &resp); err != nil {
		return resp, err
	}

	defer httpResp.Body.Close()

	return resp, err

     */

    } catch (Exception err) {

      // 원격 resource를 받아서 여기 안에 request가 있는지 확인
      if (remoteResource.request() != null) {
//        log.debug("dial error: " + err.getMessage());
        throw new BaseException("could not dial service " + remoteResource.getName());
      }

//


    }
    return null;
  }

  public void Initialise() {

  }

  /***************
   * Body binding *
   ***************/

  public static String BindJSON(HttpRequest httpRequest) {
    try {
      httpRequest.getMethodValue();
    } catch (Exception err) {

    } finally {

    }
    return null;
  }

  /*********
   * Errors *
   **********/

  // Error to return when service provides no content (Http 204).
  public String Error(String serviceName) {
    return "service " + serviceName + "returned no content";
  }

}
