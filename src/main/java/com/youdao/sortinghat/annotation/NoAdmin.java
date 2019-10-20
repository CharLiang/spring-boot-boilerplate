/**
 * @(#)NoAdmin.java, 2018-11-14.
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
 * NoAdmin
 *
 * @author 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface NoAdmin {
}