package com.lush.javaAggregator.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.stream.CharacterStreamReadingMessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;

@Configuration
@IntegrationComponentScan
@EnableIntegration
public class IntegrationConfig {

  @Bean
  public PollableChannel bridgeFromInput() {
    return new QueueChannel();
  }

  /**
   * Method name : bridgeFromOutput.
   * Description : DirectChannel 사용하면 서비스가 폴러 실행자 스레드에서 호출되므로 다른 비동기 핸드 오프가 필요하지 않습니다.
   *
   * @return
   */
  @Bean
  @BridgeFrom(value = "bridgeFromInput", poller = @Poller(fixedDelay = "1000"))
  public MessageChannel bridgeFromOutput() {
    System.out.println("Excute :::: bridgeFromOutput()");
    return new DirectChannel();
  }

  @Bean
  public QueueChannel bridgeToOutput() {
    System.out.println("Excute :::: bridgeToOutput()");
    return new QueueChannel();
  }

  @Bean
  @BridgeTo("bridgeToOutput")
  public MessageChannel bridgeToInput() {
    System.out.println("Excute :::: bridgeToInput()");
    return new DirectChannel();
  }

  /**
   * Method name : consoleSource.
   * Description : Poller의 Delay 의 간격만큼 서버에 정기적으로 새 데이터 확인. (맞나?)
   * 시작지점. 또는 인터셉터 역할? 일수도 있음 (확실하지 않음)
   *
   * @return
   */
  @Bean
  @InboundChannelAdapter(value = "inputChannel", poller = @Poller(fixedDelay = "1000"))
  public MessageSource<String> consoleSource() {
    System.out.println("Excute :::: consoleSource()");
    return CharacterStreamReadingMessageSource.stdin();
  }

  /**
   * Method name : jsonTransformer.
   * Description : Channel에 들어온 Object를 JSON으로 변환 해주는 역할. (원래 Map 이였음)
   * 인자값도 없고 언제 어떻게 발동하는지 확실하지 않음.
   *
   * @return
   */
  @Bean
  @Transformer(inputChannel = "inputChannel", outputChannel = "httpChannel")
  public ObjectToJsonTransformer jsonTransformer() {
    System.out.println("Excute :::: jsonTransformer()");
    return new ObjectToJsonTransformer();
  }

  /**
   * Method name : httpHandler.
   * Description : ServiceActivator는 Poller가 필요하지 않음
   * 다른 예제에서는 여기서 DirectChannel을 호출하도록 하는거 같음.
   *
   * @return
   */
  @Bean
  @ServiceActivator(inputChannel = "httpChannel", outputChannel = "outputChannel")
  public MessageHandler httpHandler() {
    System.out.println("Excute :::: httpHandler()");
    HttpRequestExecutingMessageHandler handler = new HttpRequestExecutingMessageHandler(
        "http://foo/service");
    handler.setExpectedResponseType(String.class);
    handler.setOutputChannelName("outputChannel");
    return handler;
  }

  /**
   * Method name : loggingHandler.
   * Description : 로그관리용인듯?.
   *
   * @return
   */
  @Bean
  @ServiceActivator(inputChannel = "outputChannel")
  public LoggingHandler loggingHandler() {
    System.out.println("Excute :::: loggingHandler()");
    return new LoggingHandler("info");
  }
}
