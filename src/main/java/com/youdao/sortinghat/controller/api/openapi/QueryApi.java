/**
 * @(#)QueryApi.java, 2019-03-13.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api.openapi;

import com.youdao.sortinghat.annotation.InnerIp;
import com.youdao.sortinghat.controller.api.base.BaseApi;
import com.youdao.sortinghat.data.dto.OrganizationQueryDTO;
import com.youdao.sortinghat.data.dto.StudentQueryDTO;
import com.youdao.sortinghat.data.dto.TeacherQueryDTO;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.OpenApiQueryService;
import com.youdao.sortinghat.type.organization.OrganizationType;
import com.youdao.sortinghat.type.organization.SubjectType;
import com.youdao.sortinghat.type.service.SchoolServicePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * QueryApi
 *
 * @author 
 *
 */
@InnerIp
@RestController
@RequestMapping("/api/query")
public class QueryApi extends BaseApi {

    @Autowired
    private OpenApiQueryService openApiQueryService;

    /**
     * 通过学校和学号匹配学生信息
     * @param schoolId
     * @param no
     * @return
     */
    @GetMapping("/student")
    public Object student(@RequestParam int schoolId,
                          @RequestParam String no) {
        StudentQueryDTO studentQueryDTO = openApiQueryService.getStudentBySchoolIdAndNo(schoolId, no);
        if (studentQueryDTO == null) {
            return Result.failure("未能找到学生信息");
        } else {
            return Result.success(studentQueryDTO);
        }
    }

    /**
     * 通过手机号匹配教师信息
     * @param mobile
     * @return
     */
    @GetMapping("/teacher")
    public Object teacher(@RequestParam String mobile) {
        TeacherQueryDTO teacherQueryDTO = openApiQueryService.getTeacherByMobile(mobile);
        if (teacherQueryDTO == null) {
            return Result.failure("未能找到教师信息");
        } else {
            return Result.success(teacherQueryDTO);
        }
    }

    /**
     * 通过手机号匹配口语教师信息
     * @param mobile
     * @return
     */
    @GetMapping("/teacherOral")
    public Object teacherOral(@RequestParam String mobile) {
        TeacherQueryDTO teacherQueryDTO = openApiQueryService.getTeacherByMobile(mobile);
        if (teacherQueryDTO == null || !teacherQueryDTO.getService().contains(SchoolServicePattern.YOUDAO_ORAL.getValue())) {
            return Result.failure("未能找到教师信息");
        } else {
            teacherQueryDTO.setClassList(teacherQueryDTO.getClassList().stream()
                    .filter(organizationDTO -> organizationDTO.getType() == OrganizationType.TEACHING.getValue().byteValue()
                            && organizationDTO.getSubject() == SubjectType.YOUDAO_ORAL.getValue())
                    .collect(Collectors.toList()));
            return Result.success(teacherQueryDTO);
        }
    }

    /**
     * 通过id匹配班级信息
     * @param id
     * @return
     */
    @GetMapping("/organization")
    public Object organization(@RequestParam int id) {
        OrganizationQueryDTO organizationQueryDTO = openApiQueryService.getOrganizationDetailById(id);
        if (organizationQueryDTO == null) {
            return Result.failure("未能找到班级信息");
        } else {
            return Result.success(organizationQueryDTO);
        }
    }

    /**
     * 批量查询班级信息
     * @param ids
     * @return
     */
    @GetMapping("/organization/batch")
    public Object organizationBatch(@RequestParam int[] ids) {
        List<OrganizationQueryDTO> organizationQueryDTOList = openApiQueryService.getOrganizationDetailByIds(ids);
        if (organizationQueryDTOList.isEmpty()) {
            return Result.failure("未能找到班级信息");
        } else {
            return Result.success(organizationQueryDTOList);
        }
    }
}