/**
 * @(#)TermDTO.java, 2019-03-15.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.TermEntity;

/**
 * TermDTO
 *
 * @author 
 *
 */
public class TermDTO {

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

    public TermDTO() {
    }

    public TermDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TermDTO(TermEntity termEntity) {
        this.id = termEntity.getId();
        this.name = termEntity.getName();
    }
}