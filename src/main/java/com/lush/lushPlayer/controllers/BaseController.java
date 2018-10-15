package com.lush.lushPlayer.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lush.lushPlayer.enums.ResponseStatusType;
import com.lush.lushPlayer.exceptions.BaseException;
import com.lush.lushPlayer.modles.Endpoint;
import com.lush.lushPlayer.modles.Response;
import com.lush.lushPlayer.modles.ServiceInfo;
import com.lush.lushPlayer.utils.HttpUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * BaseController
 *
 * Class that receives health check for services and endpoint information requests.
 */
@ControllerAdvice
@RestController
public class BaseController {

  /**
   * Define HttpServletRequest for get client request information.
   */
  @Autowired
  private HttpServletRequest request;

  /**
   * Define RestTemplate for get response information of uri.
   */
  protected RestTemplate restTemplate;

  /**
   * Define Gson for json convert and parse.
   */
  @Autowired
  private Gson gson;

  /**
   * Define service name.
   */
  @Value("${service.name}")
  private String serviceName;

  /**
   * Define service type.
   */
  @Value("${service.type}")
  private String serviceType;

  /**
   * Define service scope.
   */
  @Value("${service.scope}")
  private String serviceScope;

  /**
   * Define service version.
   */
  @Value("${service.version}")
  private String serviceVersion;

  /**
   * Define InetAddress for get host name.
   * The hostname can be imported as a HttpServletRequest object,
   * but an issue occurs in the docker collector container that recognizes the hostname as '
   * java_http'.
   */
  private InetAddress ip;

  /**
   * Define utils.
   */
  @Autowired
  private HttpUtil httpUtil;

  /**
   * Set to RestTemplate Bean.
   */
  public BaseController(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  /**
   * Set uri for rest api.
   *
   * @param context
   * @return String
   * @throws UnknownHostException
   */
  public String setUri(String context) throws UnknownHostException {
    ip = InetAddress.getLocalHost();
    return request.getScheme() + "://" + ip.getHostName() + ":" + request.getServerPort() + "/"
        + context;
  }

  /**
   * Check health.(application, database, redis)
   *
   * @return Response
   * @throws UnknownHostException
   */
  @GetMapping("/healthz")
  public Response healthz() throws UnknownHostException {
    Response response = new Response();

    // Get health data.
    String uri = setUri("health");
    ResponseEntity<String> health = restTemplate.getForEntity(uri, String.class);
    JsonObject healthBody = gson.fromJson(health.getBody(), JsonObject.class);
    String appStatus = healthBody.get("status").getAsString();

    // Check status of application.
    if (!"UP".equals(appStatus)) {
      response.setStatus(ResponseStatusType.FAIL);
      response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setMessage("AppStatus is fail");
    }
    // Check status of database connection.
    if (health.getBody().contains("db")) {
      JsonObject temp = healthBody.get("details").getAsJsonObject();
      temp = temp.get("db").getAsJsonObject();
      String dbStatus = temp.get("status").getAsString();

      if (!"UP".equals(dbStatus)) {
        response.setStatus(ResponseStatusType.FAIL);
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Database status is fail");
      }
    }

    // Check status of redis connection.
    if (health.getBody().contains("redis")) {
      JsonObject temp = healthBody.get("details").getAsJsonObject();
      temp = temp.get("redis").getAsJsonObject();
      String redisStatus = temp.get("status").getAsString();

      if (!"UP".equals(redisStatus)) {
        response.setStatus(ResponseStatusType.FAIL);
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Redis status is fail");
      }
    }


    return response;
  }

  /**
   * Get endpoint list of api.
   *
   * @return ResponseEntity
   * @throws UnknownHostException
   */
  @GetMapping("/")
  public ResponseEntity endpoints() throws UnknownHostException {

    // Get endpoints data.
    String uri = setUri("mappings");
    ResponseEntity<JsonNode> data = restTemplate.getForEntity(uri, JsonNode.class);
    JsonNode dataBody = data.getBody().findPath("dispatcherServlet");

    // Find all method and uri
    List<JsonNode> methods = dataBody.findValues("methods");
    List<JsonNode> patterns = dataBody.findValues("patterns");

    String method = "";
    String pattern = "";
    String regex = "[\"\\[\\]]";
    List<Endpoint> endpoints = new ArrayList<>();

    for (int idx = 0; idx < methods.size(); idx++) {
      method = methods.get(idx).toString().replaceAll(regex, "");
      pattern = patterns.get(idx).toString().replaceAll(regex, "");

      if (method.length() == 0 || pattern.length() == 0 || "/health".equals(pattern) || "/mappings"
          .equals(pattern)) {
        continue;
      }

      Endpoint endpoint = new Endpoint();
      endpoint.setMethod(HttpMethod.valueOf(method));
      endpoint.setUri(pattern);
      endpoints.add(endpoint);
    }

    // Set endpoints data.
    ServiceInfo serviceInfo = new ServiceInfo();
    serviceInfo.setService_name(serviceName);
    serviceInfo.setService_type(serviceType);
    serviceInfo.setService_scope(serviceScope);
    serviceInfo.setService_version(serviceVersion);
    serviceInfo.setEndpoints(endpoints);

    return new ResponseEntity(serviceInfo, httpUtil.getResponseHeaders(), HttpStatus.OK);
  }

  /**
   * Method name : handlerCoreException.
   * Description : Core Exception Handler.
   *
   * @return Response
   */
  @ExceptionHandler(BaseException.class)
  public Response handlerCoreException(BaseException e) {
    Response response = new Response();
    response.setStatus(e.getStatus());
    response.setCode(e.getCode());
    response.setMessage(e.getMessage());
    return response;
  }
}