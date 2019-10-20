/**
 * @(#)ManagerStudentApi.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api.manager;

import com.youdao.sortinghat.annotation.LoginRequired;
import com.youdao.sortinghat.annotation.ManagerRequired;
import com.youdao.sortinghat.controller.api.base.BaseApi;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * ManagerStudentApi
 *
 * @author 
 *
 */
@ManagerRequired
@LoginRequired
@RestController
@RequestMapping("/api/manager/student")
public class ManagerStudentApi extends BaseApi {

    @Autowired
    private StudentService studentService;

    /**
     * 修改学生信息
     * @param id
     * @param name
     * @param mobile
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public Object edit(@RequestParam int id,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String mobile,
                       HttpServletRequest request) {
        if (studentService.edit(getManagerSchoolId(request), id, name, mobile)) {
            return Result.success("修改学生成功");
        } else {
            return Result.failure("修改学生失败，请检查该学生信息是否正确");
        }
    }

    @PostMapping("/status")
    public Object status(@RequestParam int id,
                         @RequestParam int status,
                       HttpServletRequest request) {
        if (studentService.status(getManagerSchoolId(request), id, status)) {
            return Result.success("修改学生状态成功");
        } else {
            return Result.failure("修改学生状态失败");
        }
    }

    /**
     * 获取学生列表
     * @return
     */
    @GetMapping("/list")
    public Object list(HttpServletRequest request) {
        return Result.success(studentService.list(getManagerSchoolId(request)));
    }

    /**
     * 查询
     * @param organizationId
     * @param key
     * @param request
     * @return
     */
    @GetMapping("/query")
    public Object query(@RequestParam(defaultValue = "-1") int organizationId,
                        @RequestParam(defaultValue = "") String key,
                        HttpServletRequest request) {
        return Result.success(studentService.queryLike(getManagerSchoolId(request), organizationId, key));
    }
}