package com.youdao.sortinghat.dao.mysql.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youdao.sortinghat.type.entity.EntityStatusType;

import javax.persistence.*;
import java.util.Date;

@Table(name = "school")
public class SchoolEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学校名称
     */
    private String name;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 地区
     */
    private Integer district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取学校名称
     *
     * @return name - 学校名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置学校名称
     *
     * @param name 学校名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取学省份ID
     *
     * @return province - 省份ID
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * 设置省份ID
     *
     * @param province 省份ID
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * 获取城市ID
     *
     * @return city - 城市ID
     */
    public Integer getCity() {
        return city;
    }

    /**
     * 设置城市ID
     *
     * @param city 城市ID
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * 获取地区ID
     *
     * @return district - 地区ID
     */
    public Integer getDistrict() {
        return district;
    }

    /**
     * 设置地区ID
     *
     * @param district 地区ID
     */
    public void setDistrict(Integer district) {
        this.district = district;
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    public SchoolEntity() {
    }

    public SchoolEntity(String name) {
        this.name = name;
    }

    public SchoolEntity(String name, EntityStatusType status) {
        this.name = name;
        this.status = status.getValue().byteValue();
    }

    public SchoolEntity(String name, Integer province, Integer city, Integer district, String address, EntityStatusType status) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.district = district;
        this.address = address;
        this.status = status.getValue().byteValue();
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public boolean isActivate() {
        return status == EntityStatusType.ACTIVATE.getValue().byteValue();
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public boolean isNotActivate() {
        return status != EntityStatusType.ACTIVATE.getValue().byteValue();
    }

    @Override
    public String toString() {
        return "SchoolEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}