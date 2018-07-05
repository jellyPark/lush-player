package com.lush.transport;

import com.lush.transport.model.Communication;
import com.lush.transport.model.Config;
import com.lush.transport.model.Request;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
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
   * @param comm
   * @return
   * @throws Exception
   */
  public HttpResponse call(Communication comm) throws Exception {
    comm.getCurrentRequest();
    return null;
  }

  /**
   * Method name : dial.
   * Description : Create a request to a service resource.
   *
   * @param comm
   * @param request
   * @return
   * @throws Exception
   */
  public HttpRequest dial(Communication comm, HttpRequest request) throws Exception {
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

    // Build the resource URL. **************
    String resourceURL = String.format("%s://%s/%s", request, dnsName, request);

    return null;
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