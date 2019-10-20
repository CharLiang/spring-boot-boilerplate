/**
 * @(#)InnerIp.java, 2019-03-13.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * InnerIp
 *
 * @author 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface InnerIp {
}