package com.kim.blog.config;

import com.kim.blog.post.response.PostResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, PostResponseDto> redisPostTemplate(){
        RedisTemplate<String, PostResponseDto> redisTemplate= new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    //TODO redis 키별 캐싱 유효시간 설정
//    @Bean
//    public RedisCacheManager redisCacheManager(){
//        RedisCacheConfiguration.defaultCacheConfig()
//                .disableCachingNullValues();
//
//
//        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
//        cacheConfigurations.put("E-Auth-code", RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(60*3)));
//
//    }

}
