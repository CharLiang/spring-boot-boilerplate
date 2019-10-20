/**
 * @(#)IndexController.java, 2018-11-08.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller;

import com.youdao.sortinghat.annotation.NoLog;
import com.youdao.sortinghat.config.CommonConfig;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.util.HttpUtil;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 *
 * @author 
 *
 */
@NoLog
@RestController
public class IndexController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = "/")
    public Object index() {
        return Result.success();
    }

    @RequestMapping(value = "/health")
    public Object health() {
        String info = HttpUtil.get("http://localhost:" + CommonConfig.getPortManager() + "/health");
        return info;
    }

    @RequestMapping(value = PATH)
    public Object error() {
        return Result.error("调用失败");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}