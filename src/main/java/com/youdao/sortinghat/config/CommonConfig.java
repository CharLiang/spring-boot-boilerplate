/**
 * @(#)CommandConfig.java, 2019-01-29.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CommandConfig
 *
 * @author 
 *
 */
@Component
public class CommonConfig {

    private static Long port;

    private static String redisKey;

    private static String portManager = "7134";

    private static String urlOralClassAddOne;

    private static String urlOralClassUpdate;

    private static String urlOralClassDeleteOne;

    private static String urlOralCodeAddAll;

    public static Long getPort() {
        return port;
    }

    @Value("${server.port}")
    public void setPort(Long port) {
        CommonConfig.port = port;
    }

    public static String getRedisKey() {
        return redisKey;
    }

    @Value("${common.redis.key}")
    public void setRedisKey(String redisKey) {
        CommonConfig.redisKey = redisKey;
    }

    public static String getPortManager() {
        return portManager;
    }

    @Value("${management.port}")
    public void setPortManager(String portManager) {
        CommonConfig.portManager = portManager;
    }

    public static String getUrlOralClassAddOne() {
        return urlOralClassAddOne;
    }

    @Value("${oral.url.class.addone}")
    public void setUrlOralClassAddOne(String urlOralClassAddOne) {
        CommonConfig.urlOralClassAddOne = urlOralClassAddOne;
    }

    public static String getUrlOralClassUpdate() {
        return urlOralClassUpdate;
    }

    @Value("${oral.url.class.update}")
    public void setUrlOralClassUpdate(String urlOralClassUpdate) {
        CommonConfig.urlOralClassUpdate = urlOralClassUpdate;
    }

    public static String getUrlOralClassDeleteOne() {
        return urlOralClassDeleteOne;
    }

    @Value("${oral.url.class.deleteone}")
    public void setUrlOralClassDeleteOne(String urlOralClassDeleteOne) {
        CommonConfig.urlOralClassDeleteOne = urlOralClassDeleteOne;
    }

    public static String getUrlOralCodeAddAll() {
        return urlOralCodeAddAll;
    }

    @Value("${oral.url.code.addall}")
    public void setUrlOralCodeAddAll(String urlOralCodeAddAll) {
        CommonConfig.urlOralCodeAddAll = urlOralCodeAddAll;
    }
}