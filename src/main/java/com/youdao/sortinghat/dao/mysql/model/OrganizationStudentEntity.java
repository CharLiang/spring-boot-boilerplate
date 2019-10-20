package com.youdao.sortinghat.dao.mysql.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "organization_student")
public class OrganizationStudentEntity {
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
     * 学生ID
     */
    @Column(name = "student_id")
    private Integer studentId;

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
     * 获取学生ID
     *
     * @return student_id - 学生ID
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * 设置学生ID
     *
     * @param studentId 学生ID
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public OrganizationStudentEntity() {
    }

    public OrganizationStudentEntity(Integer organizationId, Integer studentId) {
        this.organizationId = organizationId;
        this.studentId = studentId;
    }
}