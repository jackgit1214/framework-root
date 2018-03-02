/**
 * 
 */
package com.framework.jpa.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;

public abstract class AbstractDaoUtil {

	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * 取得model 中是ID的方法名
	 * 
	 * @param entryObject
	 *            model对象
	 * @return
	 */
	protected Method getIdMethod(Object entityObject) {

		Method[] methods = entityObject.getClass().getDeclaredMethods();

		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			Annotation annotation = method.getAnnotation(Id.class);
			if (annotation != null)
				return method;
		}

		return null;
	}

	protected Object exeGetID(Object entityObject) {

		Method method = this.getIdMethod(entityObject);
		Object rtnobj = null;
		try {
			Object[] args = null;
			rtnobj = method.invoke(entityObject, args);
			return rtnobj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	protected  <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) throws Exception {
		List<T> returnList = new ArrayList<T>();
		Object[] co = list.get(0);
		Class[]	c2 = new Class[co.length];
		
		//确定构造方法
		for(int i = 0; i < co.length; i++){
			c2[i] = co[i].getClass();
		}
		for(Object[] o : list){
			Constructor<T> constructor = clazz.getConstructor(c2);
			returnList.add(constructor.newInstance(o));
		}
		return returnList;
	}	

	protected  <T> List<T> castEntity(List<Object[]> list, Class<T> clazz,LinkedHashMap<String,String> fields) throws Exception {
		
		if (fields==null)
			throw new Exception(" selected fields is not null");
		List<T> returnList = new ArrayList<T>();
		//Method[] methods = clazz.getDeclaredMethods();
		for(Object[] o : list){
			T entity = clazz.newInstance();
			Iterator<Entry<String, String>> iterator = fields.entrySet().iterator();
			int fieldOrder=0;
			while (iterator.hasNext()){
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
				String fieldname = entry.getValue();
				
		    	
		    	BeanWrapperImpl beanWrapper = new BeanWrapperImpl(false);
		    	
		    	beanWrapper.setWrappedInstance(entity);
		    	beanWrapper.setPropertyValue(fieldname,  o[fieldOrder]);
				//this.setObjectValue(fieldname, o[fieldOrder], clazz, entity);
				fieldOrder++;
			}
			returnList.add(entity);
		}
		return returnList;
	}		
	
	protected <T> void setObjectValue(String objectName,Object value,Class<T> clazz,T object){

		String methodName = "set"+ objectName.substring(0,1).toUpperCase()+objectName.substring(1);
		Method method = null;
		try {
			Field tmpfield = clazz.getDeclaredField(objectName);
			Object tmpvalue = value;
			
			//针对整型数据取进行转换
			if (tmpfield.getType().getName().equals("java.lang.Integer"))
				tmpvalue = Integer.parseInt((String)value);
			
			Object[] args = {tmpvalue};
			method = clazz.getDeclaredMethod(methodName,tmpfield.getType());
			method.invoke(object, args);
		} catch (Exception e1) {
			//e1.printStackTrace();
			log.error("no this setmethod:"+methodName+"/r/n"+e1.getMessage());
		}
		
	}	
	
	
	protected LinkedHashMap<String,String> getSelectField(String selectField){
		if (selectField==null||"".equals(selectField))
			return null;
		String[] fields = selectField.split(",");
		if (fields.length<=0)
			return null;
		LinkedHashMap<String,String> linkMap = new LinkedHashMap<String,String>();
		for (String field:fields){
			String fieldAlias = field;
			int asPos = field.indexOf(" as ");
			if (asPos > 0) //有as时以别名为主
				fieldAlias = field.substring(asPos+4);
			else{ //没有时看看是否有点操作符
				int pointPos = field.indexOf(".");
				if (pointPos > 0 )
					fieldAlias = field.substring(pointPos+1);
			}
			linkMap.put(field,fieldAlias.trim());
		}
		return linkMap;
	}
	
	/**
	 * Return the annotations associated with the specific method/constructor
	 * parameter.
	 */
	public Annotation[] getParameterAnnotations(Method method) {
		Annotation[] annotationArray = method.getAnnotations();
		return annotationArray;
	}
	
	
	
}
