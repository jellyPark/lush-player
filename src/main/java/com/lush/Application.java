package com.lush;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * Method name : modelmapper
   * Description : Create a bean of modelmapper.
   *
   * @return ModelMapper
   */
  @Bean
  public ModelMapper modelmapper() {
    return new ModelMapper();
  }

}