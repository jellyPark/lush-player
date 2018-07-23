package com.lush.util;

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

}
