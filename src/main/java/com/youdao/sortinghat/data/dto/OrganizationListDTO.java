/**
 * @(#)OrganizationNumDTO.java, 2019-03-18.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.OrganizationEntity;

/**
 * OrganizationListDTO
 *
 * @author 
 *
 */
public class OrganizationListDTO extends OrganizationDTO {

    private String typeStr;

    private String subjectStr;

    private String gradeStr;

    private int teacherNum;

    private int studentNum;

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getSubjectStr() {
        return subjectStr;
    }

    public void setSubjectStr(String subjectStr) {
        this.subjectStr = subjectStr;
    }

    public String getGradeStr() {
        return gradeStr;
    }

    public void setGradeStr(String gradeStr) {
        this.gradeStr = gradeStr;
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

    public OrganizationListDTO() {
    }

    public OrganizationListDTO(OrganizationEntity organizationEntity, int teacherNum, int studentNum) {
        super(organizationEntity);
        this.typeStr = organizationEntity.getOrganizationType().getDesc();
        this.subjectStr = organizationEntity.getSubjectType().getDesc();
        this.gradeStr = organizationEntity.getGradeType().getDesc();
        this.teacherNum = teacherNum;
        this.studentNum = studentNum;
    }
}