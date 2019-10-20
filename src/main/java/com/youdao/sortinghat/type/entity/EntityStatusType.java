package com.youdao.sortinghat.type.entity;

/**
 * 数据库实体状态
 *
 * @author suchen
 */
public enum EntityStatusType {

    UNKNOWN(-99, "未知状态"),

    OVERDUE(-2, "已过期"),

    DEACTIVATE(-1, "已停用"),

    NEW(0, "未激活"),

    ACTIVATE(1, "已启用");

    private Integer value;

    private String desc;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    EntityStatusType() {
    }

    EntityStatusType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static EntityStatusType get(Integer value) {
        for (EntityStatusType entityStatusType : values()) {
            if (entityStatusType.getValue().equals(value)) {
                return entityStatusType;
            }
        }
        return UNKNOWN;
    }
}
