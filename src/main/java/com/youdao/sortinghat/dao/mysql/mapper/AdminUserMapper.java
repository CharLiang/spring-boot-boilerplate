package com.youdao.sortinghat.dao.mysql.mapper;

import com.youdao.sortinghat.dao.mysql.model.AdminUserEntity;
import com.youdao.sortinghat.util.MyMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserMapper extends MyMapper<AdminUserEntity> {

    AdminUserEntity getAdminByUserId(String userId);
}