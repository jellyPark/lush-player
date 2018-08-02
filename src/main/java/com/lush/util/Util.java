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
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
    return request.getRequestURI();
  }

  /**
   * Method name : setServiceURL.
   * Description : Create a microservice URL.
   *
   * @return String
   */
  private String setServiceURL(String type) {

    if ("test".equals(type)) {
      return "https://podcast-staging.platformserviceaccount.com/podcasts"; // Get Test URL
    } else if ("login".equals(type)) {
      return "https://" + gateway_uri + "-" + environment + "." + domain + getUri();
    } else {
      return "https://" + gateway_uri + "-" + environment + "." + domain + "/service" + getUri();
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
      resultMap = sendDeleteHttps(Long.parseLong(param));
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
      String targetURL = setServiceURL("");
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);

      responseCode = new Integer(connection.getResponseCode());
      errorMessage = connection.getResponseMessage();
      log.info("Sending 'GET' request to URL : " + url);

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

  private Map<String, Object> sendDeleteHttps(long id) {

    HttpsURLConnection connection = null;
    URL url;
    Integer responseCode = 200;
    String errorMessage = "";

    try {

      // Create connection.
      String targetURL = setServiceURL("");
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("DELETE");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);

      responseCode = new Integer(connection.getResponseCode());
      errorMessage = connection.getResponseMessage();
      log.info("\nSending 'DELETE' request to URL : " + url);

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

      log.info("Sending 'DELETE' response : " + stringBuffer.toString());

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

  public void test() throws Exception {
    HttpClient client = new DefaultHttpClient();
    HttpPost requPost = new HttpPost(
        "https://api-gateway-staging.platformserviceaccount.com/login");
    HttpResponse response = client.execute(requPost);
    int code = response.getStatusLine().getStatusCode();

    try (BufferedReader br = new BufferedReader(
        new InputStreamReader((response.getEntity().getContent())))) {
      // Read in all of the post results into a String.
      String output = "";
      Boolean keepGoing = true;
      while (keepGoing) {
        String currentLine = br.readLine();
        if (currentLine == null) {
          keepGoing = false;
        } else {
          output += currentLine;
        }
      }
      System.out.println("Response-->" + output);
    } catch (Exception e) {
      System.out.println("Exception" + e);

    }
  }

  public Map<String, Object> sendPostHttps(String param) {

    HttpsURLConnection connection = null;
    URL url;

    try {

      // Create connection.
      String targetURL = setServiceURL("login");
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();

      // Add request header.
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
//      connection.setRequestProperty("Authorization", "Bearer "+ );
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);
      connection.setDoOutput(true);              //항상 갱신된내용을 가져옴.
      connection.setDoInput(true);
      connection.connect(); //connect

      log.info("connection :: " + connection);

      JsonObject parameter = new JsonObject();
      parameter.addProperty("email", "webdev@lush.co.uk");
      parameter.addProperty("password", "{{password}}");

      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
      out.write(parameter.toString());
      out.close();

      int responseCode = connection.getResponseCode();

      log.info("\nSending 'POST' request to URL : " + url);
      System.out.println("Post parameters : " + parameter.toString());
      log.info("Response Code : " + responseCode);
      log.info("Response Message : " + connection.getResponseMessage());

      log.info("connection.getInputStream() :: " + connection.getInputStream());

      log.info("=================================================");

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

        log.info("결과 : " + stringBuffer.toString());

        reader.close();
        return objectMap;
      } else {
        log.info("fail");
        return null;
      }

    } catch (Exception e) {

      e.printStackTrace();
      throw new BaseException(e.getMessage());

    } finally {

      if (connection != null) {
        log.info("disconnection");
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
      log.info("requestParams is null");
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

}
