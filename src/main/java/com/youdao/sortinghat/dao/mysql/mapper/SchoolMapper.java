package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.SchoolEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolMapper extends MyMapper<SchoolEntity> {

    SchoolEntity getSchoolByName(String name);

    int updateName(@Param("id") int id,
                   @Param("name") String name,
                   @Param("oldName") String oldName);

    int updateStatus(@Param("id") int id,
                   @Param("status") int status,
                   @Param("oldStatus") int oldStatus);

}