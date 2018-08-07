package com.lush.javaAggregator.configs;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfigurerAdapter {

  @Value("${message.classpath}")
  private String classPath;


  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    //WEB-INF 밑에 해당 폴더에서 properties를 찾는다.
    messageSource.setBasename(classPath);
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    //request로 넘어오는 language parameter를 받아서 locale로 설정 한다.
    localeChangeInterceptor.setParamName("language");
    return localeChangeInterceptor;
  }

  @Bean(name = "localeResolver")
  public LocaleResolver sessionLocaleResolver() {
    //세션 기준으로 로케일을 설정 한다.
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();

    //최초 기본 로케일을 강제로 설정이 가능 하다.
    localeResolver.setDefaultLocale(new Locale("ko_KR"));

    return localeResolver;
  }
}
