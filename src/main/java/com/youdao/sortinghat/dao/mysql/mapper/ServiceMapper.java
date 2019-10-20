package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.ServiceEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceMapper extends MyMapper<ServiceEntity> {

    ServiceEntity getBySchoolIdAndServiceType(@Param("schoolId") int schoolId,
                                              @Param("serviceType") int serviceType);

    ServiceEntity getBySchoolIdAndServiceTypeForUpdate(@Param("schoolId") int schoolId,
                                                       @Param("serviceType") int serviceType);

    int updateContent(@Param("id") int id,
                      @Param("content") String content,
                      @Param("oldContent") String oldContent);

    int updateStatus(@Param("id") int id,
                      @Param("status") int status,
                      @Param("oldStatus") int oldStatus);

    List<ServiceEntity> getBySchoolId(int schoolId);

    List<Integer> getServiceTypeByStudentIdAndStatus(@Param("studentId") int studentId,
                                                        @Param("status") int status);

    List<Integer> getServiceTypeByTeacherIdAndStatus(@Param("teacherId") int teacherId,
                                                        @Param("status") int status);

    List<ServiceEntity> getByStatus(int status);
}