/**
 * @(#)AdminServiceApi.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api.admin;

import com.youdao.sortinghat.annotation.AdminRequired;
import com.youdao.sortinghat.annotation.LoginRequired;
import com.youdao.sortinghat.controller.api.base.BaseApi;
import com.youdao.sortinghat.data.common.Ret;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * AdminServiceApi
 *
 * @author 
 *
 */
@AdminRequired
@LoginRequired
@RestController
@RequestMapping("/api/admin/service")
public class AdminServiceApi extends BaseApi {

    @Autowired
    private ServiceService serviceService;

    /**
     * 新增服务
     * @param schoolId
     * @param serviceType
     * @param content
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestParam int schoolId,
                      @RequestParam int serviceType,
                      @RequestParam(defaultValue = "{}") String content) {
        if (serviceService.add(schoolId, serviceType, content)) {
            return Result.success("新增服务成功");
        } else {
            return Result.failure("新增服务失败，请检查学校、服务数据是否有误，或者是否已经增加过该服务。");
        }
    }

    /**
     * 修改服务参数
     * @param id
     * @param content
     * @return
     */
    @PostMapping("/edit")
    public Object edit(@RequestParam int id,
                       @RequestParam(defaultValue = "{}") String content) {
        if (serviceService.edit(id, content)) {
            return Result.success("修改服务参数成功");
        } else {
            return Result.failure("修改服务参数失败，请检查数据是否正确。");
        }
    }

    /**
     * 修改服务状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status")
    public Object status(@RequestParam int id,
                         @RequestParam int status) {
        if (serviceService.status(id, status)) {
            return Result.success("修改服务状态成功");
        } else {
            return Result.failure("修改服务状态失败，请检查数据是否正确。");
        }
    }

    /**
     * 获取服务列表
     * @return
     */
    @GetMapping("/list")
    public Object list(@RequestParam int schoolId) {
        return Result.success(serviceService.list(schoolId));
    }

    /**
     * 新增教务
     * @param schoolId
     * @param name
     * @param no
     * @param mobile
     * @return
     */
    @PostMapping("/manager")
    public Object manager(@RequestParam int schoolId,
                          @RequestParam String name,
                          @RequestParam(required = false) String no,
                          @RequestParam String mobile) {
        Ret ret = serviceService.manager(schoolId, name, no, mobile);
        if (ret.isOk()) {
            return Result.success("新增教务成功");
        } else {
            return Result.failure(ret.getStr("message"));
        }
    }
}