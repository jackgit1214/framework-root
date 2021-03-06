package com.framework.common.anaotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented 
public @interface SystemLog {

	 String description()  default "";  

	 String moduleId() default "";
	 
	 String operId() default "";
	 
	 public enum LogAnnoDefi  {
		 
			DESC(1),MODULE(2),OPER(3);

			private LogAnnoDefi(int value) {
				
			}
	}
}
