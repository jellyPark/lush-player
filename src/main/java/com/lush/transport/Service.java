package com.lush.transport;

import com.lush.transport.model.Communication;
import com.lush.transport.model.Config;
import com.lush.transport.model.Request;
import java.util.Iterator;
import java.util.Map;
import org.springframework.stereotype.Component;

//import com.sun.deploy.net.HttpResponse;

/**
 * Class for http protocol util.
 */
@Component
public class Service {

  /**
   * Method name : call.
   * Description : Do the current service request.
   *
   * @param comm
   * @return
   * @throws Exception
   */
//  public HttpResponse call(Communication comm) throws Exception {
//    comm.getCurrentRequest();
//    return null;
//  }

  /**
   * Method name : dial.
   * Description : Create a request to a service resource.
   *
   * @param comm
   * @param request
   * @throws Exception
   */
  public void dial(Communication comm, Request request) throws Exception {
    // Variable
    String namespace = comm.getNamespace();
    String name = comm.getName();

    // Make any alterations based upon the namespace.
    if (namespace.equals("aggregators")) {
      Config config = new Config();
      String aggregatorDomainPrefix = config.getAggregatorDomainPrefix();
      if (name.indexOf(aggregatorDomainPrefix) == 0) {
        // Do nothing.
      } else {
        name = String.join("-", new String[]{aggregatorDomainPrefix, name});
        comm.setName(name);
      }
    }

    // Determine the service namespace to use based on the service version.
    String serviceNamespace = name;
    if (comm.getVersion() == 0) {
      // Do nothing.
    } else {
      serviceNamespace = String.format("%s-%d", serviceNamespace, comm.getVersion());
    }

    // Get the name of the service.
    String dnsName = buildServiceDNSName(name, comm.getBranch(), comm.getEnvironment(),
        serviceNamespace);

    // Build the resource URL.
    String resourceURL = String
        .format("%s://%s/%s", request.getProtocol(), dnsName, request.getResource());

    // Append the query string if we have any.
    // query 부분은 조금더 확인이 필요함.
    String query = request.getUrl().getQuery();
    if (query.length() > 0) {
      resourceURL = String.format("%s?%s", resourceURL, query);
    }

    // Create the request
    // request를 재정의 할수가 없다.
    comm.setCurrentRequest(null);

    // Add the headers.
    Map<String, String> headers = request.getHeaders();
    Iterator it = headers.keySet().iterator();
    while (it.hasNext()) {
      String key = (String) it.next();
      String value = headers.get(key);
      // Key Value 셋팅할 방법이 없음
    }
  }

  /**
   * Method name : buildServiceDNSName.
   * Description : Build the full DNS name for a service.
   *
   * @param service
   * @param branch
   * @param environment
   * @param serviceNamespace
   * @return
   */
  public String buildServiceDNSName(String service, String branch, String environment,
      String serviceNamespace) {
    return service + "-" + branch + "-" + environment + "." + serviceNamespace;
  }

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