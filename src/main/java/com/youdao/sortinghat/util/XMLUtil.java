/**
 * @(#)XMLUtil.java, 2018-04-04.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * XMLUtil
 *
 * @author 
 *
 */
public class XMLUtil {

    public static String map2Xmlstring(Map<String,Object> map){
        StringBuffer sb = new StringBuffer("");
        sb.append("<xml>");

        Set<String> set = map.keySet();
        for(Iterator<String> it = set.iterator(); it.hasNext();){
            String key = it.next();
            Object value = map.get(key);
            sb.append("<").append(key).append(">");
            sb.append(value);
            sb.append("</").append(key).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static Map<String,Object> xmlString2Map(String xmlStr){
        Map<String,Object> map = new TreeMap<String,Object>();
        Document doc;
        try {
            doc = DocumentHelper.parseText(xmlStr);
            Element el = doc.getRootElement();
            map = recGetXmlElementValue(el,map);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    @SuppressWarnings({ "unchecked" })
    private static Map<String, Object> recGetXmlElementValue(Element ele, Map<String, Object> map){
        List<Element> eleList = ele.elements();
        if (eleList.size() == 0) {
            map.put(ele.getName(), ele.getTextTrim());
            return map;
        } else {
            for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {
                Element innerEle = iter.next();
                recGetXmlElementValue(innerEle, map);
            }
            return map;
        }
    }
}