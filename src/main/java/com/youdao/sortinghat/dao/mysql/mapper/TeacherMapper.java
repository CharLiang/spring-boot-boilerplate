package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.TeacherEntity;
import com.youdao.sortinghat.data.dto.TeacherSubjectDTO;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherMapper extends MyMapper<TeacherEntity> {

    List<Integer> getActivateTeacherIdByMobile(String mobile);

    int updateStatus(@Param("id") int id,
                     @Param("status") int status,
                     @Param("oldStatus") int oldStatus);

    List<TeacherEntity> getBySchoolId(int schoolId);

    Integer countBySchoolIdAndClassId(@Param("schoolId") int schoolId,
                                      @Param("organizationId") int organizationId);

    List<TeacherSubjectDTO> getTeacherSubjectDTOByOrganizationId(int organizationId);

    TeacherEntity getByOrganizationIdAndTeacherIdAndSubject(@Param("organizationId") int organizationId,
                                                            @Param("teacherId") int teacherId,
                                                            @Param("subject") int subject);

    List<TeacherEntity> queryLike(@Param("schoolId") int schoolId,
                                  @Param("organizationId") int organizationId,
                                  @Param("key") String key);
}