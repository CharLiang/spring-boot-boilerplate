/**
 * @(#)OrganizationQueryDTO.java, 2019-03-21.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.OrganizationEntity;

import java.util.List;

/**
 * OrganizationQueryDTO
 *
 * @author 
 *
 */
public class OrganizationQueryDTO extends OrganizationDTO {

    private int teacherNum;

    private int studentNum;

    private List<TeacherSubjectDTO> teacherList;

    private List<StudentDTO> studentList;

    private String schoolProvince;

    private String schoolCity;

    private String schoolDistrict;

    private String schoolAddress;

    private String schoolName;

    public int getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(int teacherNum) {
        this.teacherNum = teacherNum;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public List<TeacherSubjectDTO> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherSubjectDTO> teacherList) {
        this.teacherList = teacherList;
    }

    public List<StudentDTO> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDTO> studentList) {
        this.studentList = studentList;
    }

    public String getSchoolProvince() {
        return schoolProvince;
    }

    public void setSchoolProvince(String schoolProvince) {
        this.schoolProvince = schoolProvince;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(String schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public OrganizationQueryDTO() {
    }

    public OrganizationQueryDTO(OrganizationEntity organizationEntity, List<TeacherSubjectDTO> teacherList,
                                List<StudentDTO> studentList, String schoolName, String schoolProvince,
                                String schoolCity, String schoolDistrict, String schoolAddress) {
        super(organizationEntity);
        this.teacherList = teacherList;
        this.studentList = studentList;
        this.studentNum = studentList.size();
        this.teacherNum = teacherList.size();
        this.schoolName = schoolName;
        this.schoolProvince = schoolProvince;
        this.schoolCity = schoolCity;
        this.schoolDistrict = schoolDistrict;
        this.schoolAddress = schoolAddress;
        this.schoolName = schoolName;
    }
}