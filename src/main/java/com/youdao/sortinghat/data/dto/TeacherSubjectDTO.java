/**
 * @(#)TeacherSubjectDTO.java, 2019-03-21.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.type.organization.SubjectType;

/**
 * TeacherSubjectDTO
 *
 * @author 
 *
 */
public class TeacherSubjectDTO extends TeacherDTO {

    private int subject;

    private String subjectStr;

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
        this.subjectStr = SubjectType.get(subject).getDesc();
    }

    public String getSubjectStr() {
        return subjectStr;
    }

    public void setSubjectStr(String subjectStr) {
        this.subjectStr = subjectStr;
    }

    public TeacherSubjectDTO() {
    }

    public TeacherSubjectDTO(int id, String name, String no, String mobile, int schoolId, int status, int subject, String subjectStr) {
        super(id, name, no, mobile, schoolId, status);
        this.subject = subject;
        this.subjectStr = subjectStr;
    }
}