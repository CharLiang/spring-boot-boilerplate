/**
 * @(#)SubjectType.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.type.organization;

/**
 * SubjectType
 *
 * @author 
 *
 */
public enum SubjectType {

    UNKNOWN(0, "未知类别"),

    ANYSUBJECT(1, "全科"),

    CHINESE(2, "语文"),

    MATHEMATICS(3, "数学"),

    ENGLISH(4, "英语"),

    PHYSICS(5, "物理"),

    CHEMISTRY(6, "化学"),

    BIOLOGY(7, "生物"),

    POLITICS(8, "政治"),

    HISTORY(9, "历史"),

    GEOGRAPHY(10, "地理"),

    YOUDAO_ORAL(11, "有道口语");

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

    SubjectType() {
    }

    SubjectType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SubjectType get(Integer value) {
        for (SubjectType subjectType : values()) {
            if (subjectType.getValue().equals(value)) {
                return subjectType;
            }
        }
        return UNKNOWN;
    }
}