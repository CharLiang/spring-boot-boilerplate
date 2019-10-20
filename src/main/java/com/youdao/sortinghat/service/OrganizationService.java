/**
 * @(#)OrganizationService.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.youdao.sortinghat.dao.mysql.mapper.*;
import com.youdao.sortinghat.dao.mysql.model.*;
import com.youdao.sortinghat.dao.redis.StudentRedisDao;
import com.youdao.sortinghat.data.common.SqlException;
import com.youdao.sortinghat.data.dto.OrganizationDetailDTO;
import com.youdao.sortinghat.data.dto.OrganizationListDTO;
import com.youdao.sortinghat.data.dto.StudentDTO;
import com.youdao.sortinghat.data.dto.TeacherSubjectDTO;
import com.youdao.sortinghat.data.excel.StudentAddExcel;
import com.youdao.sortinghat.data.service.YoudaoOralSchoolService;
import com.youdao.sortinghat.service.base.BaseService;
import com.youdao.sortinghat.type.entity.EntityStatusType;
import com.youdao.sortinghat.type.organization.GradeType;
import com.youdao.sortinghat.type.organization.OrganizationType;
import com.youdao.sortinghat.type.organization.SubjectType;
import com.youdao.sortinghat.type.service.SchoolServicePattern;
import com.youdao.sortinghat.util.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrganizationService
 *
 * @author 
 *
 */
@Service
public class OrganizationService extends BaseService<OrganizationEntity> {

    private static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private OralService oralService;

    @Autowired
    private TermMapper termMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationTeacherMapper organizationTeacherMapper;

    @Autowired
    private OrganizationStudentMapper organizationStudentMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentRedisDao studentRedisDao;

    @Autowired
    private ServiceStudentMapper serviceStudentMapper;

    @Autowired
    private ServiceTeacherMapper serviceTeacherMapper;

    /**
     * 新增班级
     * @param schoolId
     * @param type
     * @param grade
     * @param subject
     * @param termId
     * @param name
     * @return
     */
    public int add(int schoolId, int type, int grade, int subject, int termId, String name) {
        if (!checkParam(schoolId, type, grade, subject, termId, name)) {
            return -1;
        }
        if (organizationMapper.getBySchoolIdAndTypeAndGradeAndSubjectAndTermIdAndName(schoolId, type, grade, subject,
                termId, name) != null) {
            logger.warn("same organization schoolId:" + schoolId + " type:" + type + " grade:" + grade + " subject:" +
                    subject + " termId:" + termId + " name:" + name);
            return -1;
        }
        OrganizationEntity organizationEntity = new OrganizationEntity(schoolId, termId, type, grade, subject, name, EntityStatusType.ACTIVATE);
        if (saveNotNull(organizationEntity) != 1) {
            return -1;
        }
        return organizationEntity.getId();
    }

    /**
     * 更新班级信息
     * @param schoolId
     * @param id
     * @param type
     * @param grade
     * @param subject
     * @param termId
     * @param name
     * @return
     */
    public boolean edit(int schoolId, int id, Integer type, Integer grade, Integer subject, Integer termId, String name) {
        OrganizationEntity organizationEntity = selectByKey(id);
        if (organizationEntity == null) {
            logger.warn("can not find organization:" + id);
            return false;
        }
        if (!checkParam(schoolId, type, grade, subject, termId, name)) {
            return false;
        }
        if (organizationEntity.getType().equals(type) && organizationEntity.getGrade().equals(grade) &&
                organizationEntity.getSubject().equals(subject) && organizationEntity.getTermId().equals(termId) &&
                organizationEntity.getName().equals(name)) {
            return true;
        } else if (organizationMapper.getBySchoolIdAndTypeAndGradeAndSubjectAndTermIdAndName(schoolId, type, grade, subject,
                termId, name) != null) {
            logger.warn("same organization id:" + id + " schoolId:" + schoolId + " type:" + type + " grade:" + grade + " subject:" +
                    subject + " termId:" + termId + " name:" + name);
            return false;
        }
        organizationEntity.setType(type.byteValue());
        organizationEntity.setGrade(grade.byteValue());
        organizationEntity.setSubject(subject.byteValue());
        organizationEntity.setTermId(termId);
        organizationEntity.setName(name);
        return updateNotNull(organizationEntity) == 1;
    }

