/**
 * 
 */
package com.framework.jpa.dao.queryutil;

/**
 * @author lilj
 *
 */
public interface IQueryProperty {

	public static enum OperType {
		equals("="), great(">"), small("<"), like("like"), notlike("not like"), in(
				"in"), notin("not in");

		private String desc;

		OperType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return this.desc;
		}
	};

	public static enum LinkType {
		and("and"), or("or");
		private String desc;

		LinkType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return this.desc;
		}
	}
	
	public static enum OrderType {
		Asc("Asc"), Desc("Desc");

		private String desc;

		OrderType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return this.desc;
		}
	};	
	
	public abstract String getExpression();

	public abstract String getExpression(String fieldAlias);
	
	
}
