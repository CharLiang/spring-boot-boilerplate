package com.youdao.sortinghat.dao.mysql.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "service_teacher")
public class ServiceTeacherEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 服务ID
     */
    @Column(name = "service_id")
    private Integer serviceId;

    /**
     * 教师ID
     */
    @Column(name = "teacher_id")
    private Integer teacherId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取服务ID
     *
     * @return service_id - 服务ID
     */
    public Integer getServiceId() {
        return serviceId;
    }

    /**
     * 设置服务ID
     *
     * @param serviceId 服务ID
     */
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 获取教师ID
     *
     * @return teacher_id - 教师ID
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * 设置教师ID
     *
     * @param teacherId 教师ID
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
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

    public ServiceTeacherEntity() {
    }

    public ServiceTeacherEntity(Integer serviceId, Integer teacherId) {
        this.serviceId = serviceId;
        this.teacherId = teacherId;
    }
}