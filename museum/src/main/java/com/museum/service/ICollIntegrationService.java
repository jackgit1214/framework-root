package com.museum.service;

import java.util.List;
import java.util.Map;

import com.museum.model.CollClue;

public interface ICollIntegrationService {

	/**
	 * 根据状态取得征集线索数据
	 * 
	 * @param status
	 * @return
	 */
	public List<CollClue> getCollClueInfoByStatus(String status);

	/**
	 * 按统计征集数量
	 * 
	 * @return
	 */
	public List<Map<String, Integer>> getCollNumStatistic(String curdate,
			int type, List<String> xAxis);

	/**
	 * 按统计类别
	 * 
	 * @return
	 */
	public List<Map<String, Integer>> culTypeStatistic(String curdate,
			List<String> legend);

}
