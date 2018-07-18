package com.lush.javaAggregator.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  /**
   * Redis host name.
   */
  @Value("${spring.redis.host}")
  private String redisHost;

  /**
   * Redis port.
   */
  @Value("${spring.redis.port}")
  private int redisPort;

  /**
   * Create a JedisConnectionFactory to set the host, port, and pool for the redis.
   *
   * @return JedisConnectionFactory
   */
  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName(redisHost);
    jedisConnectionFactory.setPort(redisPort);
    jedisConnectionFactory.setUsePool(true);
    return jedisConnectionFactory;
  }

  /**
   * Create a RedisTemplate to serialize keys and values.
   *
   * @return RedisTemplate
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      JedisConnectionFactory jedisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setConnectionFactory(jedisConnectionFactory);
    return redisTemplate;
  }

}
