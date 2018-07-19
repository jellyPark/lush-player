package com.lush.javaAggregator.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class JavaAggregatorController {

  @GetMapping("/findmethodtype")
  public String findMethodType() {

    new JavaAggregatorController().a();

    Object object = new JavaAggregatorController().c();
    object.getClass().getName();

    System.out.println("Type : " + object.getClass().getName());
    return "OK";
  }

  void a() {
    b();
  }

  public void b() {
    StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();

    for (int i = stackTraceElements.length - 1; i > 0; i--) {
      System.out.print("클래스 : " + stackTraceElements[i].getClassName());
      System.out.print(", 메소드 : " + stackTraceElements[i].getMethodName());
      System.out.print(", 라인 : " + stackTraceElements[i].getLineNumber());
      System.out.print(", 파일 : " + stackTraceElements[i].getFileName());
      System.out.println();
    }
  }

  public String c() {
    return "C";
  }
  @GetMapping("/findserviceuri/2")
  public String findServiceUri() {
    new JavaAggregatorController().d();
    return "done";
  }

  void d() {
    e();
  }

  public void e() {
    String baseEnvLinkURL=null;
    HttpServletRequest currentRequest =
        ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    System.out.println("URI   :   " + currentRequest.getRequestURI());
  }

}
