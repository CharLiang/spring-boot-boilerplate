/**
 * @(#)HttpUtil.java, 2018-03-29.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpUtil
 *
 * @author 
 *
 */
@Component
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static RestTemplate restTemplate;

    @Autowired
    public HttpUtil(RestTemplate restTemplate) {
        HttpUtil.restTemplate = restTemplate;
    }

    public static Map xml(String url, Map param, List<String> cookies, HttpMethod httpMethod) {
        Map result = null;
        try {
            String body = body(url, XMLUtil.map2Xmlstring(param), cookies, MediaType.APPLICATION_XML, httpMethod);
            if (body != null) {
                result = XMLUtil.xmlString2Map(body);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String message = "xml " + httpMethod.toString() + " " + url + " "
                    + (param == null? "": param.toString()) + " cookies " + (cookies == null? "": cookies.toString()) + " error ";
            logger.error(message, e.getCause());
        }
        return result;
    }

    public static JSONObject json(String url, Map<String, Object> param, List<String> cookies, HttpMethod httpMethod) {
        JSONObject result = null;
        try {
            String body = body(url, JSON.toJSONString(param), cookies, MediaType.APPLICATION_JSON_UTF8, httpMethod);
            if (body != null) {
                result = JSON.parseObject(body);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String message = "json " + httpMethod.toString() + " " + url + " "
                    + (param == null? "": param.toString()) + " cookies " + (cookies == null? "": cookies.toString()) + " error ";
            logger.error(message, e.getCause());
        }
        return result;
    }

    public static String get(String url) {
        return get(url, new ArrayList<>());
    }

    public static String get(String url, List<String> cookies) {
        return body(url, new HashMap<>(), cookies, null, HttpMethod.GET);
    }

    public static String put(String url, MultiValueMap param) {
        return put(url, param, new ArrayList<>());
    }

    public static String put(String url, MultiValueMap param, List<String> cookies) {
        return body(url, param, cookies, null, HttpMethod.PUT);
    }

    public static String post(String url, MultiValueMap param) {
        return post(url, param, new ArrayList<>());
    }

    public static String post(String url, MultiValueMap param, List<String> cookies) {
        return body(url, param, cookies, null, HttpMethod.POST);
    }

    public static String body(String url, Object param, List<String> cookies,
                              MediaType mediaType, HttpMethod httpMethod) {
        String result = null;
        ResponseEntity<String> responseEntity = response(url, param, cookies, mediaType, httpMethod);
        if (responseEntity != null) {
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                result = responseEntity.getBody();
            } else {
                logger.warn("body " + httpMethod.toString() + " " + url + " " +
                        (param == null? "": param.toString()) + " cookies " + (cookies == null? "": cookies.toString()) +
                        " status " + responseEntity.getStatusCodeValue());
            }
        }
        return result;
    }

    public static ResponseEntity<String> response(String url, Object param, List<String> cookies,
                                                  MediaType mediaType, HttpMethod httpMethod) {
        ResponseEntity<String> result = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.put(HttpHeaders.COOKIE, cookies);
            if (mediaType != null) {
                headers.setContentType(mediaType);
            }
            HttpEntity entity = new HttpEntity<>(param, headers);
            result = http(url, httpMethod, entity);
            logger.info("[HttpUtil] url:" + url + " httpMethod:" + httpMethod.toString() + " entity" + entity.toString() + " result:" + result.toString());
        } catch (Exception e) {
            String message = httpMethod.toString() + " " + url + " "
                    + (param == null? "": param.toString()) + " cookies " + (cookies == null? "": cookies.toString()) + " error ";
            logger.error(message, e);
        }
        return result;
    }

    public static ResponseEntity<String> http(String url, HttpMethod httpMethod, HttpEntity entity) {
        logger.info("[HttpUtil] url:" + url + " httpMethod:" + httpMethod.toString() + " entity" + entity.toString());
        return restTemplate.exchange(url, httpMethod, entity, String.class);
    }

}