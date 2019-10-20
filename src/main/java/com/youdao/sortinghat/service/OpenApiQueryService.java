/**
 * @(#)OpenApiQueryService.java, 2019-03-13.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.google.common.collect.Lists;
import com.youdao.sortinghat.dao.mysql.mapper.OrganizationMapper;
import com.youdao.sortinghat.dao.mysql.mapper.ServiceMapper;
import com.youdao.sortinghat.dao.mysql.mapper.StudentMapper;
import com.youdao.sortinghat.dao.mysql.mapper.TeacherMapper;
import com.youdao.sortinghat.dao.mysql.model.*;
import com.youdao.sortinghat.data.dto.*;
import com.youdao.sortinghat.type.entity.EntityStatusType;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OpenApiQueryService
 *
 * @author 
 *
 */
@Service
public class OpenApiQueryService {

    private static Logger logger = LoggerFactory.getLogger(OpenApiQueryService.class);

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    /**
     * 批量查询班级详情
     * @param ids
     * @return
     */
    public List<OrganizationQueryDTO> getOrganizationDetailByIds(int[] ids) {
        List<OrganizationQueryDTO> organizationDetailDTOList = Lists.newArrayList();
        for (int i = 0; i < ids.length; i++) {
            OrganizationQueryDTO organizationQueryDTO = getOrganizationDetailById(ids[i]);
            if (organizationQueryDTO != null) {
                organizationDetailDTOList.add(organizationQueryDTO);
            }
        }
        return organizationDetailDTOList;
    }

    /**
     * 获取班级详情
     * @param id
     * @return
     */
    @Cacheable(value = "getOrganizationDetailById", key = "#id")
    public OrganizationQueryDTO getOrganizationDetailById(int id) {
        OrganizationEntity organizationEntity = organizationMapper.selectByPrimaryKey(id);
        if (organizationEntity == null || organizationEntity.isNotActivate()) {
            logger.warn("can not find organizationEntity id:" + id);
            return null;
        }
        SchoolEntity schoolEntity = schoolService.getSchoolById(organizationEntity.getSchoolId());
        if (schoolEntity == null) {
            logger.warn("can not find schoolEntity id:" + organizationEntity.getSchoolId());
            return null;
        }
        String schoolProvince = cacheService.getRegionCache().getUnchecked(schoolEntity.getProvince()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        String schoolCity = cacheService.getRegionCache().getUnchecked(schoolEntity.getCity()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        String schoolDistrict = cacheService.getRegionCache().getUnchecked(schoolEntity.getDistrict()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        List<StudentDTO> studentEntityList = studentMapper.getStudentDTOByOrganizationId(organizationEntity.getId());
        List<TeacherSubjectDTO> teacherEntityList = teacherMapper.getTeacherSubjectDTOByOrganizationId(organizationEntity.getId());
        OrganizationQueryDTO organizationQueryDTO = new OrganizationQueryDTO(organizationEntity,
                teacherEntityList, studentEntityList, schoolEntity.getName(), schoolProvince, schoolCity,
                schoolDistrict, schoolEntity.getAddress());
        return organizationQueryDTO;
    }

    /**
     * 通过学校和学号匹配学生
     * @param schoolId
     * @param no
     * @return
     */
    @Cacheable(value = "getStudentBySchoolIdAndNo", key = "#schoolId + ':' + #no")
    public StudentQueryDTO getStudentBySchoolIdAndNo(int schoolId, String no) {
        StudentEntity studentEntity = studentMapper.getBySchoolIdAndNo(schoolId, no);
        if (studentEntity == null || studentEntity.isNotActivate()) {
            logger.warn("can not find studentEntity schoolId:" + schoolId + " no:" + no);
            return null;
        }
        SchoolEntity schoolEntity = schoolService.getSchoolById(studentEntity.getSchoolId());
        if (schoolEntity == null) {
            logger.warn("can not find schoolEntity id:" + studentEntity.getSchoolId());
            return null;
        }
        String schoolProvince = cacheService.getRegionCache().getUnchecked(schoolEntity.getProvince()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        String schoolCity = cacheService.getRegionCache().getUnchecked(schoolEntity.getCity()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        String schoolDistrict = cacheService.getRegionCache().getUnchecked(schoolEntity.getDistrict()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        StudentQueryDTO studentQueryDTO = new StudentQueryDTO(studentEntity,
                organizationMapper.getByStudentIdAndStatus(studentEntity.getId(), EntityStatusType.ACTIVATE.getValue())
                        .stream().map(OrganizationDTO::new).collect(Collectors.toList()),
                serviceMapper.getServiceTypeByStudentIdAndStatus(studentEntity.getId(),
                        EntityStatusType.ACTIVATE.getValue()), schoolEntity.getName(), schoolProvince, schoolCity,
                schoolDistrict, schoolEntity.getAddress());
        return studentQueryDTO;
    }

    /**
     * 通过手机号获取唯一启用中的教师信息
     * @param mobile
     * @return
     */
    @Cacheable(value = "getTeacherByMobile", key = "#mobile")
    public TeacherQueryDTO getTeacherByMobile(String mobile) {
        TeacherEntity teacherEntity = teacherService.getActivateTeacherByMobile(mobile);
        if (teacherEntity == null || teacherEntity.isNotActivate()) {
            logger.warn("can not find teacherEntity mobile:" + mobile);
            return null;
        }
        SchoolEntity schoolEntity = schoolService.getSchoolById(teacherEntity.getSchoolId());
        if (schoolEntity == null) {
            logger.warn("can not find schoolEntity id:" + teacherEntity.getSchoolId());
            return null;
        }
        String schoolProvince = cacheService.getRegionCache().getUnchecked(schoolEntity.getProvince()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        String schoolCity = cacheService.getRegionCache().getUnchecked(schoolEntity.getCity()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        String schoolDistrict = cacheService.getRegionCache().getUnchecked(schoolEntity.getDistrict()).map(Pair::getKey).map(RegionEntity::getRegionName).orElse("");
        TeacherQueryDTO teacherQueryDTO = new TeacherQueryDTO(teacherEntity,
                organizationMapper.getByTeacherIdAndStatus(teacherEntity.getId(), EntityStatusType.ACTIVATE.getValue())
                        .stream().map(OrganizationDTO::new).collect(Collectors.toList()),
                serviceMapper.getServiceTypeByTeacherIdAndStatus(teacherEntity.getId(),
                        EntityStatusType.ACTIVATE.getValue()), schoolEntity.getName(), schoolProvince, schoolCity,
                schoolDistrict, schoolEntity.getAddress());
        return teacherQueryDTO;
    }
}