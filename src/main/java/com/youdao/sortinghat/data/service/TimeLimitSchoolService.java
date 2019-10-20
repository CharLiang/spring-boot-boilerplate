/**
 * @(#)TimeLimitSchoolService.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.service;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TimeLimitSchoolService
 *
 * @author 
 *
 */
public class TimeLimitSchoolService extends AbstracSchoolService {

    private Date startDate;

    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TimeLimitSchoolService() {
    }

    public TimeLimitSchoolService(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    @JSONField(serialize=false)
    public boolean isFresh() {
        Date now = new Date();
        return now.after(startDate) && now.before(endDate);
    }

    @Override
    @JSONField(serialize=false)
    public boolean isOverdue() {
        return !isFresh();
    }
}