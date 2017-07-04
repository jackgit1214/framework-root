package com.framework.common.anaotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.system.common.SysConstant;

/**
 * 记录连接历史
 * 
 * @author: lilj
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LinkHistoryAnaotation {

	String linkLevel() default SysConstant.SECOUND_LINK_SIGN;

	String linkName();

	String linkValue();

}