    /**
     * 修改班级状态
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
        OrganizationEntity organizationEntity = selectByKey(id);
        if (organizationEntity == null) {
            logger.warn("can not find organizationEntity id:" + id);
            return false;
        }
        return organizationMapper.updateStatus(id, status, organizationEntity.getStatus()) == 1;
    }

    /**
     * 获取班级详细信息
     * @param schoolId
     * @param id
     * @return
     */
    public OrganizationDetailDTO detail(int schoolId, int id) {
        OrganizationEntity organizationEntity = organizationMapper.getById(id);
        if (organizationEntity == null || organizationEntity.isNotActivate()) {
            logger.warn("can not find organizationEntity id:" + id);
            return null;
        }
        if (organizationEntity.getSchoolId() != schoolId) {
            logger.warn("different schoolId " + organizationEntity.getSchoolId() + " " + schoolId);
            return null;
        }
        List<StudentDTO> studentEntityList = studentMapper.getStudentDTOByOrganizationId(organizationEntity.getId());
        List<TeacherSubjectDTO> teacherEntityList = teacherMapper.getTeacherSubjectDTOByOrganizationId(organizationEntity.getId());
        return new OrganizationDetailDTO(organizationEntity, teacherEntityList, studentEntityList);
    }

    /**
     * 班级列表
     * @param schoolId
     * @return
     */
    public List<OrganizationListDTO> list(int schoolId) {
        return generateListDTO(organizationMapper.getBySchoolId(schoolId));
    }

