/**
 * 
 */
package com.framework.jpa.dao.queryutil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lilj
 * 
 */
public class QueryPropertiesGroup implements IQueryProperty {

	private LinkType groupType;
	
	private List<IQueryProperty> queryProperties;

	/**
	 * @param groupType
	 * @param queryProperties
	 */
	public QueryPropertiesGroup(LinkType groupType,
			List<IQueryProperty> queryProperties) {
		super();
		this.groupType = groupType;
		this.queryProperties = queryProperties;
	}

	public QueryPropertiesGroup(LinkType groupType) {
		super();
		this.groupType = groupType;
	}
	
	
	public void addQueryProperty(IQueryProperty queryProperty){
		if (this.queryProperties == null)
			this.queryProperties = new ArrayList<IQueryProperty>();
		this.queryProperties.add(queryProperty);
	}
	
	public String getExpression(String fieldAlias){
		
		if (queryProperties==null || queryProperties.size() <=0)
			return "";
		String condStr="";
		for (IQueryProperty queryProperty : queryProperties){
			if (queryProperty.equals(this)){ //避免stack溢出
				continue;
			}
			condStr = condStr + queryProperty.getExpression(fieldAlias) +" "+ groupType.getDesc()+" ";
		}
		condStr = condStr.substring(0,condStr.length() - groupType.getDesc().length() - 1);
		condStr = "("+condStr+")"; //生成组合条件
		return condStr;
	}
	
	public String getExpression(){
		return this.getExpression(null);
	}
}
