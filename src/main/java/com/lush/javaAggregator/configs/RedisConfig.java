package com.lush.javaAggregator.configs;

//import org.springframework.context.annotation.Configuration;

//@Configuration
public class RedisConfig {

//  /**
//   * Redis host name.
//   */
//  @Value("${spring.redis.host}")
//  private String redisHost;
//
//  /**
//   * Redis port.
//   */
//  @Value("${spring.redis.port}")
//  private int redisPort;
//
//  /**
//   * Create a JedisConnectionFactory to set the host, port, and pool for the redis.
//   *
//   * @return JedisConnectionFactory
//   */
//  @Bean
//  public JedisConnectionFactory jedisConnectionFactory() {
//    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//    jedisConnectionFactory.setHostName(redisHost);
//    jedisConnectionFactory.setPort(redisPort);
//    jedisConnectionFactory.setUsePool(true);
//    return jedisConnectionFactory;
//  }
//
//  /**
//   * Create a RedisTemplate to serialize keys and values.
//   *
//   * @return RedisTemplate
//   */
//  @Bean
//  public RedisTemplate<String, Object> redisTemplate(
//      JedisConnectionFactory jedisConnectionFactory) {
//    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//    redisTemplate.setKeySerializer(new StringRedisSerializer());
//    redisTemplate.setValueSerializer(new StringRedisSerializer());
//    redisTemplate.setConnectionFactory(jedisConnectionFactory);
//    return redisTemplate;
//  }

}
