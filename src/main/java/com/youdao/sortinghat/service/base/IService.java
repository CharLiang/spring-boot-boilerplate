/**
 * @(#)IService.java, 2018-03-05.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.service.base;

import java.util.List;

/**
 * IService
 *
 * @author 
 *
 */
public interface IService<T> {

    /**
     * 查询全部结果
     * @return
     */
    List<T> selectAll();

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     * @param key
     * @return
     */
    T selectByKey(Object key);

    /**
     * 根据Example条件进行查询
     * 重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     * @param entity
     * @return
     */
    int save(T entity);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param entity
     * @return
     */
    int saveNotNull(T entity);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     *根 据主键更新属性不为null的值
     * @param entity
     * @return
     */
    int updateNotNull(T entity);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param key
     * @return
     */
    int deleteByKey(Object key);

    //TODO 其他...
}