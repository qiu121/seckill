package com.github.qiu121.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis序列化配置
 *
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/17
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 注入连接工厂
        redisTemplate.setConnectionFactory(factory);

        // 设置key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置value的序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 设置Hash类型的Key序列化
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        // 设置Hash类型的Value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
