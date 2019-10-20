/**
 * @(#)TeacherService.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.google.common.collect.Lists;
import com.youdao.sortinghat.dao.mysql.mapper.OrganizationMapper;
import com.youdao.sortinghat.dao.mysql.mapper.TeacherMapper;
import com.youdao.sortinghat.dao.mysql.model.TeacherEntity;
import com.youdao.sortinghat.dao.redis.TeacherRedisDao;
import com.youdao.sortinghat.data.common.SqlException;
import com.youdao.sortinghat.data.dto.TeacherListDTO;
import com.youdao.sortinghat.data.excel.TeacherAddExcel;
import com.youdao.sortinghat.service.base.BaseService;
import com.youdao.sortinghat.type.entity.EntityStatusType;
import com.youdao.sortinghat.util.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TeacherService
 *
 * @author 
 *
 */
@Service
public class TeacherService extends BaseService<TeacherEntity> {

    private static Logger logger = LoggerFactory.getLogger(ServiceService.class);

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherRedisDao teacherRedisDao;

    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * 新增教师
     * @param schoolId
     * @param name
     * @param no
     * @param mobile
     * @return
     */
    @Transactional
    public int add(int schoolId, String name, String no, String mobile) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile)) {
            logger.warn("empty name or mobile");
            return -1;
        }
        if (teacherRedisDao.getTeacherIdByMobile(mobile) != -1) {
            logger.warn("same mobile");
            return -1;
        }
        TeacherEntity teacherEntity = new TeacherEntity(schoolId, name, no, mobile, EntityStatusType.ACTIVATE);
        if (saveNotNull(teacherEntity) != 1) {
            logger.warn("insert fail " + teacherEntity.toString());
            return -1;
        }
        if (!teacherRedisDao.saveTeacherId(mobile, teacherEntity.getId())) {
            logger.warn("save redis fail " + teacherEntity.toString());
            throw new SqlException("缓存唯一教师ID失败");
        }
        return teacherEntity.getId();
    }

    /**
     * 批量上传老师信息
     * @param schoolId
     * @param file
     * @return
     */
    @Transactional
    public boolean addByExcel(int schoolId, MultipartFile file) {
        if (file == null) {
            logger.warn("can not find file");
            return false;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<String> mobileList = Lists.newArrayList();
        try (InputStream inputStream = file.getInputStream()) {
            List<TeacherAddExcel> teacherAddExcelList = ExcelImportUtil.importExcel(inputStream, TeacherAddExcel.class, params);
            for (TeacherAddExcel teacherAddExcel : teacherAddExcelList) {
                mobileList.add(teacherAddExcel.getMobile());
                if (add(schoolId, teacherAddExcel.getName(), teacherAddExcel.getNo(), teacherAddExcel.getMobile()) == -1) {
                    logger.warn("add teacher fail school:" + schoolId + " name:" + teacherAddExcel.getName() +
                            " no:" + teacherAddExcel.getNo() + " mobile:" + teacherAddExcel.getMobile());
                    throw new SqlException("新增教师失败 姓名：" + teacherAddExcel.getName() + " 工号：" + teacherAddExcel.getNo() + " 手机号：" + teacherAddExcel.getMobile());
                }
            }
        } catch (IOException e) {
            logger.warn("file upload fail");
            return false;
        } catch (SqlException e) {
            throw e;
        } catch (Exception e) {
            logger.error("addByExcel fail", e);
            for (String mobile : mobileList) {
                teacherRedisDao.removeTeacherId(mobile);
            }
            throw new SqlException(e.getMessage(), e);
        }
        return true;
    }

    /**
     * 下载表格样例
     * @param response
     */
    public void downloadTeacherExcelExample(HttpServletResponse response) throws IOException {
        ExcelUtil.exportExcel(Lists.newArrayList(), "批量新增教师", "批量新增教师", TeacherAddExcel.class,
                "批量新增教师表格样例.xls", response);
    }

    /**
     * 修改教师信息 mobile需要redis判定
     * @param schoolId
     * @param id
     * @param name
     * @param no
     * @param mobile
     * @return
     */
    @Transactional
    public boolean edit(int schoolId, String managerMobile, int id, String name, String no, String mobile) {
        TeacherEntity teacherEntity = selectByKey(id);
        if (teacherEntity == null) {
            logger.warn("can not find teacher:" + id);
            return false;
        }
        if (teacherEntity.getSchoolId() != schoolId) {
            logger.warn("different schoolId " + teacherEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        if (teacherEntity.getMobile().equals(managerMobile)) {
            logger.warn("can not edit manager's mobile");
            return false;
        }
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(no) && StringUtils.isEmpty(mobile)) {
            logger.warn("empty param");
            return false;
        }
        if (teacherEntity.getName().equals(name) && teacherEntity.getNo().equals(no) && teacherEntity.getMobile().equals(mobile)) {
            return true;
        }
        teacherEntity.setName(StringUtils.isEmpty(name)? teacherEntity.getName(): name);
        teacherEntity.setNo(StringUtils.isEmpty(no)? teacherEntity.getNo(): no);
        if (StringUtils.isNotEmpty(mobile) && !teacherEntity.getMobile().equals(mobile)) {
            if (teacherRedisDao.getTeacherIdByMobile(mobile) != -1) {
                logger.warn("same mobile");
                return false;
            }
            teacherRedisDao.removeTeacherId(teacherEntity.getMobile());
            teacherEntity.setMobile(mobile);
            if (updateNotNull(teacherEntity) != 1) {
                logger.warn("update fail " + teacherEntity.toString());
                return false;
            }
            if (!teacherRedisDao.saveTeacherId(mobile, teacherEntity.getId())) {
                logger.warn("save redis fail " + teacherEntity.toString());
                throw new SqlException("缓存唯一教师ID失败");
            }
        } else {
            if (updateNotNull(teacherEntity) != 1) {
                logger.warn("update fail " + teacherEntity.toString());
                return false;
            }
        }
        return true;
    }

    /**
     * 修改教师状态
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
        TeacherEntity teacherEntity = selectByKey(id);
        if (teacherEntity == null) {
            logger.warn("can not find teacher:" + id);
            return false;
        }
        if (teacherEntity.getSchoolId() != schoolId) {
            logger.warn("different schoolId " + teacherEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        return teacherMapper.updateStatus(id, status, teacherEntity.getStatus()) == 1;
    }

    /**
     * 获取教师列表
     * @param schoolId
     * @return
     */
    public List<TeacherListDTO> list(int schoolId) {
        return generateListDTO(teacherMapper.getBySchoolId(schoolId));
    }

    /**
     * 查询教师信息
     * @param schoolId
     * @param organizationId
     * @param key
     * @return
     */
    public List<TeacherListDTO> queryLike(int schoolId, int organizationId, String key) {
        return generateListDTO(teacherMapper.queryLike(schoolId, organizationId, key));
    }

    /**
     * 封装DTO
     * @param teacherEntityList
     * @return
     */
    public List<TeacherListDTO> generateListDTO(List<TeacherEntity> teacherEntityList) {
        return teacherEntityList.stream()
                .map(teacherEntity -> new TeacherListDTO(teacherEntity,
                        organizationMapper.getOrganizationAndSubjectByTeacherId(teacherEntity.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 通过手机号获取唯一启用中的教师信息
     * @param mobile
     * @return
     */
    @Cacheable(value = "getActivateTeacherByMobile", unless = "#result == null", key = "#mobile")
    public TeacherEntity getActivateTeacherByMobile(String mobile) {
        Integer teacherId = teacherRedisDao.getTeacherIdByMobile(mobile);
        if (teacherId == -1) {
            return null;
        }
        return teacherMapper.selectByPrimaryKey(teacherId);
    }
}