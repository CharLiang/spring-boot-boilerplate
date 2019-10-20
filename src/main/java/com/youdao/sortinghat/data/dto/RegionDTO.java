/**
 * @(#)RegionDTO.java, 2019-04-02.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.RegionEntity;

/**
 * RegionDTO
 *
 * @author 
 *
 */
public class RegionDTO {

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

    public RegionDTO() {
    }

    public RegionDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public RegionDTO(RegionEntity regionEntity) {
        this.id = regionEntity.getId();
        this.name = regionEntity.getRegionName();
    }
}