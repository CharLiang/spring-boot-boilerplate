/**
 * @(#)OrganizationDetailDTO.java, 2019-03-18.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youdao.sortinghat.dao.mysql.model.OrganizationEntity;

import java.util.List;

/**
 * OrganizationDetailDTO
 *
 * @author 
 *
 */
public class OrganizationDetailDTO extends OrganizationDTO {

    private String typeStr;

    private int teacherNum;

    private int studentNum;

    private List<TeacherSubjectDTO> teacherList;

    private List<StudentDTO> studentList;

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
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

    @JsonIgnore
    @Override
    public Byte getType() {
        return super.getType();
    }

    @JsonIgnore
    @Override
    public int getTermId() {
        return super.getTermId();
    }

    @JsonIgnore
    @Override
    public int getGrade() {
        return super.getGrade();
    }

    @JsonIgnore
    @Override
    public int getSubject() {
        return super.getSubject();
    }

    public OrganizationDetailDTO() {
    }

    public OrganizationDetailDTO(OrganizationEntity organizationEntity, List<TeacherSubjectDTO> teacherList,
                                 List<StudentDTO> studentList) {
        super(organizationEntity);
        this.teacherList = teacherList;
        this.studentList = studentList;
        this.studentNum = studentList.size();
        this.teacherNum = teacherList.size();
        this.typeStr = organizationEntity.getOrganizationType().getDesc();
    }
}