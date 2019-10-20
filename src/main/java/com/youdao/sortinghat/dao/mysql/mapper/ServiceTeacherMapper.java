package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.ServiceTeacherEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTeacherMapper extends MyMapper<ServiceTeacherEntity> {

    ServiceTeacherEntity getByServiceIdAndTeacherId(@Param("serviceId") int serviceId,
                                                    @Param("teacherId") int teacherId);

    int deleteOralServiceRelationship(@Param("teacherId") int teacherId);
}