/**
 * @(#)SchoolDTO.java, 2019-03-15.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.SchoolEntity;

/**
 * SchoolDTO
 *
 * @author 
 *
 */
public class SchoolDTO {

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

    public SchoolDTO() {
    }

    public SchoolDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SchoolDTO(SchoolEntity schoolEntity) {
        this.id = schoolEntity.getId();
        this.name = schoolEntity.getName();
    }
}