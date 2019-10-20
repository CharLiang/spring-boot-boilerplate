/**
 * @(#)LogInterceptor.java, 2018-04-08.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.interceptor;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * LogInterceptor
 *
 * @author 
 *
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().contains("error")) {
            printRequestLogWithParams(request);
        }
        return true;
    }

    private void printRequestLogWithParams(HttpServletRequest request) {
        StringBuilder content = new StringBuilder();
        content.append(request.getRequestURI());
        String method = request.getMethod();
        if ("GET".equals(method)) {
            appendGetParams(content, request);
        } else {
            appendPostParams(content, request);
        }
        String ip = request.getHeader("x-forwarded-for");
        content.append(" ip=" + (ip == null ? request.getRemoteAddr() : ip));
        logger.info(method + " " + content.toString());
    }

    private void appendGetParams(StringBuilder content, HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString != null) {
            content.append("?");
            content.append(queryString);
        }
    }

    private void appendPostParams(StringBuilder content, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(request.getContentType()) ||
                !request.getContentType().contains("multipart/form-data")) {
            Map parameterMap = request.getParameterMap();
            if (parameterMap != null && parameterMap.size() != 0) {
                content.append("?");
                for (Object key : parameterMap.keySet()) {
                    content.append(key);
                    content.append("=");
                    content.append(request.getParameterValues(key.toString())[0]);
                    content.append("&");
                }
                content.deleteCharAt(content.length() - 1);
            }
        }

    }
}