/**
 * @(#)OrganizationDTO.java, 2019-03-15.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.OrganizationEntity;

/**
 * OrganizationDTO
 *
 * @author 
 *
 */
public class OrganizationDTO {

    private int id;

    private String name;

    private Byte type;

    private int termId;

    private int grade;

    private int subject;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public OrganizationDTO() {
    }

    public OrganizationDTO(int id, String name, Byte type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public OrganizationDTO(int id, String name, Byte type, int termId, int grade, int subject, int status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.termId = termId;
        this.grade = grade;
        this.subject = subject;
        this.status = status;
    }

    public OrganizationDTO(OrganizationEntity organizationEntity) {
        this.id = organizationEntity.getId();
        this.name = organizationEntity.getName();
        this.type = organizationEntity.getType();
        this.termId = organizationEntity.getTermId();
        this.grade = organizationEntity.getGrade();
        this.subject = organizationEntity.getSubject();
        this.status = organizationEntity.getStatus();
    }
}