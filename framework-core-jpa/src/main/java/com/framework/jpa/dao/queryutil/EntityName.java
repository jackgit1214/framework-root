/**
 * 
 */
package com.framework.jpa.dao.queryutil;

/**
 * @author lilj
 * 
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Target( {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityName {
     
    Class<?> value();
 
    String name() default "";
}