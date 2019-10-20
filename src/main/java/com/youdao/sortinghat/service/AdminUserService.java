/**
 * @(#)AdminService.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.youdao.sortinghat.dao.mysql.mapper.AdminUserMapper;
import com.youdao.sortinghat.dao.mysql.model.AdminUserEntity;
import com.youdao.sortinghat.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * AdminService
 *
 * @author 
 *
 */
@Service
public class AdminUserService extends BaseService<AdminUserEntity> {

    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 通过用户ID获取管理员信息
     * @param userId
     * @return
     */
    @Cacheable(value = "getAdminByUserId", key = "#userId")
    public AdminUserEntity getAdminByUserId(String userId) {
        return adminUserMapper.getAdminByUserId(userId);
    }
}