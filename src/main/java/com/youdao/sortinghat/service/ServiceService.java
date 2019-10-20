/**
 * @(#)ServiceService.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.youdao.sortinghat.dao.mysql.mapper.SchoolMapper;
import com.youdao.sortinghat.dao.mysql.mapper.ServiceMapper;
import com.youdao.sortinghat.dao.mysql.mapper.ServiceTeacherMapper;
import com.youdao.sortinghat.dao.mysql.model.SchoolEntity;
import com.youdao.sortinghat.dao.mysql.model.ServiceEntity;
import com.youdao.sortinghat.dao.mysql.model.ServiceTeacherEntity;
import com.youdao.sortinghat.dao.mysql.model.TeacherEntity;
import com.youdao.sortinghat.data.common.Ret;
import com.youdao.sortinghat.data.common.SqlException;
import com.youdao.sortinghat.data.service.AbstracSchoolService;
import com.youdao.sortinghat.data.service.YoudaoOralSchoolService;
import com.youdao.sortinghat.service.base.BaseService;
import com.youdao.sortinghat.type.entity.EntityStatusType;
import com.youdao.sortinghat.type.service.SchoolServicePattern;
import javafx.util.Pair;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ServiceService
 *
 * @author 
 *
 */
@Service
public class ServiceService extends BaseService<ServiceEntity> {

    private static Logger logger = LoggerFactory.getLogger(ServiceService.class);

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private ServiceTeacherMapper serviceTeacherMapper;

    /**
     * 每小时检查一次服务是否过期
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void checkServiceStatus() {
        logger.info("check school service status start");
        List<ServiceEntity> serviceEntityList = serviceMapper.getByStatus(EntityStatusType.ACTIVATE.getValue());
        serviceEntityList.parallelStream().forEach(serviceEntity -> {
            SchoolServicePattern schoolServicePattern = serviceEntity.getSchoolServicePattern();
            AbstracSchoolService abstracSchoolService = schoolServicePattern.parseContent(serviceEntity.getContent());
            if (abstracSchoolService.isOverdue()) {
                serviceMapper.updateStatus(serviceEntity.getId(), EntityStatusType.OVERDUE.getValue(),
                        serviceEntity.getStatus());
            }
        });
        logger.info("check school service status end");
    }

    /**
     * 新增服务
     * @param schoolId
     * @param serviceType
     * @param content
     * @return
     */
    public boolean add(int schoolId, Integer serviceType, String content) {
        SchoolServicePattern schoolServicePattern = SchoolServicePattern.get(serviceType);
        if (schoolServicePattern == null) {
            logger.warn("unknown service type:" + serviceType);
            return false;
        }
        if (StringUtils.isEmpty(content)) {
            logger.warn("empty content");
            return false;
        }
        SchoolEntity schoolEntity = schoolMapper.selectByPrimaryKey(schoolId);
        if (schoolEntity == null) {
            logger.warn("can not find school id:" + schoolId);
            return false;
        }
        if (serviceMapper.getBySchoolIdAndServiceType(schoolId, serviceType) != null) {
            logger.warn("same service schoolId:" + schoolId + " serviceType:" + serviceType);
            return false;
        }
        ServiceEntity serviceEntity = new ServiceEntity(schoolId, serviceType.byteValue(), EntityStatusType.ACTIVATE, content);
        return saveNotNull(serviceEntity) == 1;
    }

    /**
     * 修改服务参数
     * @param id
     * @param content
     * @return
     */
    public boolean edit(int id, String content) {
        ServiceEntity serviceEntity = selectByKey(id);
        if (serviceEntity == null) {
            logger.warn("can not find service id:" + id);
            return false;
        }
        if (StringUtils.isEmpty(content)) {
            logger.warn("empty content");
            return false;
        }
        return serviceMapper.updateContent(id, content, serviceEntity.getContent()) == 1;
    }

    /**
     * 修改服务状态
     * @param id
     * @param status
     * @return
     */
    public boolean status(int id, int status) {
        EntityStatusType entityStatusType = EntityStatusType.get(status);
        if (entityStatusType == EntityStatusType.UNKNOWN) {
            logger.warn("unknow status id:" + id +" status:" + status);
            return false;
        }
        ServiceEntity serviceEntity = selectByKey(id);
        if (serviceEntity == null) {
            logger.warn("can not find service id:" + id);
            return false;
        }
        return serviceMapper.updateStatus(id, status, serviceEntity.getStatus()) == 1;
    }

