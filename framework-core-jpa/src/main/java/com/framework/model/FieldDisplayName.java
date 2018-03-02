/************************* 鐗堟潈澹版槑 *********************************
 *                                                                  *
 *                     鐗堟潈鎵�湁锛氱櫨娲嬭蒋浠�                          *
 *          Copyright (c) 2010 by www.po-soft.com                *
 *                                                                  *
 ************************* 鍙樻洿璁板綍 *********************************
 *
 * 鍒涘缓鑰咃細slx   鍒涘缓鏃ユ湡锛�2010-7-1
 * 澶囨敞锛�
 * 
 * 淇敼鑰咃細       淇敼鏃ユ湡锛�
 * 澶囨敞锛�
 * 
 */    

package com.framework.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段显示名(中文名)注解
 * @author slx
 * @date 2010-7-1 上午08:56:37
 * @version 1.0
 */
@Target( {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDisplayName {

	String value();
}