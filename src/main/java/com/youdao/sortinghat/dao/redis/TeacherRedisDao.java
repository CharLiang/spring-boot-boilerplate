/**
 * @(#)TeacherRedisDao.java, 2019-03-11.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.dao.redis;

import com.youdao.sortinghat.dao.mysql.mapper.TeacherMapper;
import com.youdao.sortinghat.dao.redis.base.BaseRedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TeacherRedisDao
 *
 * @author 
 *
 */
@Component
public class TeacherRedisDao extends BaseRedisDao {

    public static final long expiredSeconds = 60 * 3;

    public static final String mainKey = "teacher";

    private Logger logger = LoggerFactory.getLogger(TeacherRedisDao.class);

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 缓存唯一教师ID
     * @param mobile
     * @param teacherId
     * @return
     */
    public boolean saveTeacherId(String mobile, Integer teacherId) {
        String key = getKey(mobile);
        return redisTemplate.opsForValue().setIfAbsent(key, teacherId);
    }

    /**
     * 删除缓存的唯一教师ID
     * @param mobile
     */
    public void removeTeacherId(String mobile) {
        String key = getKey(mobile);
        redisTemplate.delete(key);
    }

    /**
     * 获取唯一教师id
     * @param mobile
     * @return
     */
    public Integer getTeacherIdByMobile(String mobile) {
        String key = getKey(mobile);
        if (!redisTemplate.hasKey(key)) {
            List<Integer> teacherIdList = teacherMapper.getActivateTeacherIdByMobile(mobile);
            if (teacherIdList.isEmpty()) {
                return -1;
            } else if (teacherIdList.size() != 1) {
                logger.warn("more than one activate teacher with same mobile:" + mobile);
            }
            if (redisTemplate.opsForValue().setIfAbsent(key, teacherIdList.get(0))) {
                redisTemplate.expire(key, expiredSeconds, TimeUnit.SECONDS);
            }
        }
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    @Override
    protected String getMainKey() {
        return mainKey;
    }
}