/**
 * 
 */
package com.framework.jpa.dao.queryutil;

/**
 * @author lilj
 *
 */
public class QueryProperty implements IQueryProperty {
	
	
	/**
	 * @param propertyName
	 * @param propertyValue
	 * @param operty
	 */
	public QueryProperty(String propertyName, Object propertyValue,
			OperType operty) {
		super();
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.operty = operty;
	}

	/**
	 * 
	 */
	public QueryProperty() {
		
	}

	/**
	 * @param propertyName
	 * @param propertyValue
	 * @param operty
	 */
	public QueryProperty(String propertyName, Object propertyValue,
			OperType operty,QueryPropertiesGroup queryGroup) {
		super();
		IQueryProperty queryProperty = new QueryProperty(propertyName, propertyValue, operty);
		queryGroup.addQueryProperty(queryProperty);
	}
	
	
	/**
	 * 属性名
	 */
	private String propertyName;

	/**
	 * 属性值
	 */
	private Object propertyValue;

	/**
	 * 属性操作
	 */
	private OperType operty;

	public String getExpression() {
		return this.getExpression(null);
	}

	@Override
	public String getExpression(String fieldAlias) {
		if (propertyName==null||"".equals(propertyName)) //属性名为空时，返回空
			return null;
		if (propertyValue==null)
			propertyValue="";
		if (operty==null)
			operty = OperType.equals; //为空时默认为等于 
		
		if (fieldAlias==null||"".equals(fieldAlias))
			return propertyName + " " + operty.getDesc() + " '" + propertyValue+"'";
		else
			return fieldAlias+"."+propertyName + " " + operty.getDesc() + " '" + propertyValue+"'";
	}
	
	
}