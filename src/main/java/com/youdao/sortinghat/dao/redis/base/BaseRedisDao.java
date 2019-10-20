/**
 * @(#)RedisBaseDao.java, 2018-03-23.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.dao.redis.base;

import com.youdao.sortinghat.config.CommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisBaseDao
 *
 * @author 
 *
 */
public abstract class BaseRedisDao {

    @Autowired
    protected RedisTemplate redisTemplate;

    protected final String getBaseKey() {
        return CommonConfig.getRedisKey();
    }

    protected abstract String getMainKey();

    protected String getKey(String... keys) {
        String wholeKey = getBaseKey() + "::" + getMainKey();
        for (String key : keys) {
            wholeKey = wholeKey + "::" + key;
        }
        return wholeKey;
    }

}