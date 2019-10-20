/**
 * @(#)ManagerOrganizationApi.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api.manager;

import com.youdao.sortinghat.annotation.LoginRequired;
import com.youdao.sortinghat.annotation.ManagerRequired;
import com.youdao.sortinghat.controller.api.base.BaseApi;
import com.youdao.sortinghat.data.common.SqlException;
import com.youdao.sortinghat.data.dto.OrganizationDetailDTO;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.OrganizationService;
import com.youdao.sortinghat.service.ServiceService;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ManagerOrganizationApi
 *
 * @author 
 *
 */
@ManagerRequired
@LoginRequired
@RestController
@RequestMapping("/api/manager/organization")
public class ManagerOrganizationApi extends BaseApi {

    private static Logger logger = LoggerFactory.getLogger(ManagerOrganizationApi.class);

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ServiceService serviceService;

    /**
     * 新增班级
     * @param type
     * @param grade
     * @param subject
     * @param termId
     * @param name
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestParam int type,
                      @RequestParam int grade,
                      @RequestParam int subject,
                      @RequestParam int termId,
                      @RequestParam String name,
                      HttpServletRequest request) {
        int id = organizationService.add(getManagerSchoolId(request), type, grade, subject, termId, name);
        if (id != -1) {
            return Result.success("新增班级成功").set("id", id);
        } else {
            return Result.failure("新增班级失败");
        }
    }

    /**
     * 修改班级
     * @param id
     * @param type
     * @param grade
     * @param subject
     * @param termId
     * @param name
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public Object edit(@RequestParam int id,
                       @RequestParam int type,
                       @RequestParam int grade,
                       @RequestParam int subject,
                       @RequestParam int termId,
                       @RequestParam String name,
                       HttpServletRequest request) {
        if (organizationService.edit(getManagerSchoolId(request), id, type, grade, subject, termId, name)) {
            return Result.success("修改班级成功");
        } else {
            return Result.failure("修改班级失败");
        }
    }

    /**
     * 修改班级状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status")
    public Object status(@RequestParam int id,
                         @RequestParam int status) {
        if (organizationService.status(id, status)) {
            return Result.success("修改状态成功");
        } else {
            return Result.failure("修改状态失败");
        }
    }

    /**
     * 获取班级列表
     * @return
     */
    @GetMapping("/list")
    public Object list(HttpServletRequest request) {
        return Result.success(organizationService.list(getManagerSchoolId(request)));
    }

    /**
     * 获取班级详细信息
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/detail")
    public Object detail(@RequestParam int id,
                         HttpServletRequest request) {
        OrganizationDetailDTO organizationDetailDTO = organizationService.detail(getManagerSchoolId(request), id);
        if (organizationDetailDTO == null) {
            return Result.failure("未能找到班级信息");
        } else {
            return Result.success(organizationDetailDTO);
        }
    }

    /**
     * 检查名字是否可用
     * @param name
     * @param request
     * @return
     */
    @GetMapping("/checkNameAvailable")
    public Object checkNameAvailable(@RequestParam String name,
                                     HttpServletRequest request) {
        boolean result = organizationService.checkNameAvailable(getManagerSchoolId(request), name);
        return Result.success().set("available", result);
    }

    /**
     * 显示口语余量
     * @param request
     * @return
     */
    @GetMapping("/oralNum")
    public Object oralNum(HttpServletRequest request) {
        Pair<Integer, Integer> pair = serviceService.getOralNum(getManagerSchoolId(request));
        if (pair == null) {
            return Result.failure("未找到口语兑换码数量");
        } else {
            return Result.success().set("num", pair.getKey()).set("max", pair.getValue());
        }
    }

    /**
     * 班级新增教师
     * @param id
     * @param teacherId
     * @param subject
     * @param request
     * @return
     */
    @PostMapping("/teacher/add")
    public Object addTeacher(@RequestParam int id,
                             @RequestParam int teacherId,
                             @RequestParam int subject,
                             HttpServletRequest request) {
        boolean result = false;
        try {
            result = organizationService.addTeacher(getManagerSchoolId(request), id, teacherId, subject);
        } catch (SqlException e) {
            logger.warn("add teacher fail classId:" + id + " teacherId:" + teacherId + " subject:" + subject);
        }
        if (result) {
            return Result.success("配置教师成功");
        } else {
            return Result.failure("配置教师失败");
        }
    }

