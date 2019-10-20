package com.youdao.sortinghat.dao.mysql.model;

import com.youdao.sortinghat.type.organization.SubjectType;

import javax.persistence.*;
import java.util.Date;

@Table(name = "organization_teacher")
public class OrganizationTeacherEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 班级ID
     */
    @Column(name = "organization_id")
    private Integer organizationId;

    /**
     * 教师ID
     */
    @Column(name = "teacher_id")
    private Integer teacherId;

    /**
     * 学科
     */
    private Byte subject;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取班级ID
     *
     * @return organization_id - 班级ID
     */
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * 设置班级ID
     *
     * @param organizationId 班级ID
     */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 获取教师ID
     *
     * @return teacher_id - 教师ID
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * 设置教师ID
     *
     * @param teacherId 教师ID
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 获取学科
     *
     * @return subject - 学科
     */
    public Byte getSubject() {
        return subject;
    }

    /**
     * 设置学科
     *
     * @param subject 学科
     */
    public void setSubject(Byte subject) {
        this.subject = subject;
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

    public OrganizationTeacherEntity() {
    }

    public OrganizationTeacherEntity(Integer organizationId, Integer teacherId, SubjectType subjectType) {
        this.organizationId = organizationId;
        this.teacherId = teacherId;
        this.subject = subjectType.getValue().byteValue();
    }
}