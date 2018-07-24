package com.lush.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Util {

  @Autowired
  private HttpServletRequest request;

  public String getMethodType() {
    System.out.println("Method Type :: " + request.getRequestURI() + " :: " + request.getMethod());

    return request.getMethod();
  }

  public String getUri() {
    System.out.println("Request URL :: " + request.getRequestURL());
    System.out.println("Request URI :: " + request.getRequestURI());
    return request.getRequestURI();
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
