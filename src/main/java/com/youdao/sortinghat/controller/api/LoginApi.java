/**
 * @(#)LoginApi.java, 2019-01-03.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api;

import com.youdao.sortinghat.controller.api.base.BaseApi;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.UrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Login
 *
 * @author 
 *
 */
@RestController
@RequestMapping("/api/login")
public class LoginApi extends BaseApi {

    @Autowired
    private UrsService ursService;

    /**
     * urstoken登录
     * @param id
     * @param token
     * @param response
     * @return
     */
    @GetMapping("/ursToken")
    public Object ursToken(@RequestParam String id,
                           @RequestParam String token,
                           HttpServletResponse response) {
        if (ursService.ursToken(response, token, id, "mobile")) {
            return Result.success("登陆成功");
        } else {
            return Result.failure("登录失败");
        }
    }

    /**
     * 检查登录状态
     *
     * @param request
     * @return
     */
    @GetMapping("/checkStatus")
    public Object checkStatus(HttpServletRequest request) {
        String userId = getUserId(request);
        if (userId == null || userId.length() == 0) {
            return Result.overdue();
        }
        return Result.success();
    }
}