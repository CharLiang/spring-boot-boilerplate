/**
 * @(#)SchoolService.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.youdao.sortinghat.dao.mysql.mapper.SchoolCounterMapper;
import com.youdao.sortinghat.dao.mysql.mapper.SchoolMapper;
import com.youdao.sortinghat.dao.mysql.mapper.ServiceMapper;
import com.youdao.sortinghat.dao.mysql.model.SchoolCounterEntity;
import com.youdao.sortinghat.dao.mysql.model.SchoolEntity;
import com.youdao.sortinghat.dao.mysql.model.ServiceEntity;
import com.youdao.sortinghat.data.common.SqlException;
import com.youdao.sortinghat.data.dto.SchoolDTO;
import com.youdao.sortinghat.service.base.BaseService;
import com.youdao.sortinghat.type.entity.EntityStatusType;
import com.youdao.sortinghat.type.service.SchoolServicePattern;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SchoolService
 *
 * @author 
 *
 */
@Service
public class SchoolService extends BaseService<SchoolEntity> {

    private static Logger logger = LoggerFactory.getLogger(SchoolService.class);

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private SchoolCounterMapper schoolCounterMapper;

    /**
     * 新增学校
     * @param name
     * @return
     */
    @Transactional
    public int add(String name, int province, int city, int district, String address) {
        if (StringUtils.isEmpty("name")) {
            logger.warn("empty name");
            return -1;
        }
        if (schoolMapper.getSchoolByName(name) != null) {
            logger.warn("same name:" + name);
            return -1;
        }
        SchoolEntity schoolEntity = new SchoolEntity(name, province, city, district, address, EntityStatusType.ACTIVATE);
        if (saveNotNull(schoolEntity) != 1) {
            logger.warn("save school fail " + schoolEntity.toString());
            return -1;
        }
        ServiceEntity serviceEntity = new ServiceEntity(schoolEntity.getId(), SchoolServicePattern.MANAGER, EntityStatusType.ACTIVATE, "{}");
        if (serviceMapper.insertSelective(serviceEntity) != 1) {
            logger.warn("save manager service fail " + schoolEntity.toString());
            throw new SqlException("新增教务服务失败");
        }
        SchoolCounterEntity schoolCounterEntity = new SchoolCounterEntity(schoolEntity.getId());
        if (schoolCounterMapper.insertSelective(schoolCounterEntity) != 1) {
            logger.warn("save school counter fail " + schoolEntity.toString());
            throw new SqlException("新增学校学号计数器失败");
        }
        return schoolEntity.getId();
    }

    /**
     * 更新学校名称
     * @param id
     * @param name
     * @return
     */
    public boolean edit(int id, String name) {
        SchoolEntity schoolEntity = selectByKey(id);
        if (schoolEntity == null) {
            logger.warn("can not find school id:" + id);
            return false;
        }
        if (StringUtils.isEmpty(name) || schoolEntity.getName().equals(name)) {
            return true;
        }
        if (schoolMapper.getSchoolByName(name) != null) {
            logger.warn("same name:" + name);
            return false;
        }
        return schoolMapper.updateName(id, name, schoolEntity.getName()) == 1;
    }

    /**
     * 修改学校状态
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
        SchoolEntity schoolEntity = selectByKey(id);
        if (schoolEntity == null) {
            logger.warn("can not find school id:" + id);
            return false;
        }
        return schoolMapper.updateStatus(id, status, schoolEntity.getStatus()) == 1;
    }

    /**
     * 获取学校信息
     * @param schoolId
     * @return
     */
    @Cacheable(value = "getSchoolById", key = "#schoolId")
    public SchoolEntity getSchoolById(int schoolId) {
        return selectByKey(schoolId);
    }

    /**
     * 获取学校列表
     * @return
     */
    @Cacheable(value = "getSchoolList")
    public List<SchoolDTO> getSchoolList() {
        return selectAll().stream()
                .filter(SchoolEntity::isActivate)
                .map(SchoolDTO::new)
                .collect(Collectors.toList());
    }

}