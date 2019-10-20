/**
 * @(#)TeacherListDTO.java, 2019-03-15.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.youdao.sortinghat.dao.mysql.model.TeacherEntity;
import com.youdao.sortinghat.type.organization.SubjectType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TeacherListDTO
 *
 * @author 
 *
 */
public class TeacherListDTO extends TeacherDTO{

    List<SubjectDTO> subjectList;

    List<OrganizationDTO> classList;

    @JsonIgnore
    public List<SubjectDTO> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SubjectDTO> subjectList) {
        this.subjectList = subjectList;
    }

    @JsonIgnore
    public List<OrganizationDTO> getClassList() {
        return classList;
    }

    public void setClassList(List<OrganizationDTO> classList) {
        this.classList = classList;
    }

    public List<String> getSubjectStrList() {
        return subjectList.stream()
                .map(SubjectDTO::getName)
                .collect(Collectors.toList());
    }

    public List<String> getClassStrList() {
        return classList.stream()
                .map(OrganizationDTO::getName)
                .collect(Collectors.toList());
    }

    public TeacherListDTO() {
    }

    public TeacherListDTO(int id, String name, String no, String mobile, int schoolId, int status, List<SubjectDTO> subjectList, List<OrganizationDTO> classList) {
        super(id, name, no, mobile, schoolId, status);
        this.subjectList = subjectList;
        this.classList = classList;
    }

    public TeacherListDTO(TeacherEntity teacherEntity, List<Map> organizationAndSubjectMapList) {
        super(teacherEntity.getId(), teacherEntity.getName(), teacherEntity.getNo(), teacherEntity.getMobile(),
                teacherEntity.getSchoolId(), teacherEntity.getStatus());
        this.subjectList = Lists.newArrayList();
        this.classList = Lists.newArrayList();
        for (Map map : organizationAndSubjectMapList) {
            subjectList.add(new SubjectDTO(SubjectType.get((Integer) map.get("subjectId"))));
            classList.add(new OrganizationDTO((Integer) map.get("organizationId"), (String) map.get("organizationName"),
                    ((Integer) map.get("organizationType")).byteValue()));
        }
    }
}