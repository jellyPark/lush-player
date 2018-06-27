package com.lush.transport.model;

import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Data
public class Config {

  // AuthHeader - Name of the HTTP header to use for authentication and
  // authorization.
  private String authHeader = "Authorization";

  // AuthHeaderPrefix - Prefix expected for the HTTP auth header value.
  private String authHeaderPrefix = "Bearer";

  // ProtocolHTTP - Protocol string for non-ssl requests.
  private String protocolHTTP = "http";

  // ProtocolHTTPS - Protocol string for ssl requests.
  private String protocolHTTPS = "https";

  // RequestKey - Name of the context key used to pass a service request
  // between layers of middleware.
  private String requestKey = "request";

  // ServiceVersionHeader - Name of the HTTP header to use for service version.
  private String serviceVersionHeader = "x-service-version";

  // AggregatorDomainPrefix - The prefix value used for aggregator domains.
  private String aggregatorDomainPrefix = "agg";

  // getServiceDomain - Get the top level domain of the service environment.
  public String getServiceDomain() {
    return System.getenv("SOA_DOMAIN");
  }

  // getGatewayURI - Get the URI of the API gateway.
  public String getGatewayURI() {
    return System.getenv("SOA_GATEWAY_URI");
  }

  // getGatewayURL - Get the URL of the API gateway.
  public String getGatewayURL() {
    return System.getenv("SOA_GATEWAY_URL");
  }

}
