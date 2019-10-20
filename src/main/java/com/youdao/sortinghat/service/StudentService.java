/**
 * @(#)StudentService.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.youdao.sortinghat.dao.mysql.mapper.OrganizationMapper;
import com.youdao.sortinghat.dao.mysql.mapper.SchoolCounterMapper;
import com.youdao.sortinghat.dao.mysql.mapper.StudentMapper;
import com.youdao.sortinghat.dao.mysql.model.SchoolCounterEntity;
import com.youdao.sortinghat.dao.mysql.model.StudentEntity;
import com.youdao.sortinghat.data.common.SqlException;
import com.youdao.sortinghat.data.dto.StudentListDTO;
import com.youdao.sortinghat.service.base.BaseService;
import com.youdao.sortinghat.type.entity.EntityStatusType;
import com.youdao.sortinghat.util.HashUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StudentService
 *
 * @author 
 *
 */
@Service
public class StudentService extends BaseService<StudentEntity> {

    private static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SchoolCounterMapper schoolCounterMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * 新增学生
     * @param schoolId
     * @param name
     * @param no
     * @param mobile
     * @return
     */
    public boolean add(int schoolId, String name, String no, String mobile) {
        return addAndGet(schoolId, name, no, mobile) != null;
    }

    /**
     * 新增学生
     * @param schoolId
     * @param name
     * @param no
     * @param mobile
     * @return
     */
    @Transactional
    public StudentEntity addAndGet(int schoolId, String name, String no, String mobile) {
        String year = new SimpleDateFormat("yy").format(new Date());
        SchoolCounterEntity schoolCounterEntity = schoolCounterMapper.getBySchoolIdForUpdate(schoolId);
        if (schoolCounterEntity == null) {
            logger.warn("can not find school counter");
            return null;
        }
        // 超位数
        if (schoolId > 90000) {
            logger.error("schoolId too long");
            return null;
        }
        if (schoolCounterEntity.getStudentCounter() > 9999) {
            logger.error("student counter too long");
            return null;
        }
        int newCounter = schoolCounterEntity.getStudentCounter() + 1;
        String prefix = String.valueOf(10000 + schoolId);
        String studentNo = String.valueOf(Integer.valueOf(year) * 10000 + newCounter);
        // 10001 190001
        String sn = prefix + studentNo;
        if (StringUtils.isEmpty(no)) {
            no = sn;
        }
        if (studentMapper.getBySchoolIdAndNo(schoolId, no) != null) {
            logger.warn("same schoolId with no");
            return null;
        }
        StudentEntity studentEntity = new StudentEntity(schoolId, name, no, Long.valueOf(sn),
                HashUtil.md5(studentNo), mobile, EntityStatusType.ACTIVATE);
        if (saveNotNull(studentEntity) != 1) {
            logger.warn("save student fail "+ studentEntity.toString());
            return null;
        }
        if (schoolCounterMapper.updateCounter(schoolId, newCounter,
                schoolCounterEntity.getStudentCounter()) != 1) {
            logger.error("update school counter fail schoolId:" + schoolId + " counter:" + newCounter +
                    " oldCounter:" + schoolCounterEntity.getStudentCounter());
            throw new SqlException("更新学校学号计数器失败");
        }
        return studentEntity;
    }

    /**
     * 获取或新增学生信息
     * @param schoolId
     * @param name
     * @param no
     * @param mobile
     * @return
     */
    public StudentEntity getOrAddStudent(int schoolId, String name, String no, String mobile) {
        if (StringUtils.isNotEmpty(no)) {
            StudentEntity studentEntity = studentMapper.getBySchoolIdAndNo(schoolId, no);
            if (studentEntity != null) {
                return studentEntity;
            }
        }
        return addAndGet(schoolId, name, no, mobile);
    }

    /**
     * 修改学生信息
     * @param id
     * @param name
     * @param mobile
     * @return
     */
    public boolean edit(int schoolId, int id, String name, String mobile) {
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(mobile)) {
            logger.warn("empty param");
            return false;
        }
        StudentEntity studentEntity = selectByKey(id);
        if (studentEntity == null) {
            logger.warn("can not find student " + id);
            return false;
        }
        if (studentEntity.getSchoolId() != schoolId) {
            logger.warn("different schoolId " + studentEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        if (studentEntity.getName().equals(name) && studentEntity.getMobile().equals(mobile)) {
            return true;
        }
        studentEntity.setName(StringUtils.isEmpty(name)? studentEntity.getName(): name);
        studentEntity.setMobile(StringUtils.isEmpty(mobile)? studentEntity.getMobile(): mobile);
        return updateNotNull(studentEntity) == 1;
    }

    /**
     * 修改学生状态
     * @param schoolId
     * @param id
     * @param status
     * @return
     */
    public boolean status(int schoolId, int id, int status) {
        EntityStatusType entityStatusType = EntityStatusType.get(status);
        if (entityStatusType == EntityStatusType.UNKNOWN) {
            logger.warn("unknow status id:" + id +" status:" + status);
            return false;
        }
        StudentEntity studentEntity = selectByKey(id);
        if (studentEntity == null) {
            logger.warn("can not find student:" + id);
            return false;
        }
        if (studentEntity.getSchoolId() != schoolId) {
            logger.warn("different schoolId " + studentEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        return studentMapper.updateStatus(id, status, studentEntity.getStatus()) == 1;
    }

    /**
     * 获取学生列表
     * @param schoolId
     * @return
     */
    public List<StudentListDTO> list(int schoolId) {
        return generateListDTO(studentMapper.getBySchoolId(schoolId));
    }

    /**
     * 查询
     * @param schoolId
     * @param organizationId
     * @param key
     * @return
     */
    public List<StudentListDTO> queryLike(int schoolId, int organizationId, String key) {
        return generateListDTO(studentMapper.queryLike(schoolId, organizationId, key));
    }

    /**
     * 封装DTO
     * @param studentEntityList
     * @return
     */
    public List<StudentListDTO> generateListDTO(List<StudentEntity> studentEntityList) {
        return studentEntityList.stream()
                .map(studentEntity -> new StudentListDTO(studentEntity, organizationMapper.getByStudentId(studentEntity.getId())))
                .collect(Collectors.toList());
    }
}