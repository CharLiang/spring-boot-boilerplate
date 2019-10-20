/**
 * @(#)Ret.java, 2018-04-02.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.common;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Ret
 *
 * @author 
 *
 */
public class Ret extends HashMap {
    private static final String STATUS_OK = "isOk";
    private static final String STATUS_NOTOK = "isNotOK";

    public Ret() {
    }

    public static Ret by(Object key, Object value) {
        return (new Ret()).set(key, value);
    }

    public static Ret create(Object key, Object value) {
        return (new Ret()).set(key, value);
    }

    public static Ret create() {
        return new Ret();
    }

    public static Ret ok() {
        return (new Ret()).setOk();
    }

    public static Ret ok(Object key, Object value) {
        return ok().set(key, value);
    }

    public static Ret notok() {
        return new Ret().setNOTOK();
    }

    public static Ret notok(Object key, Object value) {
        return notok().set(key, value);
    }

    public Ret setOk() {
        super.put("isOk", Boolean.TRUE);
        super.put("isNotOK", Boolean.FALSE);
        return this;
    }

    public Ret setNOTOK() {
        super.put("isOk", Boolean.FALSE);
        super.put("isNotOK", Boolean.TRUE);
        return this;
    }

    public boolean isOk() {
        Boolean isOk = (Boolean)this.get("isOk");
        return isOk != null && isOk.booleanValue();
    }

    public boolean isNotOK() {
        Boolean isNotOK = (Boolean)this.get("isNotOK");
        return isNotOK != null && isNotOK.booleanValue();
    }

    public Ret set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public Ret set(Map map) {
        super.putAll(map);
        return this;
    }

    public Ret set(Ret ret) {
        super.putAll(ret);
        return this;
    }

    public Ret delete(Object key) {
        super.remove(key);
        return this;
    }

    public <T> T getAs(Object key) {
        return (T) this.get(key);
    }

    public String getStr(Object key) {
        return (String)this.get(key);
    }

    public Integer getInt(Object key) {
        return (Integer)this.get(key);
    }

    public Long getLong(Object key) {
        return (Long)this.get(key);
    }

    public Boolean getBoolean(Object key) {
        return (Boolean)this.get(key);
    }

    public boolean notNull(Object key) {
        return this.get(key) != null;
    }

    public boolean isNull(Object key) {
        return this.get(key) == null;
    }

    public boolean isTrue(Object key) {
        Object value = this.get(key);
        return value instanceof Boolean && ((Boolean)value).booleanValue();
    }

    public boolean isFalse(Object key) {
        Object value = this.get(key);
        return value instanceof Boolean && !((Boolean)value).booleanValue();
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public boolean equals(Object ret) {
        return ret instanceof Ret && super.equals(ret);
    }
}