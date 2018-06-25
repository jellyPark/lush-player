package com.lush.javaAggregator.modles;

import java.util.List;

/**
 * Response object to hold endpoint information.
 */
public class ServiceInfo {

  /**
   * Service name of microservice.
   */
  private String service_name;

  /**
   * Service type of microservice.
   */
  private String service_type;

  /**
   * Service scope of microservice.
   */
  private String service_scope;

  /**
   * Service version of microservice.
   */
  private String service_version;

  /**
   * Endpoint list of microservice.
   */
  private List<Endpoint> endpoints;

  /**
   * Get Service name
   */
  public String getService_name() {
    return service_name;
  }

  /**
   * Set Service name
   */
  public void setService_name(String service_name) {
    this.service_name = service_name;
  }

  /**
   * Get Service type
   */
  public String getService_type() {
    return service_type;
  }

  /**
   * Set Service type
   */
  public void setService_type(String service_type) {
    this.service_type = service_type;
  }

  /**
   * Get Service scope
   */
  public String getService_scope() {
    return service_scope;
  }

  /**
   * Set Service scope
   */
  public void setService_scope(String service_scope) {
    this.service_scope = service_scope;
  }

  /**
   * Get Service version
   */
  public String getService_version() {
    return service_version;
  }

  /**
   * Set Service version
   */
  public void setService_version(String service_version) {
    this.service_version = service_version;
  }

  /**
   * Get Endpoint list
   */
  public List<Endpoint> getEndpoints() {
    return endpoints;
  }

  /**
   * Set Endpoint list
   */
  public void setEndpoints(List<Endpoint> endpoints) {
    this.endpoints = endpoints;
  }
}
