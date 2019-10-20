/**
 * @(#)CacheConfig.java, 2018-11-05.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.youdao.sortinghat.service.CacheService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * CacheConfig
 *
 * @author 
 *
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(){
        Caffeine caffeine = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .expireAfterAccess(CacheService.ALWAYS, TimeUnit.MINUTES);
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setAllowNullValues(true);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

}