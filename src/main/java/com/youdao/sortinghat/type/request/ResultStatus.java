/**
 * @(#)ResponseStatus.java, 2018-03-23.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.type.request;

/**
 * 报文状态
 *
 * @author 
 *
 */
public enum ResultStatus {

    UNKNOWN(-99, "未知状态"),

    OVERDUE(-2, "登录超时"),

    ERROR(-1, "错误"),

    FAILURE(0, "失败"),

    SUCCESS(1, "成功");

    private Integer value;

    private String desc;

    ResultStatus() {
    }

    ResultStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static ResultStatus get(Integer value) {

        for (ResultStatus resultStatus : values()) {
            if (resultStatus.getValue().equals(value)) {
                return resultStatus;
            }
        }
        return UNKNOWN;
    }
}