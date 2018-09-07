package com.lush.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lush.javaAggregator.enums.ResponseStatusType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Audio;
import com.lush.javaAggregator.modles.Podcast;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.modles.Token;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
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
   * Model mapper.
   */
  @Autowired
  private ModelMapper modelMapper;


  /**
   * Get a login token.
   *
   * @return token value.
   */
  public String login() {

    Map<String, Object> loginInfo = new HashMap<>();
    loginInfo.put("email", "webdev@lush.co.uk");
    loginInfo.put("password", "13 young START quarter 75");

    HttpsURLConnection connection = null;
    URL url;

    try {

      // Create connection.
      String targetURL = "https://" + gateway_uri + "-" + environment + "." + domain + "/login";
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();

      // Add request header.
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.connect();

      // Map<String, Object> to JsonObject
      JsonObject jsonParmeter = bindingJson(loginInfo);

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

        Token key = getTokenKey(objectMap);

        reader.close();
        return key.getValue();

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
        logger.info("login disconnection");
        connection.disconnect();
      }
    }
  }


  /**
   * Method name : sendGetHttps.
   * Description : It transmits with "GET" method.
   *
   * @return Map
   */
  public Map<String, Object> sendGetHttps(String endpoint, String tokenKey) {

    HttpsURLConnection connection = null;
    URL url;
    Integer responseCode = 200;
    String errorMessage = "";

    try {

      // Create connection.
      String targetURL = setServiceURL(endpoint);

      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Authorization", "Bearer " + tokenKey);
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

      reader.close();

      return objectMap;

    } catch (Exception e) {

      e.printStackTrace();
      throw new BaseException(responseCode,
          errorMessage.length() > 0 ? errorMessage : e.getMessage());

    } finally {
      if (connection != null) {
        logger.info(endpoint + "disconnect");
        connection.disconnect();
      }
    }

  }

  /**
   * Method name : sendPostHttps.
   * Description : It transmits with "POST" method.
   *
   * @param endpoint
   * @param tokenKey
   * @param parameter
   * @return Map
   */
  public Map<String, Object> sendPostHttps(String endpoint, String tokenKey,
      Map<String, Object> parameter) {

    HttpsURLConnection connection = null;
    URL url;

    try {

      // Create connection.
      String targetURL = setServiceURL(endpoint);
      url = new URL(targetURL);
      connection = (HttpsURLConnection) url.openConnection();

      // Add request header.
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Authorization", "Bearer " + tokenKey);
      connection.setConnectTimeout(10000);
      connection.setReadTimeout(5000);
      connection.setDoOutput(true);
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
        logger.info(endpoint + " : disconnection");
        connection.disconnect();
      }
    }
  }


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
  private String setServiceURL(String endpoint) {

    if ("/login".equals(endpoint)) {
      return "https://" + gateway_uri + "-" + environment + "." + domain + endpoint;
    } else {
      return "https://" + gateway_uri + "-" + environment + "." + domain + "/service" + endpoint;
    }

  }

//  public Response callService(String methodType, Map<String, Object> param) {
//
//    Map<String, Object> resultMap = null;
//    Response response = new Response();
//
//    if ("GET".equals(methodType)) {
////      resultMap = sendGetHttps();
//    } else if ("POST".equals(methodType)) {
//      resultMap = sendPostHttps(param);
//    } else if ("PUT".equals(methodType)) {
//
//    } else if ("PATCH".equals(methodType)) {
//
//    } else if ("DELETE".equals(methodType)) {
//      resultMap = sendDeleteHttps(param);
//    }
//
//    Integer code = (int) Double.parseDouble(resultMap.get("code").toString());
//
//    if (code == 200) {
//
//      response.setStatus(ResponseStatusType.OK);
//      response.setCode(code);
//      response.setMessage(resultMap.get("message").toString());
//      response.setData(resultMap.get("data"));
//
//    } else {
//
//      response.setStatus(ResponseStatusType.FAIL);
//      response.setCode(code);
//      response.setMessage(resultMap.get("message").toString());
//      response.setData(resultMap.get("data"));
//
//    }
//
//    return response;
//  }


  private Map<String, Object> sendDeleteHttps(Map<String, Object> param) {

    HttpsURLConnection connection = null;
    URL url;
    Integer responseCode = 200;
    String errorMessage = "";

    try {

      // Create connection.
      String targetURL = "";//setServiceURL(endpoint);
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
   * Formatting Map object to Podcast object.
   *
   * @param response
   * @param audios
   * @return Podcast
   */
  public Podcast bindingPodcasts(Map<String, Object> response, List<Audio> audios) {

    Podcast podcast = new Podcast();

    if (response.containsKey("data")) {
      Map<String, Object> data = (Map<String, Object>) response.get("data"); // data만 꺼내옴

      if (data.containsKey("podcasts")) {
        Map<String, Object> podcasts = (Map<String, Object>) data.get("podcasts"); // podcasts 꺼내옴

        podcast = modelMapper.map(podcasts, Podcast.class);
        podcast.setAudio_files(audios);

      }
    }

    return podcast;

  }

  /**
   * Formatting Map object to Token objcet.
   *
   * @param parameter
   * @return Token
   */
  public Token getTokenKey(Map<String, Object> parameter) {

    Token token = new Token();

    if (parameter.containsKey("data")) {
      Map<String, Object> data = (Map<String, Object>) parameter.get("data"); // data만 꺼내옴

      if (data.containsKey("consumer")) {
        Map<String, Object> consumer = (Map<String, Object>) data.get("consumer"); // consumer만 꺼내옴

        if (consumer.containsKey("tokens")) {

          List<Map<String, Object>> tokens = (List<Map<String, Object>>) consumer.get("tokens");
          ObjectMapper m = new ObjectMapper();
          token = m.convertValue(tokens.get(0), Token.class);

        }
      }
    }

    return token;
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


}
