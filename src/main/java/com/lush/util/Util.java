package com.lush.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lush.javaAggregator.enums.ExceptionType;
import com.lush.javaAggregator.enums.ResponseStatusType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import java.net.HttpURLConnection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Util {

  static final Logger logger = LoggerFactory.getLogger(Util.class);

  @Value("${services.domain}")
  private String domain;

  @Value("${services.branch}")
  private String branch;

  @Value("${services.environment}")
  private String environment;

  @Value("${services.gateway_uri}")
  private String gateway_uri;

  @Value("${services.aggregator_prefix}")
  private String aggregator_prefix;

  /**
   * Define Gson for json convert and parse.
   */
  @Autowired
  private Gson gson;

  /**
   * Model mapper.
   */
  @Autowired
  private ModelMapper modelMapper;

  /**
   * Method name : callService.
   * Description : Call the service.
   *
   * @param methodType
   * @param url
   * @param serviceName
   * @param param
   * @param request
   * @return
   */
  public Map<String, Object> callService(String methodType, String url, String serviceName, Map<String, Object> param, HttpServletRequest request) {

    RestTemplate restTemplate = new RestTemplate();
    Integer responseCode = 200;
    String errorMessage = "";
    Map<String, Object> objectMap = null;

    try {
      if ("POST".equals(methodType) || "PUT".equals(methodType)) {

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(param, getHeader(request));

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.resolve(methodType), httpEntity, Object.class);

        responseCode = response.getStatusCodeValue();
        ObjectMapper mapper = new ObjectMapper();
        objectMap = mapper.convertValue(response.getBody(), Map.class);

      } else {

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(getHeader(request));

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.resolve(methodType), httpEntity, Object.class);

        responseCode = response.getStatusCodeValue();
        ObjectMapper mapper = new ObjectMapper();
        objectMap = mapper.convertValue(response.getBody(), Map.class);

      }

      logger.info("Sending '" + methodType + "' request to URL : " + url);

      if (responseCode == HttpURLConnection.HTTP_OK) {
        return objectMap;

      } else {
        throw new BaseException().setCommonExceptoin(ExceptionType.NOT_FOUND_DATA);
      }
    } catch (Exception e) {

      e.printStackTrace();
      throw new BaseException(responseCode,
          errorMessage.length() > 0 ? errorMessage : e.getMessage());
    }

  }

/*******************************************************
 * Formatting
 *******************************************************/

  /**
   * Formatting Map object to Response object.
   *
   * @param resultMap
   * @return Response
   */
  public Response bindingResponse(Map<String, Object> resultMap) {

    Response response = new Response();

    if (resultMap != null) {
      Integer code = (int) Double.parseDouble(resultMap.get("code").toString());

      if (code == 200) {

        response.setStatus(ResponseStatusType.OK);
        response.setCode(code);
        response.setMessage(resultMap.get("message").toString());
        response.setData(resultMap.get("data"));

      } else {

        response.setStatus(ResponseStatusType.FAIL);
        response.setCode(code);
        response.setMessage(resultMap.get("message").toString());
        response.setData(resultMap.get("data"));

      }
    }

    return response;
  }

  /**
   * Method name : bindingJson.
   * Description : Map<String, Object> to JsonObject.
   *
   * @param parameter
   * @return JsonObject
   */
  public JsonObject bindingJson(Map<String, Object> parameter) {

    logger.info("RequestParams : " + parameter);

    JsonObject jsonParameter = new JsonObject();

    for (Map.Entry<String, Object> entry : parameter.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      jsonParameter.addProperty(key, value.toString());
    }

    return jsonParameter;
  }

  /*******************************************************
   * Utils
   *******************************************************/

  /**
   * Method name : setServiceURL.
   * Description : Create a microservice URL.
   *
   * @return String
   */
  public String setServiceURL(String endpoint) {

    if ("/login".equals(endpoint)) {
      return "https://" + gateway_uri + "-" + environment + "." + domain + endpoint;
    } else {
      return "https://" + gateway_uri + "-" + environment + "." + domain + "/service" + endpoint;
    }
  }

//  /**
//   * Method name : getParams.
//   * Description :
//   *
//   * @return Map
//   */
//  public Map<String, Object> getParams(Map<String, Object> params) throws Exception {
//    Map<String, Object> reqMap = new HashMap<String, Object>();
//
//    if (params.equals("")) {
//      logger.info("requestParams is null");
//    } else {
//      String methodType = request.getMethod();
//      if (methodType.equals("POST") || methodType.equals("PUT")) {
//        reqMap.putAll(params);
//      } else {
//        Enumeration e = request.getParameterNames();
//        while (e.hasMoreElements()) {
//          String strKey = (String) e.nextElement();
//          Object strVal[] = request.getParameterValues(strKey);
//          if (!reqMap.containsKey(strKey)) {
//            reqMap.put(strKey, strVal[0]);
//          }
//        }
//      }
//    }
//    return reqMap;
//  }
//
//  public boolean checkPageNum() throws Exception {
//    boolean check = true;
//    String methodType = request.getMethod();
//    System.out.println("  request.getParameter : " + request.getParameter("page"));
//    if (methodType.equals("GET")) {
//      String pageNum = request.getParameter("page");
//      if (pageNum != null) {
//        if (Integer.parseInt(pageNum) < 0) {
//          check = false;
//        }
//      }
//    }
//    return check;
//  }

  /**
   * Method name : getUrl.
   * Description : Request의 Url에서 ContextPath를 제외하고 가져온다.
   *
   * @param req
   * @return
   */
  public String getUrl(HttpServletRequest req) {

    //get url
    String url = req.getRequestURL().toString();

    return url.replace(req.getContextPath(), "");
  }

  /**
   * Method name : getServiceName.
   * Description : Url에서 Service Name만 가져온다.
   *
   * @param url
   * @return
   */
  public String getServiceName(String url) {

    String[] serivceName = url.split("/");

    // [0] - http:
    // [1] -
    // [2] - {java_http}
    // [3] - {servicePath}
    return serivceName[3];
  }


  /**
   * Method name : getHeader.
   * Description : HttpServletRequest in header to HttpHeaders.
   *
   * @param request
   * @return HttpHeaders
   */
  public HttpHeaders getHeader(HttpServletRequest request){

    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.set("Authorization",request.getHeader("Authorization"));
    return headers;

  }
}
