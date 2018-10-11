package com.lush.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.lush.javaAggregator.enums.ExceptionType;
import com.lush.javaAggregator.enums.ResponseStatusType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Audio;
import com.lush.javaAggregator.modles.Response;
import java.net.HttpURLConnection;
import java.util.Enumeration;
import java.util.HashMap;
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
   * Model mapper.
   */
  @Autowired
  private ModelMapper modelMapper;

  /**
   * Method name : callService.
   * Description : Call the service.
   *
   * @param url
   * @param request
   * @param methodType
   * @return
   */
  public Map<String, Object> callService(String url, HttpServletRequest request, String methodType) {

    RestTemplate restTemplate = new RestTemplate();
    Integer responseCode = 200;
    String errorMessage = "";
    Map<String, Object> objectMap = null;

    // Add request header.
    HttpHeaders headers = new HttpHeaders();

    try {
      if ("POST".equals(methodType) || "PUT".equals(methodType)) {

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(request);

        ResponseEntity<Object> response = restTemplate
            .exchange(url, HttpMethod.resolve(methodType), httpEntity, Object.class);

        responseCode = response.getStatusCodeValue();
        ObjectMapper mapper = new ObjectMapper();
        objectMap = mapper.convertValue(response.getBody(), Map.class);

      } else {

        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

        ResponseEntity<Object> response = restTemplate
            .exchange(url, HttpMethod.resolve(methodType), httpEntity, Object.class);

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
   * Formatting Map object to Audio object.
   *
   * @param response
   * @return Audio
   */
  public Audio bindingAudio(Map<String, Object> response) {

    Audio audios = new Audio();

    if (response.containsKey("data")) {
      Map<String, Object> data = (Map<String, Object>) response.get("data"); // data만 꺼내옴

      if (data.containsKey("audio")) {
        Map<String, Object> audio = (Map<String, Object>) data.get("audio"); // audio 꺼내옴

        ObjectMapper m = new ObjectMapper();
        audios = m.convertValue(audio.get("audio_file"), Audio.class);

      }
    }

    return audios;
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

//  /**
//   * Method name : getUri.
//   * Description : Get the endpoint.
//   *
//   * @return String
//   */
//  public String getUri() {
//    return request.getRequestURI();
//  }

  /**
   * Method name : setServiceURL.
   * Description : Create a microservice URL.
   *
   * @return String
   */
  private String setServiceURL(String endpoint) {

    if ("/login".equals(endpoint)) {
      return "https://" + gateway_uri + "-" + environment + "." + domain + endpoint;
    } else {
      return "https://" + gateway_uri + "-" + environment + "." + domain + "/service" + endpoint;
    }
  }

//  /**
//   * Method name : getMethodType.
//   * Description : Get the method type of RequestMapping.
//   *
//   * @return String
//   */
//  public String getMethodType() {
//    return request.getMethod();
//  }


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
}
