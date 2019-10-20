/**
 * @(#)BaseController.java, 2018-11-06.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.controller.base;

import com.google.common.collect.Maps;
import com.youdao.sortinghat.data.common.Constants;
import com.youdao.sortinghat.util.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import outfox.account.conf.AccConst;
import outfox.account.data.user.UserInfoWritable;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * BaseController
 *
 * @author 
 *
 */
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    public Map<String, Object> getWWWMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    public Map<String, Object> getXmlMap(HttpServletRequest request) {
        if (request.getHeader("content-type") == null ||
                !request.getHeader("content-type").contains("xml")) {
            return Maps.newHashMap();
        }
        String string = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            string = sb.toString();
        } catch (IOException e) {
            logger.error("[BaseController] getXmlMap error", e);
            e.printStackTrace();
        }
        return XMLUtil.xmlString2Map(string);
    }

    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        return ip == null ? request.getRemoteAddr() : ip;
    }

    public String getUserId(HttpServletRequest request) {
        return (String) request.getAttribute(AccConst.USER_ID_ATTR);
    }

    public String getUserMobile(HttpServletRequest request) {
        String mobile = null;
        UserInfoWritable userInfoWritable = (UserInfoWritable) request.getAttribute("DICT" + AccConst.ATTR_PART_USER_ID_WRITABLE);
        if (userInfoWritable != null) {
            if (userInfoWritable.from.startsWith("urs-phone")) {
                mobile = userInfoWritable.userName;
            }
        }
        return mobile;
    }

    public Integer getManagerSchoolId(HttpServletRequest request) {
        return (Integer) request.getAttribute(Constants.MANAGER_SCHOOL_ID);
    }

}