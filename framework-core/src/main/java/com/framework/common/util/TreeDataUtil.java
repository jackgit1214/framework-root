/**
 * 
 */
package com.framework.common.util;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.common.model.ITreeData;

/**
 * @author lilj
 *
 */
public class TreeDataUtil{

	public static JsonNode ConvertToJsonData(ITreeData treeData) throws Exception{
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode  jsonnode = objectMapper.readTree(objectMapper.writeValueAsString(treeData));
		return jsonnode;
	}
	
	public static JsonNode ConvertToJsonData(List<?> treeDatas) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode  jsonnode = objectMapper.readTree(objectMapper.writeValueAsString(treeDatas));
		return jsonnode;
	}
	
	public static String ConvertToString(ITreeData treeData) throws Exception{
		return ConvertToJsonData(treeData).toString();
	}

	public static String ConvertToString(List<?> treeDatas) throws Exception{
		return ConvertToJsonData(treeDatas).toString();
	}	

	
}
