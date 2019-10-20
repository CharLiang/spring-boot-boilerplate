package com.youdao.sortinghat.dao.mysql.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youdao.sortinghat.type.entity.EntityStatusType;
import com.youdao.sortinghat.type.organization.GradeType;
import com.youdao.sortinghat.type.organization.OrganizationType;
import com.youdao.sortinghat.type.organization.SubjectType;

import javax.persistence.*;
import java.util.Date;

@Table(name = "organization")
public class OrganizationEntity {
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
     * 学期ID
     */
    @Column(name = "term_id")
    private Integer termId;

    /**
     * 班级类型
     */
    private Byte type;

    /**
     * 班级年级
     */
    private Byte grade;

    /**
     * 班级学科
     */
    private Byte subject;

    /**
     * 班级名称
     */
    private String name;

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
     * 获取学期ID
     *
     * @return term_id - 学期ID
     */
    public Integer getTermId() {
        return termId;
    }

    /**
     * 设置学期ID
     *
     * @param termId 学期ID
     */
    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    /**
     * 获取班级类型
     *
     * @return type - 班级类型
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置班级类型
     *
     * @param type 班级类型
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取班级年级
     *
     * @return grade - 班级年级
     */
    public Byte getGrade() {
        return grade;
    }

    /**
     * 设置班级年级
     *
     * @param grade 班级年级
     */
    public void setGrade(Byte grade) {
        this.grade = grade;
    }

    /**
     * 获取班级学科
     *
     * @return subject - 班级学科
     */
    public Byte getSubject() {
        return subject;
    }

    /**
     * 设置班级学科
     *
     * @param subject 班级学科
     */
    public void setSubject(Byte subject) {
        this.subject = subject;
    }

    /**
     * 获取班级名称
     *
     * @return name - 班级名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置班级名称
     *
     * @param name 班级名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public OrganizationEntity() {
    }

    public OrganizationEntity(Integer schoolId, Integer termId, Integer type, Integer grade, Integer subject, String name, EntityStatusType entityStatusType) {
        this.schoolId = schoolId;
        this.termId = termId;
        this.type = type.byteValue();
        this.grade = grade.byteValue();
        this.subject = subject.byteValue();
        this.name = name;
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

    @JsonIgnore
    @JSONField(serialize=false)
    public SubjectType getSubjectType() {
        return SubjectType.get(subject.intValue());
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public OrganizationType getOrganizationType() {
        return OrganizationType.get(type.intValue());
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public GradeType getGradeType() {
        return GradeType.get(grade.intValue());
    }
}