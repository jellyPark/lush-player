package com.lush.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.lush.javaAggregator.enums.ExceptionType;
import com.lush.javaAggregator.enums.ResponseStatusType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Audio;
import com.lush.javaAggregator.modles.Podcast;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.modles.Token;
import java.net.HttpURLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
  // 추후 해당 내용 삭제 -> Header에서 넘어오는 tokenKey를 추출하여 사용하는 방식으로 변경 진행 해야함.
  public String login() {

    RestTemplate restTemplate = new RestTemplate();
    Map<String, Object> loginInfo = new HashMap<>();
    loginInfo.put("email", "webdev@lush.co.uk");
    loginInfo.put("password", "13 young START quarter 75");

    String targetUri = "https://" + gateway_uri + "-" + environment + "." + domain + "/login";
    Map<String, Object> objectMap = restTemplate.postForObject(targetUri, loginInfo, Map.class);

    Token key = getTokenKey(objectMap);

    return key.getValue();
  }


  /**
   * Method name : callService.
   * Description : Call the service.
   *
   * @param endpoint
   * @param tokenKey
   * @param parameter
   * @return
   */
  // parameter로 endpoint를 받아야할지, 아니면 getUri()를 사용할 것인지 논의 필요.
  // 현재 endpoint를 받아오는 이유는 두개의 endpoint를 호출해야 하는 경우가 발생하기 때문.
  public Map<String, Object> callService(String endpoint, String tokenKey,
      Map<String, Object> parameter) {

    RestTemplate restTemplate = new RestTemplate();
    Integer responseCode = 200;
    String errorMessage = "";
    String targetURL = setServiceURL(endpoint);
    Map<String, Object> objectMap = null;
    // Add request header.
    String methodType = getMethodType();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + tokenKey);

    try {
      if ("POST".equals(methodType) || "PUT".equals(methodType)) {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(parameter, headers);
        ResponseEntity<Object> response = restTemplate
            .exchange(targetURL, HttpMethod.resolve(methodType), httpEntity, Object.class);
        responseCode = response.getStatusCodeValue();
        ObjectMapper mapper = new ObjectMapper();
        objectMap = mapper.convertValue(response.getBody(), Map.class);
      } else {
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
        ResponseEntity<Object> response = restTemplate
            .exchange(targetURL, HttpMethod.resolve(methodType), httpEntity, Object.class);
        responseCode = response.getStatusCodeValue();
        ObjectMapper mapper = new ObjectMapper();
        objectMap = mapper.convertValue(response.getBody(), Map.class);
      }
      logger.info("Sending '" + methodType + "' request to URL : " + targetURL);

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

  /*******************************************************
   * Utils
   *******************************************************/

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
}
