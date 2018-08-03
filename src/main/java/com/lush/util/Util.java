package com.lush.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lush.javaAggregator.enums.ResponseStatusType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Util {

  static final Logger logger = LoggerFactory.getLogger(Util.class);

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
    return request.getMethod();
  }

  /**
   * Method name : getUri.
   * Description : Get the endpoint.
   *
   * @return String
   */
  public String getUri() {
    return request.getRequestURI();
  }

  /**
   * Method name : setServiceURL.
   * Description : Create a microservice URL.
   *
   * @return String
   */
  private String setServiceURL() {

    String type = getUri();

    if ("/test".equals(type)) {
      return "https://podcast-staging.platformserviceaccount.com/podcasts"; // Get Test URL
    } else if ("/login".equals(type)) {
      return "https://" + gateway_uri + "-" + environment + "." + domain + getUri();
    } else {
      return "https://" + gateway_uri + "-" + environment + "." + domain + "/service" + getUri();
    }

  }

  public Response callService(String methodType, Map<String, Object> param) {

    Map<String, Object> resultMap = null;
    Response response = new Response();

    if ("GET".equals(methodType)) {
      resultMap = sendGetHttps();
    } else if ("POST".equals(methodType)) {
      resultMap = sendPostHttps(param);
    } else if ("PUT".equals(methodType)) {

    } else if ("PATCH".equals(methodType)) {

    } else if ("DELETE".equals(methodType)) {
      resultMap = sendDeleteHttps(param);
    }

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

    return response;
  }

  /**
   * Method name : sendGetHttps.
   * Description : It transmits with "GET" method.
   * security 추가 작업 해야함.(권한문제)
   *
   * @return Map
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
      logger.info("Sending 'GET' request to URL : " + url);

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

      logger.info("Sending 'GET' response : " + stringBuffer.toString());

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

  private Map<String, Object> sendDeleteHttps(Map<String, Object> param) {

    HttpsURLConnection connection = null;
    URL url;
    Integer responseCode = 200;
    String errorMessage = "";

    try {

      // Create connection.
      String targetURL = setServiceURL();
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("DELETE");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);

      responseCode = new Integer(connection.getResponseCode());
      errorMessage = connection.getResponseMessage();
      logger.info("\nSending 'DELETE' request to URL : " + url);

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

      logger.info("Sending 'DELETE' response : " + stringBuffer.toString());

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

  public Map<String, Object> sendPostHttps(Map<String, Object> parameter) {

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
//      type 이 /login이 아닌경우(일반 서비스 호출의 경우)
//      Authorization 에 token을 담아 보내는 작업이 필요함
//      String type = getUri();
//      connection.setRequestProperty("Authorization", "Bearer "+ );
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);
      connection.setDoOutput(true);              //항상 갱신된내용을 가져옴.
      connection.setDoInput(true);
      connection.connect(); //connect

      // Map<String, Object> to JsonObject
      JsonObject jsonParmeter = bindingJson(parameter);

      // Send Parameter
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
      out.write(jsonParmeter.toString());
      out.close();

      int responseCode = connection.getResponseCode();

      logger.info("\nSending 'POST' request to URL : " + url);

      if (responseCode == HttpURLConnection.HTTP_OK) {

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

        String key = getTokenKey(objectMap);
        logger.info("result : " + stringBuffer.toString());

        reader.close();
        return objectMap;

      } else {
        logger.info("fail");
        logger.info("Response Code : " + responseCode);
        logger.info("Response Message : " + connection.getResponseMessage());
        return null;
      }

    } catch (Exception e) {

      e.printStackTrace();
      throw new BaseException(e.getMessage());

    } finally {

      if (connection != null) {
        logger.info("disconnection");
        connection.disconnect();
      }
    }
  }

  /**
   * Method name : getParams.
   * Description :
   *
   * @return Map
   */
  public Map<String, Object> getParams(Map<String, Object> params) throws Exception {
    Map<String, Object> reqMap = new HashMap<String, Object>();

    if (params.equals("")) {
      logger.info("requestParams is null");
    } else {
      String methodType = request.getMethod();
      if (methodType.equals("POST") || methodType.equals("PUT")) {
/*        ObjectMapper mapper = new ObjectMapper();
        reqMap = mapper.readValue(params, new TypeReference<HashMap>() {
        });*/
        reqMap.putAll(params);
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

  public boolean checkPageNum() throws Exception {
    boolean check = true;
    String methodType = request.getMethod();
    System.out.println("  request.getParameter : " + request.getParameter("page"));
    if (methodType.equals("GET")) {
      String pageNum = request.getParameter("page");
      if (pageNum != null) {
        if (Integer.parseInt(pageNum) < 0) {
          check = false;
        }
      }
    }
    return check;
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

  public String getTokenKey(Map<String, Object> parameter) {

    for (Map.Entry<String, Object> entry : parameter.entrySet()) {
      String key = entry.getKey();

      logger.info("find Key :: " + key);
    }

    return "test";
  }


}
