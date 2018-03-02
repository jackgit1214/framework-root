/**
 * 
 */
package com.framework.jpa.services;

import java.lang.reflect.Field;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.jpa.dao.ICommonDao;
import com.framework.jpa.dao.IQueryPageDao;
import com.framework.jpa.dao.queryutil.EntityName;

/**
 * @author lilj
 * 抽象services
 */
public abstract class AbstractServices {

	protected final Log log = LogFactory.getLog(this.getClass());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void init_method(){
		
		Field[] fields = this.getClass().getDeclaredFields();
		if (fields == null || fields.length <=0 )
			return ;
		for (Field field :fields){
			EntityName entityName = field.getAnnotation(EntityName.class);
			if ( entityName!=null && (field.getType() == ICommonDao.class || field.getType() == IQueryPageDao.class )){
				field.setAccessible(true);
				try {
					ICommonDao commonDao = (ICommonDao)field.get(this);
					String tmpname = "".equals(entityName.name())?entityName.value().getSimpleName():entityName.name();
					commonDao.setEntityName(entityName.value(),tmpname);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}
}
