package com.youdao.sortinghat.filter;

import com.alibaba.fastjson.JSONObject;
import com.youdao.sortinghat.data.common.Constants;
import com.youdao.sortinghat.util.AESUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 获取教师端cookie
                if (!cookie.getName().equals("HOMEWORK_T_LOGIN")) {
                    continue;
                }
                String decryptKey = "h#hh8_drj6HzjPC(k4Eadt1Wh2bkRU";
                String cookieValue = cookie.getValue().replaceAll("\"","");
                String userId = cookieValueDecrypt(decryptKey, cookieValue);
                if (userId != null && userId.length() != 0) {
                    request.setAttribute(Constants.USER_ID_ATTR, userId);
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * cookie解密
     *
     * @param key
     * @param cookieValue
     * @return
     */
    private String cookieValueDecrypt(String key, String cookieValue) {
        AESUtils aesUtil = new AESUtils(key, AESUtils.ENCODE_BITNUM);
        try {
            String decryptedString = aesUtil.decrypt(cookieValue);
            JSONObject jsonValue = JSONObject.parseObject(decryptedString);
            return jsonValue.getString("accountId");
        } catch (RuntimeException e) {
            return null;
        }
    }
}
