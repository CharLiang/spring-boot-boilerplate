/**
 * @(#)LockRedisDao.java, 2018-09-06.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.dao.redis;

import com.youdao.sortinghat.dao.redis.base.BaseRedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * LockRedisDao
 *
 * @author 
 *
 */
@Component
public class LockRedisDao extends BaseRedisDao {

    public static final long expiredSeconds = 60 * 5;

    public static final String mainKey = "lock";

    private Logger logger = LoggerFactory.getLogger(LockRedisDao.class);

    public boolean lock(String lockName) {
        String key = getKey(lockName);
        boolean lock = false;
        try {
            lock = redisTemplate.opsForValue().setIfAbsent(key, "1");
            if (lock) {
                // 5分钟
                redisTemplate.expire(key, expiredSeconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            logger.error("[LockRedisDao] lock error lockName:" + lockName, e);
            e.printStackTrace();
        }
        return lock;
    }

    public boolean unLock(String lockName) {
        String key = getKey(lockName);
        boolean unLock = false;
        try {
            redisTemplate.delete(key);
            unLock = true;
        } catch (Exception e) {
            logger.error("[LockRedisDao] unLock error lockName:" + lockName, e);
            e.printStackTrace();
        }
        return unLock;
    }

    @Override
    protected String getMainKey() {
        return mainKey;
    }
}