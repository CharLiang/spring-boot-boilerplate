package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.OrganizationStudentEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationStudentMapper extends MyMapper<OrganizationStudentEntity> {

    OrganizationStudentEntity getByOrganizationIdAndStudentId(@Param("organizationId") int organizationId,
                                                              @Param("studentId") int studentId);
}