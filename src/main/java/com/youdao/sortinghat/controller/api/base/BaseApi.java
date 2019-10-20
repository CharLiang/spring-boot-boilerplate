/**
 * @(#)ApiBaseController.java, 2018-11-06.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.api.base;

import com.google.common.collect.Maps;
import com.youdao.sortinghat.controller.base.BaseController;
import com.youdao.sortinghat.data.request.Result;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * BaseApi
 *
 * @author 
 *
 */
public class BaseApi extends BaseController {

    protected String checkParam(Map param, String... keys) {
        String loseKeys = "";
        for (String key : keys) {
            if (!param.containsKey(key)) {
                loseKeys = loseKeys + key + " ";
            }
        }
        return loseKeys;
    }

    protected String checkParam(HttpServletRequest request, String... keys) {
        return checkParam(request.getParameterMap(), keys);
    }

    protected Result returnValidResult(BindingResult bindingResult) {
        Result result = Result.success();
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMessageMap = Maps.newHashMap();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            result = Result.failure("参数异常", errorMessageMap);
        }
        return result;
    }

}