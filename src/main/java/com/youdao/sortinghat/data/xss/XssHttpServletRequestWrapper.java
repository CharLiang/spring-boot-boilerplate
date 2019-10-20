/**
 * @(#)XssHttpServletRequestWrapper.java, 2018-09-06.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.xss;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * XssHttpServletRequestWrapper
 *
 * @author 
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 描述 : 构造函数
     *
     * @param request 请求对象
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value != null) {
            value = HtmlUtils.htmlEscape(value);
        }
        return value;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value != null) {
            value = HtmlUtils.htmlEscape(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = HtmlUtils.htmlEscape(values[i]);
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

}