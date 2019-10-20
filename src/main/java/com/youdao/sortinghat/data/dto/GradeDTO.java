/**
 * @(#)GradeDTO.java, 2019-03-15.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.type.organization.GradeType;

/**
 * GradeDTO
 *
 * @author 
 *
 */
public class GradeDTO {

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

    public GradeDTO() {
    }

    public GradeDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GradeDTO(GradeType gradeType) {
        this.id = gradeType.getValue();
        this.name = gradeType.getDesc();
    }
}