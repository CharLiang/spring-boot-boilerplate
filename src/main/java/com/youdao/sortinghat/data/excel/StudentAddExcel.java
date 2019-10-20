/**
 * @(#)StudentAddExcel.java, 2019-03-27.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * StudentAddExcel
 *
 * @author 
 *
 */
public class StudentAddExcel {

    @Excel(name = "姓名")
    private String name = "";

    @Excel(name = "学号")
    private String no = "";

    @Excel(name = "手机号")
    private String mobile = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public StudentAddExcel() {
    }

    public StudentAddExcel(String name, String no, String mobile) {
        this.name = name;
        this.no = no;
        this.mobile = mobile;
    }
}