    /**
     * 班级删除教师
     * @param teacherId
     * @param organizationId
     * @param subject
     * @param request
     * @return
     */
    @PostMapping("/teacher/del")
    public Object delTeacher(@RequestParam int teacherId,
                             @RequestParam int organizationId,
                             @RequestParam int subject,
                             HttpServletRequest request) {
        if (organizationService.delTeacher(getManagerSchoolId(request), teacherId, organizationId, subject)) {
            return Result.success("删除教师成功");
        } else {
            return Result.failure("删除教师失败");
        }
    }

    /**
     * 班级新增学生
     * @param id
     * @param name
     * @param no
     * @param mobile
     * @param request
     * @return
     */
    @PostMapping("/student/add")
    public Object addStudent(@RequestParam int id,
                             @RequestParam String name,
                             @RequestParam(defaultValue = "") String no,
                             @RequestParam(defaultValue = "")  String mobile,
                             HttpServletRequest request) {
        int studentId = -1;
        try {
            studentId = organizationService.addStudent(getManagerSchoolId(request), id, name, no, mobile);
        } catch (SqlException e) {
            logger.warn("save student fail classId:" + id + " name:" + name + " no:" + no + " mobile:" + mobile);
        }
        if (studentId != -1) {
            return Result.success("新增学生成功").set("id", studentId);
        } else {
            return Result.failure("新增学生失败");
        }
    }

    /**
     * 批量增加学生
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/student/addByExcel")
    public Object addStudentByExcel(@RequestParam int id,
                                    @RequestParam MultipartFile file,
                                    HttpServletRequest request) {
        boolean result = false;
        String message = "";
        try {
            result = organizationService.addStudentByExcel(getManagerSchoolId(request), id, file);
        } catch (SqlException e) {
            logger.warn("addByExcel fail " + e.getMessage());
            message = e.getMessage();
        }
        if (result) {
            return Result.success("批量新增学生成功");
        } else {
            return Result.failure(message);
        }
    }

    /**
     * 下载样例表格
     * @param response
     */
    @GetMapping("/student/excelExample")
    public void excelExample(HttpServletResponse response) throws IOException {
        organizationService.downloadStudentExcelExample(response);
    }

    /**
     * 班级删除学生
     * @param studentId
     * @param organizationId
     * @param request
     * @return
     */
    @PostMapping("/student/del")
    public Object delStudent(@RequestParam int studentId,
                             @RequestParam int organizationId,
                             HttpServletRequest request) {
        boolean result = false;
        try {
            result = organizationService.delStudent(getManagerSchoolId(request), organizationId, studentId);
        } catch (SqlException e) {
            logger.warn("delete student fail classId:" + organizationId + " studentId:" + studentId);
        }
        if (result) {
            return Result.success("删除学生成功");
        } else {
            return Result.failure("删除学生失败");
        }
    }

    /**
     * 教师列表上方班级搜索条
     * @param type
     * @param grade
     * @param subject
     * @param request
     * @return
     */
    @GetMapping("/queryForTeacher")
    public Object queryForTeacher(@RequestParam(defaultValue = "-1") int type,
                                  @RequestParam(defaultValue = "-1") int grade,
                                  @RequestParam(defaultValue = "-1") int subject,
                                  HttpServletRequest request) {
        return Result.success(organizationService.queryByAdminForTeacher(getManagerSchoolId(request), type, grade, subject));
    }

    /**
     * 搜索班级信息
     * @param type
     * @param grade
     * @param subject
     * @param request
     * @return
     */
    @GetMapping("/query")
    public Object query(@RequestParam(defaultValue = "-1") int type,
                        @RequestParam(defaultValue = "-1") int grade,
                        @RequestParam(defaultValue = "-1") int subject,
                        @RequestParam(defaultValue = "") String name,
                        HttpServletRequest request) {
        return Result.success(organizationService.query(getManagerSchoolId(request), type, grade, subject, name));
    }
}