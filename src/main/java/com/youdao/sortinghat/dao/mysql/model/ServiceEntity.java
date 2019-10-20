package com.youdao.sortinghat.dao.mysql.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youdao.sortinghat.type.entity.EntityStatusType;
import com.youdao.sortinghat.type.service.SchoolServicePattern;

import javax.persistence.*;
import java.util.Date;

@Table(name = "service")
public class ServiceEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学校ID
     */
    @Column(name = "school_id")
    private Integer schoolId;

    /**
     * 服务代码
     */
    @Column(name = "service_type")
    private Byte serviceType;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 服务参数
     */
    private String content;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取学校ID
     *
     * @return school_id - 学校ID
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * 设置学校ID
     *
     * @param schoolId 学校ID
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取服务代码
     *
     * @return service_type - 服务代码
     */
    public Byte getServiceType() {
        return serviceType;
    }

    /**
     * 设置服务代码
     *
     * @param serviceType 服务代码
     */
    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取服务参数
     *
     * @return content - 服务参数
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置服务参数
     *
     * @param content 服务参数
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public ServiceEntity() {
    }

    public ServiceEntity(Integer schoolId, SchoolServicePattern serviceType, EntityStatusType entityStatusType, String content) {
        this.schoolId = schoolId;
        this.serviceType = serviceType.getValue().byteValue();
        this.status = entityStatusType.getValue().byteValue();
        this.content = content;
    }

    public ServiceEntity(Integer schoolId, Byte serviceType, EntityStatusType entityStatusType, String content) {
        this.schoolId = schoolId;
        this.serviceType = serviceType;
        this.status = entityStatusType.getValue().byteValue();
        this.content = content;
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public boolean isActivate() {
        return status == EntityStatusType.ACTIVATE.getValue().byteValue();
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public boolean isNotActivate() {
        return status != EntityStatusType.ACTIVATE.getValue().byteValue();
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public SchoolServicePattern getSchoolServicePattern() {
        return SchoolServicePattern.get(serviceType.intValue());
    }
}