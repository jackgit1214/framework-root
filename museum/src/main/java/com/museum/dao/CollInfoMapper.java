package com.museum.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.museum.model.CollInfo;

public interface CollInfoMapper extends IDataMapperByPage<CollInfo>,
		IDataMapperCRUD<CollInfo> {

	/**
	 * 根据条件统计征集数量
	 * 
	 * @param type
	 * @param year
	 * @return
	 */
	@MapKey("name")
	public List<Map<String, Integer>> getCollNum1(@Param("type") int type,
			@Param("condition") int condition);

	@MapKey("name")
	public Map<String, Map<String, Integer>> getCollNum(
			@Param("type") int type, @Param("condition") int condition);

	@MapKey("name")
	public Map<String, Map<String, Integer>> culTypeStatistic(
			@Param("year") int year);

}