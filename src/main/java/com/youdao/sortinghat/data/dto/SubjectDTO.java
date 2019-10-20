/**
 * @(#)SubjectDTO.java, 2019-03-15.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.type.organization.SubjectType;

/**
 * SubjectDTO
 *
 * @author 
 *
 */
public class SubjectDTO {

    private int id;

    private String name;

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

    public SubjectDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubjectDTO(SubjectType subjectType) {
        this.id = subjectType.getValue();
        this.name = subjectType.getDesc();
    }
}