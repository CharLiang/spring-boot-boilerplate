/**
 * @(#)StudentDetailDTO.java, 2019-03-15.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.youdao.sortinghat.dao.mysql.model.OrganizationEntity;
import com.youdao.sortinghat.dao.mysql.model.StudentEntity;
import com.youdao.sortinghat.type.organization.OrganizationType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * StudentListDTO
 *
 * @author 
 *
 */
public class StudentListDTO extends StudentDTO {

    private String password;

    private List<OrganizationDTO> classList;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public List<OrganizationDTO> getClassList() {
        return classList;
    }

    public void setClassList(List<OrganizationDTO> classList) {
        this.classList = classList;
    }

    public List<String> getClassStrList() {
        List<String> list = Lists.newArrayList();
        list.addAll(classList.stream()
                .filter(organizationDTO -> organizationDTO.getType() == OrganizationType.ADMINISTRATIVE.getValue().byteValue())
                .map(OrganizationDTO::getName)
                .collect(Collectors.toList()));
        list.addAll(classList.stream()
                .filter(organizationDTO -> organizationDTO.getType() == OrganizationType.TEACHING.getValue().byteValue())
                .map(OrganizationDTO::getName)
                .collect(Collectors.toList()));
        return list;
    }

    public StudentListDTO() {
    }

    public StudentListDTO(int id, String name, String no, String mobile, String sn, int schoolId, int status, String password, List<OrganizationDTO> classList) {
        super(id, name, no, mobile, sn, schoolId, status);
        this.password = password;
        this.classList = classList;
    }

    public StudentListDTO(StudentEntity studentEntity, List<OrganizationEntity> organizationEntityList) {
        super(studentEntity.getId(), studentEntity.getName(), studentEntity.getNo(), studentEntity.getMobile(),
                String.valueOf(studentEntity.getSn()), studentEntity.getSchoolId(), studentEntity.getStatus());
        this.password = String.valueOf(studentEntity.getSn()).substring(5);
        classList = organizationEntityList.stream()
                .map(OrganizationDTO::new)
                .collect(Collectors.toList());
    }
}