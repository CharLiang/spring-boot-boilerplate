/**
 * @(#)StudentQueryDTO.java, 2019-03-21.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.youdao.sortinghat.dao.mysql.model.StudentEntity;

import java.util.List;

/**
 * StudentQueryDTO
 *
 * @author 
 *
 */
public class StudentQueryDTO extends StudentDTO {

    private List<OrganizationDTO> classList;

    private List<Integer> service;

    private String schoolProvince;

    private String schoolCity;

    private String schoolDistrict;

    private String schoolAddress;

    private String schoolName;

    public List<OrganizationDTO> getClassList() {
        return classList;
    }

    public void setClassList(List<OrganizationDTO> classList) {
        this.classList = classList;
    }

    public List<Integer> getService() {
        return service;
    }

    public void setService(List<Integer> service) {
        this.service = service;
    }

    public String getSchoolProvince() {
        return schoolProvince;
    }

    public void setSchoolProvince(String schoolProvince) {
        this.schoolProvince = schoolProvince;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(String schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public StudentQueryDTO() {
    }

    public StudentQueryDTO(int id, String name, String no, String mobile, String sn, int schoolId, int status,
                           List<OrganizationDTO> classList, List<Integer> service, String schoolProvince,
                           String schoolCity, String schoolDistrict, String schoolAddress, String schoolName) {
        super(id, name, no, mobile, sn, schoolId, status);
        this.classList = classList;
        this.service = service;
        this.schoolProvince = schoolProvince;
        this.schoolCity = schoolCity;
        this.schoolDistrict = schoolDistrict;
        this.schoolAddress = schoolAddress;
        this.schoolName = schoolName;
    }

    public StudentQueryDTO(StudentEntity studentEntity, List<OrganizationDTO> classList, List<Integer> service,
                           String schoolName, String schoolProvince, String schoolCity, String schoolDistrict,
                           String schoolAddress) {
        super(studentEntity);
        this.classList = classList;
        this.service = service;
        this.schoolProvince = schoolProvince;
        this.schoolCity = schoolCity;
        this.schoolDistrict = schoolDistrict;
        this.schoolAddress = schoolAddress;
        this.schoolName = schoolName;
    }
}