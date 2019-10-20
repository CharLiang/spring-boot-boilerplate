package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.StudentEntity;
import com.youdao.sortinghat.data.dto.StudentDTO;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper extends MyMapper<StudentEntity> {

    List<StudentEntity> getBySchoolId(int schoolId);

    Integer countBySchoolIdAndClassId(@Param("schoolId") int schoolId,
                                      @Param("organizationId") int organizationId);

    int updateStatus(@Param("id") int id,
                     @Param("status") int status,
                     @Param("oldStatus") int oldStatus);

    List<StudentDTO> getStudentDTOByOrganizationId(int organizationId);

    StudentEntity getBySchoolIdAndNo(@Param("schoolId") int schoolId,
                                     @Param("no") String no);

    List<Integer> getStudentIdByIdAndClassType(@Param("id") int id,
                                               @Param("classType") int classType);

    StudentEntity getByStudentIdAndClassId(@Param("id") int id,
                                           @Param("organizationId") int organizationId);

    List<StudentEntity> queryLike(@Param("schoolId") int schoolId,
                                  @Param("organizationId") int organizationId,
                                  @Param("key") String key);
}