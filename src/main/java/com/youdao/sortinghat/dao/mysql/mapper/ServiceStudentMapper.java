package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.ServiceStudentEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceStudentMapper extends MyMapper<ServiceStudentEntity> {

    ServiceStudentEntity getByServiceIdAndStudentId(@Param("serviceId") int serviceId,
                                                    @Param("studentId") int studentId);

    int deleteOralServiceRelationship(@Param("studentId") int studentId);
}