    /**
     * 班级配置教师
     * @param schoolId
     * @param id
     * @param teacherId
     * @param subject
     * @return
     */
    @Transactional
    public boolean addTeacher(int schoolId, int id, int teacherId, int subject) {
        SchoolEntity schoolEntity = schoolService.getSchoolById(schoolId);
        if (schoolEntity == null || schoolEntity.isNotActivate()) {
            logger.warn("can not find school:" + schoolId);
            return false;
        }
        OrganizationEntity organizationEntity = selectByKey(id);
        if (organizationEntity == null) {
            logger.warn("can not find organizationEntity id:" + id);
            return false;
        }
        if (organizationEntity.getSchoolId() != schoolId) {
            logger.warn("different organization schoolId " + organizationEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        SubjectType subjectType = SubjectType.get(subject);
        if (subjectType.equals(SubjectType.UNKNOWN)) {
            logger.warn("unknown subjectType:" + subject);
            return false;
        }
        if (organizationEntity.getSubjectType().equals(SubjectType.YOUDAO_ORAL) &&
                !subjectType.equals(SubjectType.YOUDAO_ORAL)) {
            logger.warn("youdao oral organization needs oral teacher");
            return false;
        }
        TeacherEntity teacherEntity = teacherMapper.selectByPrimaryKey(teacherId);
        if (teacherEntity == null) {
            logger.warn("can not find teacher:" + teacherId);
            return false;
        }
        if (teacherEntity.getSchoolId() != schoolId) {
            logger.warn("different teacher schoolId " + organizationEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        if (teacherMapper.getByOrganizationIdAndTeacherIdAndSubject(id, teacherId, subject) != null) {
            logger.warn("same teacher");
            return false;
        }
        OrganizationTeacherEntity organizationTeacherEntity = new OrganizationTeacherEntity(id, teacherId, subjectType);
        if (organizationTeacherMapper.insertSelective(organizationTeacherEntity) != 1) {
            return false;
        }
        if (organizationEntity.getSubjectType().equals(SubjectType.YOUDAO_ORAL)) {
            ServiceEntity serviceEntity = serviceMapper.getBySchoolIdAndServiceType(schoolId,
                    SchoolServicePattern.YOUDAO_ORAL.getValue());
            if (serviceEntity == null || serviceEntity.isNotActivate()) {
                logger.warn("can not find activate oral service:" + schoolId);
                throw new SqlException("找不到口语服务");
            }
            if (serviceTeacherMapper.getByServiceIdAndTeacherId(serviceEntity.getId(), teacherId) == null) {
                ServiceTeacherEntity serviceTeacherEntity = new ServiceTeacherEntity(serviceEntity.getId(), teacherId);
                if (serviceTeacherMapper.insertSelective(serviceTeacherEntity) != 1) {
                    logger.warn("insert teacher service fail");
                    throw new SqlException("增加口语服务关系失败");
                }
            }
        }
        return true;
    }

    /**
     * 班级删除教师
     * @param schoolId
     * @param teacherId
     * @param organizationId
     * @param subject
     * @return
     */
    public boolean delTeacher(int schoolId, int teacherId, int organizationId, int subject) {
        OrganizationTeacherEntity organizationTeacherEntity =
                organizationTeacherMapper.getByOrganizationIdAndTeacherIdAndSubject(organizationId, teacherId, subject);
        if (organizationTeacherEntity == null) {
            logger.warn("can not find teacher relationship organizationId:" + organizationId + " teacherId:" +
                    teacherId + " subject:" + subject);
            return false;
        }
        OrganizationEntity organizationEntity = selectByKey(organizationTeacherEntity.getOrganizationId());
        if (organizationEntity == null) {
            logger.warn("can not find organization:" + organizationTeacherEntity.getOrganizationId());
            return false;
        }
        if (organizationEntity.getSchoolId() != schoolId) {
            logger.warn("different organization schoolId " + organizationEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        if (organizationTeacherMapper.deleteByPrimaryKey(organizationTeacherEntity.getId()) != 1) {
            logger.warn("delete relationship fail organizationId:" + organizationId + " teacherId:" + teacherId);
            return false;
        }
        serviceTeacherMapper.deleteOralServiceRelationship(teacherId);
        return true;
    }

    /**
     * 新增班级学生
     * 注意:
     * 1、行政班一个学生只能有一个
     * 2、教学班的口语班需要校验服务参数
     * @param schoolId
     * @param id
     * @param name
     * @param no
     * @param mobile
     * @return
     */
    @Transactional
    public int addStudent(int schoolId, int id, String name, String no, String mobile) {
        SchoolEntity schoolEntity = schoolService.getSchoolById(schoolId);
        if (schoolEntity == null || schoolEntity.isNotActivate()) {
            logger.warn("can not find school:" + schoolId);
            return -1;
        }
        OrganizationEntity organizationEntity = selectByKey(id);
        if (organizationEntity == null) {
            logger.warn("can not find organizationEntity id:" + id);
            return -1;
        }
        if (organizationEntity.getSchoolId() != schoolId) {
            logger.warn("different organization schoolId " + organizationEntity.getSchoolId() + " " + schoolId);
            return -1;
        }
        if (organizationEntity.getOrganizationType().equals(OrganizationType.TEACHING) && StringUtils.isEmpty(no)) {
            logger.warn("teaching class but empty no");
            return -1;
        }
        StudentEntity studentEntity = studentService.getOrAddStudent(schoolId, name, no, mobile);
        if (studentEntity == null) {
            return -1;
        }
        if (studentMapper.getByStudentIdAndClassId(studentEntity.getId(), organizationEntity.getId()) != null) {
            logger.warn("same student");
            return -1;
        }
        if (organizationEntity.getOrganizationType().equals(OrganizationType.ADMINISTRATIVE)) {
            // 一个学生只能出现在一个行政班当中
            if (studentRedisDao.getStudentAdministrativeClassId(studentEntity.getId()) != -1 ||
                    !studentRedisDao.saveStudentAdministrativeClassId(studentEntity.getId(), organizationEntity.getId())) {
                logger.warn("administrative conflict");
                return -1;
            }
            OrganizationStudentEntity organizationStudentEntity = new OrganizationStudentEntity(organizationEntity.getId(),
                    studentEntity.getId());
            if (organizationStudentMapper.insertSelective(organizationStudentEntity) == 1) {
                return studentEntity.getId();
            } else {
                logger.warn("insert fail");
                studentRedisDao.removeStudentAdministrativeClassId(studentEntity.getId());
                return -1;
            }
        } else if (organizationEntity.getSubjectType().equals(SubjectType.YOUDAO_ORAL)) {
            if (StringUtils.isEmpty(studentEntity.getMobile())) {
                logger.warn("can not insert empty mobile student into oral class");
                return -1;
            }
            // 口语班级需要校验口语剩余人数
            ServiceEntity serviceEntity = serviceMapper.getBySchoolIdAndServiceTypeForUpdate(schoolId,
                    SchoolServicePattern.YOUDAO_ORAL.getValue());
            if (serviceEntity == null || serviceEntity.isNotActivate()) {
                logger.warn("can not find activate oral service:" + schoolId);
                return -1;
            }
            SchoolServicePattern schoolServicePattern = serviceEntity.getSchoolServicePattern();
            if (schoolServicePattern.equals(SchoolServicePattern.UNKNOWN)) {
                logger.warn("unknown school serviceType:" + serviceEntity.getServiceType().intValue());
                return -1;
            }
            YoudaoOralSchoolService youdaoOralSchoolService = (YoudaoOralSchoolService)
                    schoolServicePattern.parseContent(serviceEntity.getContent());
            if (youdaoOralSchoolService == null) {
                logger.warn("parse content fail serviceType:" + serviceEntity.getServiceType().intValue() +
                        " content:" + serviceEntity.getContent());
                return -1;
            }
            if (youdaoOralSchoolService.getNum() <= 0) {
                logger.warn("out of stock:" + serviceEntity.getId());
                return -1;
            }
            OrganizationStudentEntity organizationStudentEntity = new OrganizationStudentEntity(organizationEntity.getId(),
                    studentEntity.getId());
            if (organizationStudentMapper.insertSelective(organizationStudentEntity) != 1) {
                logger.warn("insert fail");
                return -1;
            }
            youdaoOralSchoolService.setNum(youdaoOralSchoolService.getNum() - 1);
            if (serviceMapper.updateContent(serviceEntity.getId(), JSON.toJSONString(youdaoOralSchoolService), serviceEntity.getContent()) != 1) {
                logger.warn("update service fail");
                throw new SqlException("新增学生失败");
            }
            if (serviceStudentMapper.getByServiceIdAndStudentId(serviceEntity.getId(), studentEntity.getId()) == null) {
                ServiceStudentEntity serviceStudentEntity = new ServiceStudentEntity(serviceEntity.getId(), studentEntity.getId());
                if (serviceStudentMapper.insertSelective(serviceStudentEntity) != 1) {
                    logger.warn("insert service student relationship fail");
                    throw new SqlException("新增学生失败");
                }
            }
            oralService.classDeleteOne(organizationEntity.getId(), studentEntity.getMobile());
            if (!oralService.classAddOne(organizationEntity.getId(), studentEntity.getMobile())) {
                logger.warn("add class and student to oral server fail");
                throw new SqlException("新增学生失败");
            }
            if (!oralService.codeAddAll(studentEntity.getMobile())) {
                logger.warn("add code to oral server fail");
                throw new SqlException("新增学生失败");
            }
            return studentEntity.getId();
        } else {
            // 直接添加学生
            OrganizationStudentEntity organizationStudentEntity = new OrganizationStudentEntity(organizationEntity.getId(),
                    studentEntity.getId());
            if (organizationStudentMapper.insertSelective(organizationStudentEntity) != 1) {
                return -1;
            }
            return studentEntity.getId();
        }
    }

    /**
     * 批量增加学生信息
     * @param schoolId
     * @param id
     * @param file
     * @return
     */
    @Transactional
    public boolean addStudentByExcel(int schoolId, int id, MultipartFile file) {
        if (file == null) {
            logger.warn("can not find file");
            return false;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<Integer> studentIdList = Lists.newArrayList();
        try (InputStream inputStream = file.getInputStream()) {
            List<StudentAddExcel> studentAddExcelList = ExcelImportUtil.importExcel(inputStream, StudentAddExcel.class,
                    params);
            for (StudentAddExcel studentAddExcel : studentAddExcelList) {
                int studentId = addStudent(schoolId, id, studentAddExcel.getName(), studentAddExcel.getNo(),
                        studentAddExcel.getMobile());
                if (studentId == -1) {
                    logger.warn("add student fail school:" + schoolId + " id:" + id + " name:" +
                            studentAddExcel.getName() + " no:" + studentAddExcel.getNo() + " mobile:" +
                            studentAddExcel.getMobile());
                    throw new SqlException("新增学生失败 姓名：" + studentAddExcel.getName() + " 学号：" +
                            studentAddExcel.getNo() + " 手机号：" + studentAddExcel.getMobile());
                } else {
                    studentIdList.add(studentId);
                }
            }
        } catch (IOException e) {
            logger.warn("file upload fail");
            return false;
        } catch (SqlException e) {
            throw e;
        } catch (Exception e) {
            logger.error("addByExcel fail", e);
            for (Integer studentId : studentIdList) {
                studentRedisDao.removeStudentAdministrativeClassId(studentId);
            }
            throw new SqlException(e.getMessage(), e);
        }
        return true;
    }

    /**
     * 下载表格样例
     * @param response
     */
    public void downloadStudentExcelExample(HttpServletResponse response) throws IOException {
        ExcelUtil.exportExcel(Lists.newArrayList(), "批量新增学生", "批量新增学生", StudentAddExcel.class,
                "批量新增学生表格样例.xls", response);
    }

    /**
     * 删除班级学生
     * @param schoolId
     * @param organizationId
     * @param studentId
     * @return
     */
    @Transactional
    public boolean delStudent(int schoolId, int organizationId, int studentId) {
        OrganizationStudentEntity organizationStudentEntity =
                organizationStudentMapper.getByOrganizationIdAndStudentId(organizationId, studentId);
        if (organizationStudentEntity == null) {
            logger.warn("can not find teacher relationship organizationId:" + organizationId + " studentId:" + studentId);
            return false;
        }
        OrganizationEntity organizationEntity = selectByKey(organizationStudentEntity.getOrganizationId());
        if (organizationEntity == null) {
            logger.warn("can not find organization:" + organizationStudentEntity.getOrganizationId());
            return false;
        }
        if (organizationEntity.getSchoolId() != schoolId) {
            logger.warn("different organization schoolId " + organizationEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        StudentEntity studentEntity = studentMapper.selectByPrimaryKey(studentId);
        if (studentEntity == null) {
            logger.warn("can not find student studentId:" + studentId);
            return false;
        }
        if (studentEntity.getSchoolId() != schoolId) {
            logger.warn("different student schoolId " + studentEntity.getSchoolId() + " " + schoolId);
            return false;
        }
        if (organizationStudentMapper.deleteByPrimaryKey(organizationStudentEntity.getId()) != 1) {
            logger.warn("delete relationship fail organizationId:" + organizationId + " studentId:" + studentId);
            return false;
        }
        if (organizationEntity.getOrganizationType().equals(OrganizationType.ADMINISTRATIVE)) {
            studentRedisDao.removeStudentAdministrativeClassId(studentId);
        }
        if (organizationEntity.getSubjectType().equals(SubjectType.YOUDAO_ORAL)) {
            // TODO: 暂不增加人数 但是要删除关系
            serviceStudentMapper.deleteOralServiceRelationship(studentId);
            if (!oralService.classDeleteOne(organizationId, studentEntity.getMobile())) {
                logger.warn("delete relationship fail from oral server organizationId:" + organizationId + " studentId:" + studentId);
                throw new SqlException("删除学生失败");
            }
        }
        return true;
    }

    /**
     * 教师列表上方班级搜索条
     * @param schoolId
     * @param type
     * @param grade
     * @param subject
     * @return
     */
    public JSONArray queryByAdminForTeacher(int schoolId, int type, int grade, int subject) {
        List<OrganizationEntity> organizationEntityList = organizationMapper.query(schoolId, type, grade, subject,
                -1, "", EntityStatusType.UNKNOWN.getValue());
        return new JSONArray(organizationEntityList.stream()
                .filter(OrganizationEntity::isActivate)
                .map(organizationEntity -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", organizationEntity.getId());
                    jsonObject.put("name", organizationEntity.getName());
                    return jsonObject;
                }).collect(Collectors.toList()));
    }

    /**
     * 搜索
     * @param schoolId
     * @param type
     * @param grade
     * @param subject
     * @param name
     * @return
     */
    public List<OrganizationListDTO> query(int schoolId, int type, int grade, int subject, String name) {
        return generateListDTO(organizationMapper.query(schoolId, type, grade, subject,
                -1, name, EntityStatusType.UNKNOWN.getValue()));
    }

    /**
     * 检查名字是否可用
     * @param schoolId
     * @param name
     * @return
     */
    public boolean checkNameAvailable(int schoolId, String name) {
        return organizationMapper.getBySchoolIdAndName(schoolId, name).isEmpty();
    }

    /**
     * 封装列表DTO
     * @param organizationEntityList
     * @return
     */
    private List<OrganizationListDTO> generateListDTO(List<OrganizationEntity> organizationEntityList) {
        return organizationEntityList.stream()
                .map(organizationEntity -> new OrganizationListDTO(organizationEntity,
                        studentMapper.countBySchoolIdAndClassId(organizationEntity.getSchoolId(), organizationEntity.getId()),
                        teacherMapper.countBySchoolIdAndClassId(organizationEntity.getSchoolId(), organizationEntity.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 参数校验
     * @param schoolId
     * @param type
     * @param grade
     * @param subject
     * @param termId
     * @param name
     * @return
     */
    private boolean checkParam(int schoolId, int type, int grade, int subject, int termId, String name) {
        SchoolEntity schoolEntity = schoolService.getSchoolById(schoolId);
        if (schoolEntity == null || schoolEntity.isNotActivate()) {
            logger.warn("can not find school:" + schoolId);
            return false;
        }
        OrganizationType organizationType = OrganizationType.get(type);
        if (organizationType.equals(OrganizationType.UNKNOWN)) {
            logger.warn("unknown organizationType:" + type);
            return false;
        }
        if (organizationType.equals(OrganizationType.ADMINISTRATIVE)) {
            subject = SubjectType.ANYSUBJECT.getValue();
        }
        GradeType gradeType = GradeType.get(grade);
        if (gradeType.equals(GradeType.UNKNOWN)) {
            logger.warn("unknown gradeType:" + grade);
            return false;
        }
        SubjectType subjectType = SubjectType.get(subject);
        if (subjectType.equals(SubjectType.UNKNOWN)) {
            logger.warn("unknown subjectType:" + subject);
            return false;
        }
        if (subjectType.equals(SubjectType.YOUDAO_ORAL)) {
            ServiceEntity serviceEntity = serviceMapper.getBySchoolIdAndServiceType(schoolId, SchoolServicePattern.YOUDAO_ORAL.getValue());
            if (schoolEntity == null || serviceEntity.isNotActivate()) {
                logger.warn("can find activate youdao_oral service schoolId:" + schoolId + " type:" + type +
                        " grade:" + grade + " subject:" + subject + " termId:" + termId + " name:" + name);
                return false;
            }
        }
        TermEntity termEntity = termMapper.selectByPrimaryKey(termId);
        if (termEntity == null) {
            logger.warn("can not find term:" + termId);
            return false;
        }
        if (StringUtils.isEmpty(name)) {
            logger.warn("empty name schoolId:" + schoolId + " type:" + type + " grade:" + grade + " subject:" +
                    subject + " termId:" + termId + " name:" + name);
            return false;
        }
        return true;
    }
}