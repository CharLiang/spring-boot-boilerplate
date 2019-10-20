package com.youdao.sortinghat.dao.mysql.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youdao.sortinghat.type.entity.EntityStatusType;

import javax.persistence.*;
import java.util.Date;

@Table(name = "student")
public class StudentEntity {
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
     * 学生姓名
     */
    private String name;

    /**
     * 学生编号
     */
    private String no;

    /**
     * 学生全局编号
     */
    private Long sn;

    /**
     * 密码
     */
    private String password;

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
     * 获取学生姓名
     *
     * @return name - 学生姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置学生姓名
     *
     * @param name 学生姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取学生编号
     *
     * @return no - 学生编号
     */
    public String getNo() {
        return no;
    }

    /**
     * 设置学生编号
     *
     * @param no 学生编号
     */
    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    /**
     * 获取学生全局编号
     *
     * @return sn - 学生局编号
     */
    public Long getSn() {
        return sn;
    }

    /**
     * 设置学生全局编号
     *
     * @param sn 学生全局编号
     */
    public void setSn(Long sn) {
        this.sn = sn;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public StudentEntity() {
    }

    public StudentEntity(Integer schoolId, String name, String no, Long sn, String password, String mobile, EntityStatusType entityStatusType) {
        this.schoolId = schoolId;
        this.name = name;
        this.no = no;
        this.sn = sn;
        this.password = password;
        this.mobile = mobile;
        this.status = entityStatusType.getValue().byteValue();
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
        return "StudentEntity{" +
                "id=" + id +
                ", schoolId=" + schoolId +
                ", name='" + name + '\'' +
                ", no='" + no + '\'' +
                ", sn=" + sn +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}