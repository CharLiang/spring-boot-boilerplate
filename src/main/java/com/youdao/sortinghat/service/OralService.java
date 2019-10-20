/**
 * @(#)OralService.java, 2019-04-02.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.alibaba.fastjson.JSONObject;
import com.youdao.sortinghat.config.CommonConfig;
import com.youdao.sortinghat.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OralService
 *
 * @author 
 *
 */
@Service
public class OralService {

    private static Logger logger = LoggerFactory.getLogger(OralService.class);

    /**
     * 添加学生班级关系
     * @param organizationId
     * @param studentPhone
     * @return
     */
    public boolean classAddOne(int organizationId, String studentPhone) {
        MultiValueMap param = new LinkedMultiValueMap<String, String>();
        param.add("classId", String.valueOf(organizationId));
        param.add("phone", studentPhone);
        String json = HttpUtil.post(CommonConfig.getUrlOralClassAddOne(), param);
        if (StringUtils.isEmpty(json)) {
            logger.warn("http fail classId:" + organizationId + " phone:" + studentPhone);
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (!jsonObject.containsKey("success") || !jsonObject.getBoolean("success")) {
            logger.warn("add one fail classId:" + organizationId + " phone:" + studentPhone);
            return false;
        }
        return true;
    }

    /**
     * 批量更新班级学生关系
     * @param organizationId
     * @param studentPhoneList
     * @return
     */
    public boolean classUpdate(int organizationId, List<String> studentPhoneList) {
        MultiValueMap param = new LinkedMultiValueMap<String, String>();
        param.add("classId", String.valueOf(organizationId));
        param.put("phones", studentPhoneList);
        String json = HttpUtil.post(CommonConfig.getUrlOralClassUpdate(), param);
        if (StringUtils.isEmpty(json)) {
            logger.warn("update fail classId:" + organizationId + " phones:" + studentPhoneList.toString());
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (!jsonObject.containsKey("success") || !jsonObject.getBoolean("success")) {
            logger.warn("update fail classId:" + organizationId + " phones:" + studentPhoneList.toString());
            return false;
        }
        return true;
    }

    /**
     * 删除班级学生关系
     * @param organizationId
     * @param studentPhone
     * @return
     */
    public boolean classDeleteOne(int organizationId, String studentPhone) {
        MultiValueMap param = new LinkedMultiValueMap<String, String>();
        param.add("classId", String.valueOf(organizationId));
        param.add("phone", studentPhone);
        String json = HttpUtil.post(CommonConfig.getUrlOralClassDeleteOne(), param);
        if (StringUtils.isEmpty(json)) {
            logger.warn("http fail classId:" + organizationId + " phone:" + studentPhone);
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (!jsonObject.containsKey("success") || !jsonObject.getBoolean("success")) {
            logger.warn("delete one fail classId:" + organizationId + " phone:" + studentPhone);
            return false;
        }
        return true;
    }

    /**
     * 兑换激活码
     * @param studentPhone
     * @return
     */
    public boolean codeAddAll(String studentPhone) {
        MultiValueMap param = new LinkedMultiValueMap<String, String>();
        param.add("cdKeyId", "30MC_20181220_ToB");
        param.add("phones", studentPhone);
        param.add("serviceStartTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String json = HttpUtil.post(CommonConfig.getUrlOralCodeAddAll(), param);
        if (StringUtils.isEmpty(json)) {
            logger.warn("http fail studentPhone:" + studentPhone);
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (!jsonObject.containsKey("success") || !jsonObject.getBoolean("success")) {
            logger.warn("add code fail studentPhone:" + studentPhone);
            return false;
        }
        return true;
    }

}