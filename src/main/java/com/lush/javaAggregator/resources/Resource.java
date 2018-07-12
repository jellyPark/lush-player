package com.lush.javaAggregator.resources;

import com.lush.core.model.ResponseDto;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import com.lush.transport.Service;
import com.lush.transport.model.Communication;
import com.lush.transport.model.ErrorNoContent;
import com.lush.transport.model.Request;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Slf4j
public class Resource {

  public Communication communication;

  public Request request;

  public Service service;

  @Autowired
  HttpResponse httpResponse;

  // RPC interface representing the generic behaviour a remote resource should have
  public interface RPC {

    ResponseDto call();
  }

  /**
   * Method name : call.
   * Description : Call implements RPC, calls the service and returns a response in the standard
   * microservice schema.
   *
   * @return
   * @throws Exception
   */
  public ResponseDto call() throws Exception {
    ResponseDto responseDto = new ResponseDto();

    communication = new Communication();
    request = new Request();

    try {
      service.dial(communication, request);

      try {
        service.call(communication);
      } catch (Exception e) {
        log.debug("error calling rpc: " + e.getMessage());
      }

      // Bind JSON

    } catch (Exception e) {
      log.debug("error calling rpc: " + e.getMessage());
      throw new Exception("could not dial service " + communication.getName());
    }

    return responseDto;
  }


  public Response CallPaginated(RemoteResource remoteResource) {

    // 원격 resource를 받아서 여기 안에 request가 있는지 확인
    // 원격 resource를 call한 아이가 있는지 확인
    // http 연결상태 확인

    Response response = new Response();

    try {

      if (httpResponse.getStatusCode() == HttpStatus.NO_CONTENT.value()) {
//        if httpResp.StatusCode == http.StatusNoContent {
////          err = &ErrNoContent{r.Service}
////          return resp, err
////        }
      }

    } catch (Exception err) {

      if (remoteResource.request() != null) {
        log.debug("dial error: " + err.getMessage());
        throw new BaseException("could not dial service " + remoteResource.getName());
      }

      if (remoteResource.Call() != null) {
        log.debug("error calling rpc: " + err.getStackTrace());
        throw new BaseException();
      }

    }

    /*

	if err := domain.BindJSON(httpResp.Body, &resp); err != nil {
		return resp, err
	}

	defer httpResp.Body.Close()

	return resp, err

     */
    return null;
  }

  public void Initialise() {

  }

  /*********
   * Errors *
   **********/

  // Error to return when service provides no content (Http 204).
  public String Error(ErrorNoContent e) throws Exception {
    return "service " + e.getService().getName() + "returned no content";
  }

}
