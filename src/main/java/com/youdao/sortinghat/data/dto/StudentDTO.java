/**
 * @(#)StudentDTO.java, 2019-03-21.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.StudentEntity;

/**
 * StudentDTO
 *
 * @author 
 *
 */
public class StudentDTO {

    private int id;

    private String name;

    private String no;

    private String mobile;

    private String sn;

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public StudentDTO() {
    }

    public StudentDTO(int id, String name, String no, String mobile, String sn, int schoolId, int status) {
        this.id = id;
        this.name = name;
        this.no = no;
        this.mobile = mobile;
        this.sn = sn;
        this.schoolId = schoolId;
        this.status = status;
    }

    public StudentDTO(StudentEntity studentEntity) {
        this.id = studentEntity.getId();
        this.name = studentEntity.getName();
        this.no = studentEntity.getNo();
        this.mobile = studentEntity.getMobile();
        this.sn = String.valueOf(studentEntity.getSn());
        this.schoolId = studentEntity.getSchoolId();
        this.status = studentEntity.getStatus();
    }
}