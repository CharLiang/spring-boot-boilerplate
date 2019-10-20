/**
 * @(#)UrsService.java, 2019-01-03.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youdao.sortinghat.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * UrsService
 *
 * @author 
 *
 */
@Service
public class UrsService {

    private static final String ACCOUNTSERVER_LOGIN_URL = "http://dict.youdao.com/login/acc/login";

    private static Logger logger = LoggerFactory.getLogger(UrsService.class);

    public boolean ursToken(HttpServletResponse response, String token, String id, String app) {
        boolean result = false;
        String url = ACCOUNTSERVER_LOGIN_URL + "?product=DICT&tp=tpac&acc=urstoken&cf=3&app=" + app +
                "&access_token=" + token + "&opid=" + id;
        try {
            ResponseEntity<String> responseEntity = HttpUtil.response(url, Maps.newHashMap(), Lists.newArrayList(), MediaType.APPLICATION_JSON, HttpMethod.GET);
            if (responseEntity != null) {
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
                    if (!jsonObject.containsKey("ecode") && !jsonObject.containsKey("tpcode") &&
                            jsonObject.containsKey("userid") && responseEntity.getHeaders().containsKey("Set-Cookie")) {
                        List<String> setCookieList = responseEntity.getHeaders().get("Set-Cookie");
                        logger.info("[UrsService] ursToken succ token:" + token + " id:" + id + " app:" + app +
                                " userid:" + jsonObject.getString("userid") + " Set-Cookie:" + setCookieList);
                        for (String cookie : setCookieList) {
                            String[] strings = cookie.split(";")[0].split("=");
                            response.addCookie(new Cookie(strings[0], strings[1]));
                        }
                        result = true;
                    }
                } else {
                    logger.warn("[UrsService] ursToken fail token:" + token + " id:" + id + " app:" + app +
                            " code:" + responseEntity.getStatusCodeValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[UrsService] ursToken error token:" + token + " id:" + id + " app:" + app, e);
        }
        return result;
    }
}