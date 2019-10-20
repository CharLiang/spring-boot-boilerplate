/**
 * @(#)NeedAppLogin.java, 2018-04-10.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.annotation;

import java.lang.annotation.*;

/**
 * NoLog
 *
 * @author 
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface NoLog {
}