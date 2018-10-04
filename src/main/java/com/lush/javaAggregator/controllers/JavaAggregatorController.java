package com.lush.javaAggregator.controllers;


import com.lush.javaAggregator.enums.ResponseStatusType;
import com.lush.javaAggregator.exceptions.BaseException;
import com.lush.javaAggregator.modles.Audio;
import com.lush.javaAggregator.modles.Podcast;
import com.lush.javaAggregator.modles.Response;
import com.lush.javaAggregator.utils.HttpUtil;
import com.lush.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JavaAggregatorController {

  static final Logger logger = LoggerFactory.getLogger(JavaAggregatorController.class);

  @Autowired
  private Util util;

  @Autowired
  private MessageSource messageSource;

  /**
   * Define Utils for get response headers.
   */
  @Autowired
  private HttpUtil httpUtil;


  /**
   * Get service.
   *
   * @return ResponseEntity
   */
  @GetMapping
  public ResponseEntity<Object> getTest(@RequestParam Map<String, Object> param) {

    System.out.println("aaaaaaaaa>>>" + param);

    Response response = new Response();

    // login -> get token value
    String tokenKey = util.login();

    if (tokenKey != null && !"".equals(tokenKey)) {

      // get podcasts service response
      Map<String, Object> podcastsResponse = util
          .callService("/podcasts/podcasts/33", tokenKey, null);
      response = util.bindingResponse(podcastsResponse);

      if (response.getStatus() == ResponseStatusType.OK) {

        // get audio service response
        Map<String, Object> audioResponse = util.callService("/audio/audio/16", tokenKey, null);
        response = util.bindingResponse(audioResponse);

        if (response.getStatus() == ResponseStatusType.OK) {

          List<Audio> audios = new ArrayList<>();
          Audio audio = util.bindingAudio(audioResponse);
          audios.add(audio);

          // podcasts mapping
          Podcast podcast = util.bindingPodcasts(podcastsResponse, audios);

          response.setData(podcast);
        }
      }

      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }

  }


  /**
   * Post service.
   *
   * @param podcast
   * @return ResponseEntity
   */
  @PostMapping("/podcasts/podcasts")
  public ResponseEntity<Object> postTest(@RequestBody Map<String, Object> podcast) {

    Response response = new Response();

    // login -> get token value
    String tokenKey = util.login();

    if (tokenKey != null && !"".equals(tokenKey)) {
      Map<String, Object> podcastsResponse = util.callService(util.getUri(), tokenKey, podcast);

      response = util.bindingResponse(podcastsResponse);

      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }

  }

  @PutMapping("/podcasts/podcasts/{id}")
  public ResponseEntity<Object> putTest(@PathVariable long id,
      @RequestBody Map<String, Object> podcast) {

    Response response = new Response();

    // login -> get token value
    String tokenKey = util.login();

    if (tokenKey != null && !"".equals(tokenKey)) {
      Map<String, Object> podcastsResponse = util.callService(util.getUri(), tokenKey, podcast);

      response = util.bindingResponse(podcastsResponse);

      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }
  }

  @DeleteMapping("/podcasts/podcasts/{id}")
  public ResponseEntity<Object> deleteTest(@PathVariable long id) {

    Response response = new Response();

    // login -> get token value
    String tokenKey = util.login();

    if (tokenKey != null && !"".equals(tokenKey)) {
      Map<String, Object> podcastsResponse = util.callService(util.getUri(), tokenKey, null);

      response = util.bindingResponse(podcastsResponse);

      return new ResponseEntity<>(response, httpUtil.getResponseHeaders(), HttpStatus.OK);
    } else {
      throw new BaseException("Login FAIL.");
    }
  }


}
