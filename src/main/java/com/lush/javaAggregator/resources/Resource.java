package com.lush.javaAggregator.resources;

import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import com.lush.transport.model.ErrorNoContent;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Slf4j
public class Resource {

  @Autowired
  HttpResponse httpResponse;

  // RPC interface representing the generic behaviour a remote resource should have
  public interface RPC {

    Response Call();
  }

  public Response CallPaginated(RemoteResource remoteResource) {

    // 원격 resource를 받아서 여기 안에 request가 있는지 확인
    // 원격 resource를 call한 아이가 있는지 확인
    // http 연결상태 확인

    Response response = new Response();

    try {

      if (httpResponse.getStatusCode() == HttpStatus.NO_CONTENT.value()) {
//        if httpResp.StatusCode == http.StatusNoContent {
//          err = &ErrNoContent{r.Service}
//          return resp, err
//        }
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
