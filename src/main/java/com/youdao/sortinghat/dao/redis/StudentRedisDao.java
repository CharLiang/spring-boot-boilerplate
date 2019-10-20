/**
 * @(#)StudentRedisDao.java, 2019-03-13.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.dao.redis;

import com.youdao.sortinghat.dao.mysql.mapper.StudentMapper;
import com.youdao.sortinghat.dao.redis.base.BaseRedisDao;
import com.youdao.sortinghat.type.organization.OrganizationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * StudentRedisDao
 *
 * @author 
 *
 */
@Component
public class StudentRedisDao extends BaseRedisDao {

    public static final long expiredSeconds = 60 * 3;

    public static final String mainKey = "student";

    private Logger logger = LoggerFactory.getLogger(TeacherRedisDao.class);

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 删除缓存的学生唯一行政班id
     * @param studentId
     */
    public void removeStudentAdministrativeClassId(int studentId) {
        String key = getKey(String.valueOf(studentId), "administrative");
        redisTemplate.delete(key);
    }

    /**
     * 缓存学生唯一行政班id
     * @param studentId
     * @param administrativeClassId
     * @return
     */
    public boolean saveStudentAdministrativeClassId(int studentId, int administrativeClassId) {
        String key = getKey(String.valueOf(studentId), "administrative");
        return redisTemplate.opsForValue().setIfAbsent(key, administrativeClassId);
    }

    /**
     * 获取学生唯一行政班id
     * @param studentId
     * @return
     */
    public Integer getStudentAdministrativeClassId(int studentId) {
        String key = getKey(String.valueOf(studentId), "administrative");
        if (!redisTemplate.hasKey(key)) {
            List<Integer> studentIdList = studentMapper.getStudentIdByIdAndClassType(studentId,
                    OrganizationType.ADMINISTRATIVE.getValue());
            if (studentIdList.isEmpty()) {
                return -1;
            } else if (studentIdList.size() != 1) {
                logger.warn("more than one administrative class with same student:" + studentId);
            }
            if (redisTemplate.opsForValue().setIfAbsent(key, studentIdList.get(0))) {
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