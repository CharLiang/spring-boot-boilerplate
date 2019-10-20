package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.OrganizationTeacherEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationTeacherMapper extends MyMapper<OrganizationTeacherEntity> {

    OrganizationTeacherEntity getByOrganizationIdAndTeacherIdAndSubject(@Param("organizationId") int organizationId,
                                                                        @Param("teacherId") int teacherId,
                                                                        @Param("subject") int subject);
}