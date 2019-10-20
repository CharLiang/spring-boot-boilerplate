/**
 * @(#)Result.java, 2018-04-02.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youdao.sortinghat.data.common.Ret;
import com.youdao.sortinghat.type.request.ResultStatus;
import org.apache.commons.lang3.StringUtils;

/**
 * Result
 *
 * @author 
 *
 */
public class Result extends Ret {

    public Result() {
    }

    public static Result success() {
        return (Result) new Result().set("status", ResultStatus.SUCCESS.getValue())
                .set("message", "");
    }

    public static Result success(String message) {
        return (Result) new Result().set("status", ResultStatus.SUCCESS.getValue())
                .set("message", message);
    }

    public static Result success(Object data) {
        return (Result) new Result().set("status", ResultStatus.SUCCESS.getValue())
                .set("message", "")
                .set("data", data);
    }

    public static Result success(String message, Object data) {
        return (Result) new Result().set("status", ResultStatus.SUCCESS.getValue())
                .set("message", message)
                .set("data", data);
    }

    public static Result failure() {
        return (Result) new Result().set("status", ResultStatus.FAILURE.getValue())
                .set("message", "");
    }

    public static Result failure(String message) {
        return (Result) new Result().set("status", ResultStatus.FAILURE.getValue())
                .set("message", message);
    }

    public static Result failure(Object data) {
        return (Result) new Result().set("status", ResultStatus.FAILURE.getValue())
                .set("message", "")
                .set("data", data);
    }

    public static Result failure(String message, Object data) {
        return (Result) new Result().set("status", ResultStatus.FAILURE.getValue())
                .set("message", message)
                .set("data", data);
    }

    public static Result error() {
        return (Result) new Result().set("status", ResultStatus.ERROR.getValue())
                .set("message", "");
    }

    public static Result error(String message) {
        return (Result) new Result().set("status", ResultStatus.ERROR.getValue())
                .set("message", message);
    }

    public static Result error(Object data) {
        return (Result) new Result().set("status", ResultStatus.ERROR.getValue())
                .set("message", "")
                .set("data", data);
    }

    public static Result error(String message, Object data) {
        return (Result) new Result().set("status", ResultStatus.ERROR.getValue())
                .set("message", message)
                .set("data", data);
    }

    public static Result overdue() {
        return (Result) new Result().set("status", ResultStatus.OVERDUE.getValue())
                .set("message", "登录超时，请重新登录");
    }

    public boolean isSuccess() {
        return StringUtils.isNumeric(this.get("status").toString()) &&
                ResultStatus.SUCCESS.equals(ResultStatus.get(Integer.valueOf(this.get("status").toString())));
    }

    public boolean isFailure() {
        return StringUtils.isNumeric(this.get("status").toString()) &&
                ResultStatus.FAILURE.equals(ResultStatus.get(Integer.valueOf(this.get("status").toString())));
    }

    public boolean isOverdue() {
        return StringUtils.isNumeric(this.get("status").toString()) &&
                ResultStatus.OVERDUE.equals(ResultStatus.get(Integer.valueOf(this.get("status").toString())));
    }

    public String getMessage() {
        String message = (String) this.get("message");
        return message == null? "": message;
    }

    public JSONObject getJSONObject(String key) {
        return getAs(key);
    }

    public JSONArray getJSONArray(String key) {
        return getAs(key);
    }
}