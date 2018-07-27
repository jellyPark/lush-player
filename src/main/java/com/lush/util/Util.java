package com.lush.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lush.javaAggregator.enums.ResponseStatusType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Util {

  @Autowired
  private HttpServletRequest request;

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
   * Method name : getMethodType.
   * Description : Get the method type of RequestMapping.
   *
   * @return String
   */
  public String getMethodType() {
    log.info("Method Type :: " + request.getRequestURI() + " :: " + request.getMethod());
    return request.getMethod();
  }

  /**
   * Method name : getUri.
   * Description : Get the endpoint.
   *
   * @return String
   */
  public String getUri() {
    log.info("Request URL :: " + request.getRequestURL());
    log.info("Request URI :: " + request.getRequestURI());
    return request.getRequestURI();
  }

  /**
   * Method name : setServiceURL.
   * Description : Create a microservice URL.
   *
   * @return String
   */
  private String setServiceURL() {

//    Get Test URL
//    return "https://podcast-staging.platformserviceaccount.com/podcasts";
    return "https://" + gateway_uri + "-" + environment + "." + domain + "/service" + getUri();
  }

  /**
   * Method name : serverHealthCheck.
   * Description : Check the health of the microservice servers.
   *
   * @param methodType
   * @return Response
   */

  public Response serverHealthCheck(String methodType) {

    Response response = new Response();
    HttpsURLConnection connection = null;
    URL url;

    try {

      // Create connection
      String targetURL = setServiceURL();
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod(methodType);
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Accept", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);

      int responseCode = connection.getResponseCode();
      log.info("\nSending 'GET' request to URL : " + url);
      log.info("Response Code : " + responseCode);

      BufferedReader reader = new BufferedReader(
          new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

      String inputLine;
      StringBuffer stringBuffer = new StringBuffer();
      while ((inputLine = reader.readLine()) != null) {
        stringBuffer.append(inputLine);
      }

      Gson gson = new Gson();
      Type outputType = new TypeToken<Map<String, Object>>() {
      }.getType();
      Map<String, Object> objectMap = gson.fromJson(stringBuffer.toString(), outputType);

      log.info("조회결과 : " + stringBuffer.toString());

      if (responseCode == 200) {

        response.setStatus(ResponseStatusType.OK);
        Integer code = (int) Double.parseDouble(objectMap.get("code").toString());
        response.setCode(code);
        response.setMessage(objectMap.get("message").toString());
        response.setData(objectMap.get("data"));
      } else {
        response.setStatus(ResponseStatusType.FAIL);
        Integer code = (int) Double.parseDouble(objectMap.get("code").toString());
        response.setCode(code);
        response.setMessage(objectMap.get("message").toString());
        response.setData(objectMap.get("data"));
      }

      reader.close();
      return response;

    } catch (Exception e) {

      e.printStackTrace();
      throw new BaseException(e.getMessage());

    } finally {

      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  public Response callService(String methodType, String param) {

    Map<String, Object> resultMap = null;
    Response response = new Response();

    if ("GET".equals(methodType)) {
      resultMap = sendGetHttps();
    } else if ("POST".equals(methodType)) {
      resultMap = sendPostHttps(param);
    } else if ("PUT".equals(methodType)) {

    } else if ("PATCH".equals(methodType)) {

    } else if ("DELETE".equals(methodType)) {

    }

    log.info("resultMap : " + resultMap.toString());
    Integer code = (int) Double.parseDouble(resultMap.get("code").toString());

    log.info("code : " + code);

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

    return response;
  }

  /**
   * Method name : sendGetHttps.
   * Description : It transmits with "GET" method.
   * security 추가 작업 해야함.(권한문제)
   *
   * @return Map<String   ,       Object>
   */
  private Map<String, Object> sendGetHttps() {

    HttpsURLConnection connection = null;
    URL url;
    Integer responseCode = 200;
    String errorMessage = "";

    try {

      // Create connection.
      String targetURL = setServiceURL();
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);

      responseCode = new Integer(connection.getResponseCode());
      errorMessage = connection.getResponseMessage();
      log.info("\nSending 'GET' request to URL : " + url);

      // Set the result values.
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

      String inputLine;
      StringBuffer stringBuffer = new StringBuffer();
      while ((inputLine = reader.readLine()) != null) {
        stringBuffer.append(inputLine);
      }

      Gson gson = new Gson();
      Type outputType = new TypeToken<Map<String, Object>>() {
      }.getType();
      Map<String, Object> objectMap = gson.fromJson(stringBuffer.toString(), outputType);

      log.info("Sending 'GET' response : " + stringBuffer.toString());

      reader.close();
      return objectMap;

    } catch (Exception e) {

      e.printStackTrace();

      throw new BaseException(responseCode,
          errorMessage.length() > 0 ? errorMessage : e.getMessage());

    } finally {

      if (connection != null) {
        connection.disconnect();
      }
    }

  }

  public Map<String, Object> sendPostHttps(String param) {

    HttpsURLConnection connection = null;
    URL url;

    try {

      // Create connection.
      String targetURL = setServiceURL();
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();

      // Add request header.
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
//      connection.setRequestProperty("Accept", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);

      // Send post request
      connection.setDoOutput(true);              //항상 갱신된내용을 가져옴.
      DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
      wr.writeBytes(param);
      wr.flush();
      wr.close();

      int responseCode = connection.getResponseCode();
      log.info("\nSending 'POST' request to URL : " + url);
      System.out.println("Post parameters : " + param);
      log.info("Response Code : " + responseCode);

      // Set the result values.
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

      String inputLine;
      StringBuffer stringBuffer = new StringBuffer();
      while ((inputLine = reader.readLine()) != null) {
        stringBuffer.append(inputLine);
      }

      Gson gson = new Gson();
      Type outputType = new TypeToken<Map<String, Object>>() {
      }.getType();
      Map<String, Object> objectMap = gson.fromJson(stringBuffer.toString(), outputType);

      log.info("결과 : " + stringBuffer.toString());

      reader.close();
      return objectMap;

    } catch (Exception e) {

      e.printStackTrace();
      throw new BaseException(e.getMessage());

    } finally {

      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  public HashMap getParams(String params) throws Exception {
    HashMap reqMap = new HashMap();
    if (params.equals("")) {
      log.info("requestParams is null");
    } else {
      String methodType = request.getMethod();
      if (methodType.equals("POST") || methodType.equals("PUT")) {
        ObjectMapper mapper = new ObjectMapper();
        reqMap = mapper.readValue(params, new TypeReference<HashMap>() {
        });
      } else {
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
          String strKey = (String) e.nextElement();
          Object strVal[] = request.getParameterValues(strKey);
          if (!reqMap.containsKey(strKey)) {
            reqMap.put(strKey, strVal[0]);
          }
        }
      }
    }
    return reqMap;
  }
}
