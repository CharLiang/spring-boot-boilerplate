/**
 * @(#)ForeverSchoolService.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ForeverSchoolService
 *
 * @author 
 *
 */
public class ForeverSchoolService extends AbstracSchoolService {

    @Override
    @JsonIgnore
    @JSONField(serialize=false)
    public boolean isFresh() {
        return true;
    }

    @Override
    @JsonIgnore
    @JSONField(serialize=false)
    public boolean isOverdue() {
        return false;
    }

}