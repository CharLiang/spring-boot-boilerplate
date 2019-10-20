/**
 * @(#)YoudaoOralSchoolService.java, 2019-03-12.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.service;

/**
 * YoudaoOralSchoolService
 *
 * @author 
 *
 */
public class YoudaoOralSchoolService extends ForeverSchoolService {

    private int max;

    private int num;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public YoudaoOralSchoolService() {
    }

    public YoudaoOralSchoolService(int max, int num) {
        this.max = max;
        this.num = num;
    }
}