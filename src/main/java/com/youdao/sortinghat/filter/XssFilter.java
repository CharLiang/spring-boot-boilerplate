/**
 * @(#)XssFilter.java, 2018-09-06.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.filter;

import com.youdao.sortinghat.data.xss.XssHttpServletRequestWrapper;
import outfox.account.conf.AccConst;
import outfox.account.data.user.UserInfoWritable;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XssFilter
 *
 * @author 
 *
 */
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest =
                new XssHttpServletRequestWrapper((HttpServletRequest) request);
        // fake user
        request.setAttribute(AccConst.USER_ID_ATTR, "fake_user_id");
        request.setAttribute(AccConst.ATTR_BIND_PHONEID, "fake_mobile");
        UserInfoWritable userInfoWritable = new UserInfoWritable();
        userInfoWritable.from = "urs-phone";
        userInfoWritable.userName = "fake_mobile";
        userInfoWritable.userId = "fake_user_id";
        request.setAttribute("DICT" + AccConst.ATTR_PART_USER_ID_WRITABLE, userInfoWritable);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {
    }

}