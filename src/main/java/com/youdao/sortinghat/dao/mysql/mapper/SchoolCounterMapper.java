package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.SchoolCounterEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolCounterMapper extends MyMapper<SchoolCounterEntity> {

    SchoolCounterEntity getBySchoolIdForUpdate(int schoolId);

    int updateCounter(@Param("schoolId") int schoolId,
                      @Param("counter") int counter,
                      @Param("oldCounter") int oldCounter);
}