package com.youdao.sortinghat.dao.mysql.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youdao.sortinghat.type.entity.EntityStatusType;

import javax.persistence.*;
import java.util.Date;

@Table(name = "teacher")
public class TeacherEntity {
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
     * 教室姓名
     */
    private String name;

    /**
     * 教师编号
     */
    private String no;

    /**
     * 手机号码
     */
    private String mobile;

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
     * 获取教室姓名
     *
     * @return name - 教室姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置教室姓名
     *
     * @param name 教室姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取教师编号
     *
     * @return no - 教师编号
     */
    public String getNo() {
        return no;
    }

    /**
     * 设置教师编号
     *
     * @param no 教师编号
     */
    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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

    public TeacherEntity() {
    }

    public TeacherEntity(Integer schoolId, String name, String no, String mobile, EntityStatusType status) {
        this.schoolId = schoolId;
        this.name = name;
        this.no = no;
        this.mobile = mobile;
        this.status = status.getValue().byteValue();
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

    @Override
    public String toString() {
        return "TeacherEntity{" +
                "id=" + id +
                ", schoolId=" + schoolId +
                ", name='" + name + '\'' +
                ", no='" + no + '\'' +
                ", mobile='" + mobile + '\'' +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}