/**
 * @(#)SchoolServicePattern.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.type.service;

import com.alibaba.fastjson.JSON;
import com.youdao.sortinghat.data.service.AbstracSchoolService;
import com.youdao.sortinghat.data.service.ForeverSchoolService;
import com.youdao.sortinghat.data.service.YoudaoOralSchoolService;
import org.apache.commons.lang.StringUtils;

/**
 * SchoolServicePattern
 *
 * @author 
 *
 */
public enum SchoolServicePattern {

    MANAGER(1, "教务服务", ForeverSchoolService.class),

    YOUDAO_ORAL(2, "有道口语服务", YoudaoOralSchoolService.class),

    UNKNOWN(-1, "未知服务", String.class);

    private String desc;

    private Integer value;

    private Class clazz;

    public String getDesc() {
        return desc;
    }

    public Integer getValue() {
        return value;
    }

    public Class getClazz() {
        return clazz;
    }

    SchoolServicePattern() {
    }

    SchoolServicePattern(int value, String desc, Class clazz) {
        this.desc = desc;
        this.value = value;
        this.clazz = clazz;
    }

    public AbstracSchoolService parseContent(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return (AbstracSchoolService) JSON.parseObject(content, this.clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static SchoolServicePattern get(Integer value) {
        for (SchoolServicePattern schoolServicePattern : values()) {
            if (schoolServicePattern.getValue().equals(value)) {
                return schoolServicePattern;
            }
        }
        return UNKNOWN;
    }
}