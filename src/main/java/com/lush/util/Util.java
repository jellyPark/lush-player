package com.lush.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lush.javaAggregator.enums.ResponseStatusType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Util {

  @Autowired
  private HttpServletRequest request;

  /**
   * Method name : getMethodType
   * Description : Gets the method type of RequestMapping.
   *
   * @return String
   */
  public String getMethodType() {
    System.out.println("Method Type :: " + request.getRequestURI() + " :: " + request.getMethod());
    return request.getMethod();
  }

  public String getUri() {
    System.out.println("Request URL :: " + request.getRequestURL());
    System.out.println("Request URI :: " + request.getRequestURI());
    return request.getRequestURI();
  }

  public Response serverHealth(String targetURL, String methodType) {

    Response response = new Response();
    HttpsURLConnection connection = null;
    URL url;

    try {
      // Create connection
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod(methodType);
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Accept", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);

      int responseCode = connection.getResponseCode();
      System.out.println("\nSending 'GET' request to URL : " + url);
      System.out.println("Response Code : " + responseCode);

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

      System.out.println("조회결과 : " + stringBuffer.toString());

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

    } catch (Exception e) {
      e.printStackTrace();
      throw new BaseException(e.getMessage());
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
      return response;
    }
  }

  public HashMap getParams(String params) throws Exception {
    HashMap reqMap = new HashMap();
    if (params.equals("")) {
      System.out.println("requestParams is null");
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
