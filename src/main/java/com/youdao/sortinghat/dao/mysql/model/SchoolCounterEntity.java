package com.youdao.sortinghat.dao.mysql.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "school_counter")
public class SchoolCounterEntity {
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
     * 学生序列号
     */
    @Column(name = "student_counter")
    private Integer studentCounter;

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
     * 获取学生序列号
     *
     * @return student_counter - 学生序列号
     */
    public Integer getStudentCounter() {
        return studentCounter;
    }

    /**
     * 设置学生序列号
     *
     * @param studentCounter 学生序列号
     */
    public void setStudentCounter(Integer studentCounter) {
        this.studentCounter = studentCounter;
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

    public SchoolCounterEntity() {
    }

    public SchoolCounterEntity(Integer schoolId) {
        this.schoolId = schoolId;
    }
}