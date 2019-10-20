/**
 * @(#)OrganizationType.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.type.organization;

/**
 * OrganizationType
 *
 * @author 
 *
 */
public enum OrganizationType {

    UNKNOWN(0, "未知类别"),

    ADMINISTRATIVE(1, "行政班"),

    TEACHING(2, "教学班");

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

    OrganizationType() {
    }

    OrganizationType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static OrganizationType get(Integer value) {
        for (OrganizationType organizationType : values()) {
            if (organizationType.getValue().equals(value)) {
                return organizationType;
            }
        }
        return UNKNOWN;
    }
}