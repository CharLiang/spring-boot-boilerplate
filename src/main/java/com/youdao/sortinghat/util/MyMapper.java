/**
 * @(#)MyMapper.java, 2018-03-05.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * MyMapper 通用Mapper
 *
 * @author 
 *
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}