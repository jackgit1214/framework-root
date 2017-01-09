/**
 * 
 */
package com.framework.jpa.service;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lilj
 *
 */
public class TestJsonNode {

	@Test
	public void test() throws Exception {
		TreeBean tb = new TreeBeanImpl();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode  jsonnode = objectMapper.readTree(objectMapper.writeValueAsString(tb));
		System.out.println(jsonnode.toString());
	}

	
	public interface TreeBean{
		
		@JsonProperty("name")
		public abstract String getNodeName();
		
		@JsonProperty("pId")
		public abstract String getPid();
		
		@JsonProperty("id")
		public abstract String getId();
		
	}
	
	public class TreeBeanImpl implements TreeBean{

		/* (non-Javadoc)
		 * @see test.framework.service.TestJsonNode.TreeBean#getNodeName()
		 */
		@Override
		public String getNodeName() {
			// TODO Auto-generated method stub
			return "name";
		}

		/* (non-Javadoc)
		 * @see test.framework.service.TestJsonNode.TreeBean#getPid()
		 */
		@Override
		public String getPid() {
			// TODO Auto-generated method stub
			return "pid123";
		}

		/* (non-Javadoc)
		 * @see test.framework.service.TestJsonNode.TreeBean#getId()
		 */
		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return "id123";
		}
		
	}
}
