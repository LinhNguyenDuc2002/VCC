package com.example.otpauthentication.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("user");

        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterAccess(300, TimeUnit.SECONDS)
                .maximumSize(100);

        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
