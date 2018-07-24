package com.lush.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
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

  public String serverHealth(String targetURL, String methodType) {
    URL url;
    HttpsURLConnection connection = null;

    try {
      // Create connection
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod(methodType);
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);

      int responseCode = connection.getResponseCode();
      System.out.println("\nSending 'GET' request to URL : " + url);
      System.out.println("Response Code : " + responseCode);

      Charset charset = Charset.forName("UTF-8");

      BufferedReader reader = new BufferedReader(
          new InputStreamReader(connection.getInputStream(), charset));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = reader.readLine()) != null) {
        response.append(inputLine);
      }

      reader.close();

      System.out.println("조회결과 : " + response.toString());
      return response.toString();

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  public HashMap getParams(String params) throws Exception{
    HashMap reqMap = new HashMap();
    if(params.equals("")){
      System.out.println("requestParams is null");
    }else{
      String methodType = request.getMethod();
      if(methodType.equals("POST") || methodType.equals("PUT")){
        ObjectMapper mapper = new ObjectMapper();
        reqMap = mapper.readValue(params, new TypeReference<HashMap>(){});
      }else{
        Enumeration e= request.getParameterNames();
        while (e.hasMoreElements()) {
          String strKey = (String)e.nextElement();
          Object strVal[] = request.getParameterValues(strKey);
          if( !reqMap.containsKey( strKey ) ){
            reqMap.put(strKey,strVal[0]);
          }
        }
      }
    }
    return reqMap;
  }
}