    /**
     * 获取服务列表
     * @param schoolId
     * @return
     */
    public List<ServiceEntity> list(int schoolId) {
        return serviceMapper.getBySchoolId(schoolId);
    }

    /**
     * 获取服务列表
     * @param schoolId
     * @return
     */
    public Ret manager(int schoolId, String name, String no, String mobile) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile)) {
            logger.warn("empty name or mobile");
            return Ret.notok("message", "姓名和手机号不能为空");
        }
        SchoolEntity schoolEntity = schoolMapper.selectByPrimaryKey(schoolId);
        if (schoolEntity == null || schoolEntity.isNotActivate()) {
            logger.warn("can not find school id:" + schoolId);
            return Ret.notok("message", "未能找到学校信息");
        }
        ServiceEntity serviceEntity = serviceMapper.getBySchoolIdAndServiceType(schoolId, SchoolServicePattern.MANAGER.getValue());
        if (serviceEntity == null || serviceEntity.isNotActivate()) {
            logger.warn("can not find school manager service schoolId:" + schoolId);
            return Ret.notok("message", "该学校并没有增加教务的权限");
        }
        TeacherEntity teacherEntity = teacherService.getActivateTeacherByMobile(mobile);
        if (teacherEntity == null) {
            try {
                if (teacherService.add(schoolId, name, no, mobile) != -1) {
                    teacherEntity = teacherService.getActivateTeacherByMobile(mobile);
                }
            } catch (SqlException e) {
                logger.warn("save teacher fail " + teacherEntity.toString());
            }
        }
        if (teacherEntity.getSchoolId() != schoolId) {
            logger.error("different schoolId:" + schoolId + " " + teacherEntity.getSchoolId());
            return Ret.notok("message", "新增教师失败");
        }
        if (teacherEntity == null) {
            logger.error("add teacher error schoolId:" + schoolEntity + " name:" + name + " no:" + no + " mobile:" + mobile);
            return Ret.notok("message", "新增教师失败");
        }
        if (serviceTeacherMapper.getByServiceIdAndTeacherId(serviceEntity.getId(), teacherEntity.getId()) != null) {
            logger.warn("same school service teacher schoolId:" + schoolEntity + " name:" + name + " no:" + no + " mobile:" + mobile);
            return Ret.notok("message", "该教师已经是教务身份");
        }
        ServiceTeacherEntity serviceTeacherEntity = new ServiceTeacherEntity(serviceEntity.getId(), teacherEntity.getId());
        if (serviceTeacherMapper.insertSelective(serviceTeacherEntity) == 1) {
            return Ret.ok();
        } else {
            logger.warn("save fail schoolId:" + schoolEntity + " name:" + name + " no:" + no + " mobile:" + mobile);
            return Ret.notok("message", "新增教务失败");
        }
    }

    /**
     * 获取口语剩余人数
     * @param schoolId
     * @return
     */
    public Pair<Integer, Integer> getOralNum(int schoolId) {
        // 口语班级需要校验口语剩余人数
        ServiceEntity serviceEntity = serviceMapper.getBySchoolIdAndServiceType(schoolId,
                SchoolServicePattern.YOUDAO_ORAL.getValue());
        if (serviceEntity == null || serviceEntity.isNotActivate()) {
            logger.warn("can not find activate oral service:" + schoolId);
            return null;
        }
        SchoolServicePattern schoolServicePattern = serviceEntity.getSchoolServicePattern();
        if (schoolServicePattern.equals(SchoolServicePattern.UNKNOWN)) {
            logger.warn("unknown school serviceType:" + serviceEntity.getServiceType().intValue());
            return null;
        }
        YoudaoOralSchoolService youdaoOralSchoolService = (YoudaoOralSchoolService)
                schoolServicePattern.parseContent(serviceEntity.getContent());
        if (youdaoOralSchoolService == null) {
            logger.warn("parse content fail serviceType:" + serviceEntity.getServiceType().intValue() +
                    " content:" + serviceEntity.getContent());
            return null;
        }
        return new Pair<>(youdaoOralSchoolService.getNum(), youdaoOralSchoolService.getMax());
    }

}