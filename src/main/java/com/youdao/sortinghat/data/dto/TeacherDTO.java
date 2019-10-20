/**
 * @(#)TeacherDTO.java, 2019-03-21.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.TeacherEntity;

/**
 * TeacherDTO
 *
 * @author 
 *
 */
public class TeacherDTO {

    private int id;

    private String name;

    private String no;

    private String mobile;

    private int schoolId;

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TeacherDTO() {
    }

    public TeacherDTO(int id, String name, String no, String mobile, int schoolId, int status) {
        this.id = id;
        this.name = name;
        this.no = no;
        this.mobile = mobile;
        this.schoolId = schoolId;
        this.status = status;
    }

    public TeacherDTO(TeacherEntity teacherEntity) {
        this.id = teacherEntity.getId();
        this.name = teacherEntity.getName();
        this.no = teacherEntity.getNo();
        this.mobile = teacherEntity.getMobile();
        this.schoolId = teacherEntity.getSchoolId();
        this.status = teacherEntity.getStatus();
    }
}