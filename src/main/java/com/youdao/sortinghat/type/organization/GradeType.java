/**
 * @(#)GradeType.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.type.organization;

/**
 * GradeType
 *
 * @author 
 *
 */
public enum GradeType {

    UNKNOWN(0, "未知类别"),

    ONE(1, "一年级"),

    TWO(2, "二年级"),

    THREE(3, "三年级"),

    FOUR(4, "四年级"),

    FIVE(5, "五年级"),

    SIX(6, "六年级"),

    SEVEN(7, "七年级"),

    EIGHT(8, "八年级"),

    NINE(9, "九年级"),

    TEN(10, "高中一年级"),

    ELEVEN(11, "高中二年级"),

    TWELVE(12, "高中三年级"),

    FRESHMAN(13, "大学一年级"),

    SOPHOMORE(14, "大学二年级"),

    JUNIOR(15, "大学三年级"),

    SENIOR(16, "大学四年级");

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

    GradeType() {
    }

    GradeType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static GradeType get(Integer value) {
        for (GradeType gradeType : values()) {
            if (gradeType.getValue().equals(value)) {
                return gradeType;
            }
        }
        return UNKNOWN;
    }
}