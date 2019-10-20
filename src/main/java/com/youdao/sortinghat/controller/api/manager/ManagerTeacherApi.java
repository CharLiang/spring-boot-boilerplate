/**
 * @(#)ManagerTeacherApi.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api.manager;

import com.youdao.sortinghat.annotation.LoginRequired;
import com.youdao.sortinghat.annotation.ManagerRequired;
import com.youdao.sortinghat.controller.api.base.BaseApi;
import com.youdao.sortinghat.data.common.SqlException;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ManagerTeacherApi
 *
 * @author 
 *
 */
@ManagerRequired
@LoginRequired
@RestController
@RequestMapping("/api/manager/teacher")
public class ManagerTeacherApi extends BaseApi {

    private static Logger logger = LoggerFactory.getLogger(ManagerTeacherApi.class);

    @Autowired
    private TeacherService teacherService;

    /**
     * 新增教师
     * @param name
     * @param no
     * @param mobile
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestParam String name,
                      @RequestParam(required = false) String no,
                      @RequestParam String mobile,
                      HttpServletRequest request) {
        int id = -1;
        try {
            id = teacherService.add(getManagerSchoolId(request), name, no, mobile);
        } catch (SqlException e) {
            logger.warn("save teacher fail name:" + name + " no:" + no + " mobile:" + mobile);
        }
        if (id != -1) {
            return Result.success("新增教师成功").set("id", id);
        } else {
            return Result.failure("新增教师失败，请检查该教师信息是否存在重复");
        }
    }

    /**
     * 批量增加教师
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/addByExcel")
    public Object addByExcel(@RequestParam MultipartFile file,
                             HttpServletRequest request) {
        boolean result = false;
        String message = "";
        try {
            result = teacherService.addByExcel(getManagerSchoolId(request), file);
        } catch (SqlException e) {
            logger.warn("addByExcel fail " + e.getMessage());
            message = e.getMessage();
        }
        if (result) {
            return Result.success("批量上传教师成功");
        } else {
            return Result.failure(message);
        }
    }

    /**
     * 下载样例表格
     * @param response
     */
    @GetMapping("/excelExample")
    public void excelExample(HttpServletResponse response) throws IOException {
        teacherService.downloadTeacherExcelExample(response);
    }

    /**
     * 修改教师
     * @param id
     * @param name
     * @param no
     * @param mobile
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public Object edit(@RequestParam int id,
                       @RequestParam String name,
                       @RequestParam(required = false) String no,
                       @RequestParam String mobile,
                       HttpServletRequest request) {
        boolean result = false;
        try {
            result = teacherService.edit(getManagerSchoolId(request), getUserMobile(request), id, name, no, mobile);
        } catch (SqlException e) {
            logger.warn("save teacher fail name:" + name + " no:" + no + " mobile:" + mobile);
        }
        if (result) {
            return Result.success("修改教师成功");
        } else {
            return Result.failure("修改教师失败，请检查该教师信息是否正确");
        }
    }

    /**
     * 修改教师状态
     * @param id
     * @param status
     * @param request
     * @return
     */
    @PostMapping("/status")
    public Object status(@RequestParam int id,
                         @RequestParam int status,
                         HttpServletRequest request) {
        if (teacherService.status(getManagerSchoolId(request), id, status)) {
            return Result.success("修改状态成功");
        } else {
            return Result.failure("修改状态失败，请检查学校是否存在，或者新状态是否合法");
        }
    }

    /**
     * 获取教师列表
     * @return
     */
    @GetMapping("/list")
    public Object list(HttpServletRequest request) {
        return Result.success(teacherService.list(getManagerSchoolId(request)));
    }

    /**
     * 查询教师信息
     * @param organizationId
     * @param key
     * @param request
     * @return
     */
    @GetMapping("/query")
    public Object query(@RequestParam(defaultValue = "-1") int organizationId,
                        @RequestParam(defaultValue = "") String key,
                        HttpServletRequest request) {
        return Result.success(teacherService.queryLike(getManagerSchoolId(request), organizationId, key));
    }
}