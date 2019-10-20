/**
 * @(#)AdminSchoolApi.java, 2019-03-07.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api.admin;

import com.youdao.sortinghat.annotation.AdminRequired;
import com.youdao.sortinghat.annotation.LoginRequired;
import com.youdao.sortinghat.controller.api.base.BaseApi;
import com.youdao.sortinghat.data.common.SqlException;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * AdminSchoolApi
 *
 * @author 
 *
 */
@AdminRequired
@LoginRequired
@RestController
@RequestMapping("/api/admin/school")
public class AdminSchoolApi extends BaseApi {

    private static Logger logger = LoggerFactory.getLogger(AdminSchoolApi.class);

    @Autowired
    private SchoolService schoolService;

    /**
     * 新增学校
     * @param name
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestParam String name,
                      @RequestParam(defaultValue = "0") int province,
                      @RequestParam(defaultValue = "0") int city,
                      @RequestParam(defaultValue = "0") int district,
                      @RequestParam(defaultValue = "") String address) {
        int id = -1;
        try {
            id = schoolService.add(name, province, city, district, address);
        } catch (SqlException e) {
            logger.warn("save school fail name:" + name);
        }
        if (id != -1) {
            return Result.success("新增学校成功").set("id", id);
        } else {
            return Result.failure("新增学校失败，请检查学校名称是否有重复。");
        }
    }

    /**
     * 更新学校名称
     * @param name
     * @param id
     * @return
     */
    @PostMapping("/edit")
    public Object edit(@RequestParam String name,
                       @RequestParam int id) {
        if (schoolService.edit(id, name)) {
            return Result.success("修改名称成功");
        } else {
            return Result.failure("修改名称失败，请检查学校是否存在，或者学校名称是否有重复。");
        }
    }

    /**
     * 修改学校状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status")
    public Object status(@RequestParam int id,
                         @RequestParam int status) {
        if (schoolService.status(id, status)) {
            return Result.success("修改状态成功");
        } else {
            return Result.failure("修改状态失败，请检查学校是否存在，或者新状态是否合法。");
        }
    }

    /**
     * 获取学校列表
     * @return
     */
    @GetMapping("/list")
    public Object list() {
        return Result.success(schoolService.selectAll());
    }
}