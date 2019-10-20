package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.RegionEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionMapper extends MyMapper<RegionEntity> {

    List<RegionEntity> getByParentId(int parentId);
}