/**
 * 
 */
package com.framework.jpa.dao.order;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.framework.jpa.dao.queryutil.IQueryProperty;
import com.framework.jpa.dao.queryutil.IQueryProperty.OrderType;

/**
 * @author lilj
 *
 */
public class OrderProperty implements IQueryProperty {

	private LinkedHashMap<String,OrderType> orderField;

	
	/**
	 * @param orderField
	 */
	public OrderProperty(LinkedHashMap<String,OrderType> orderField) {
		super();
		this.orderField = orderField;
	}

	@Override
	public String getExpression() {
		return this.getExpression("");
	}

	/**
	 * 针对排序，fieldAlias不起作用
	 */
	@Override
	public String getExpression(String fieldAlias) {
		if (orderField==null||"".equals(orderField)) //属性名为空时，返回空
			return null;
		String orderExpression=" ";
		Iterator<Entry<String, OrderType>> iterator = orderField.entrySet().iterator();;
		
		while (iterator.hasNext()){
			Entry<String, OrderType> entry = iterator.next();
			String fieldName = entry.getKey();
			String orderType = entry.getValue().getDesc();
			orderExpression = orderExpression+" "+fieldName+" "+orderType+" ,";
		}
		orderExpression = orderExpression.substring(0,orderExpression.length() - 2);
		return orderExpression; 
	}
	
}
