package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.OrganizationEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrganizationMapper extends MyMapper<OrganizationEntity> {

    OrganizationEntity getBySchoolIdAndTypeAndGradeAndSubjectAndTermIdAndName(@Param("schoolId") int schoolId,
                                                                              @Param("type") int type,
                                                                              @Param("grade") int grade,
                                                                              @Param("subject") int subject,
                                                                              @Param("termId") int termId,
                                                                              @Param("name") String name);

    List<OrganizationEntity> getBySchoolIdAndName(@Param("schoolId") int schoolId,
                                                  @Param("name") String name);

    int updateStatus(@Param("id") int id,
                     @Param("status") int status,
                     @Param("oldStatus") int oldStatus);

    List<OrganizationEntity> getBySchoolId(int schoolId);

    List<OrganizationEntity> getByStudentId(int studentId);

    List<OrganizationEntity> getByStudentIdAndStatus(@Param("studentId") int studentId,
                                                        @Param("status") int status);

    List<OrganizationEntity> getByTeacherIdAndStatus(@Param("teacherId") int teacherId,
                                                        @Param("status") int status);

    List<OrganizationEntity> query(@Param("schoolId") int schoolId,
                                   @Param("type") int type,
                                   @Param("grade") int grade,
                                   @Param("subject") int subject,
                                   @Param("termId") int termId,
                                   @Param("name") String name,
                                   @Param("status") int status);

    OrganizationEntity getById(int id);

    List<Map> getOrganizationAndSubjectByTeacherId(int teacherId);
}