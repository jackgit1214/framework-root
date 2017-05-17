package com.museum.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.mybatis.model.QueryModel;
import com.museum.model.CollClue;
import com.museum.service.CollClueService;
import com.museum.service.CollInfoService;
import com.museum.service.ICollIntegrationService;

@Service
public class CollIntegrationServiceImpl implements ICollIntegrationService {

	@Autowired
	public CollClueService collClueServiceImpl;

	@Autowired
	public CollInfoService collInfoServiceImpl;

	@Override
	public List<CollClue> getCollClueInfoByStatus(String status) {

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("status", status);
		List<CollClue> collClues = this.collClueServiceImpl
				.findObjects(queryModel);
		// TODO Auto-generated method stub
		return collClues;
	}

	@Override
	public List<Map<String, Integer>> getCollNumStatistic(String curdate,
			int type, List<String> xAxis) {

		List<Map<String, Integer>> mapResults = new ArrayList<Map<String, Integer>>();
		/**
		 * 统计条件，
		 */
		int condition = 0;
		if (type == 0) { // 按年进行统计
			// 不需要条件
		} else if (type == 1) { // 月统计
			if (curdate == null || "".equals(curdate)) {
				Calendar cal = Calendar.getInstance();
				condition = cal.get(Calendar.YEAR); // 取当前年
			}

			else
				condition = Integer.parseInt(curdate);
		} else if (type == 2) { // 日统计
			if (curdate == null || "".equals(curdate))
				condition = Calendar.MONTH;
			else
				condition = Integer.parseInt(curdate);
		}
		Map<String, Map<String, Integer>> results = this.collInfoServiceImpl
				.getCollNum(type, condition);

		Object[] key_arr = results.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object key : key_arr) {
			xAxis.add(String.valueOf(key));
			mapResults.add(results.get(key));
		}
		return mapResults;

	}

	@Override
	public List<Map<String, Integer>> culTypeStatistic(String curdate,
			List<String> legend) {

		List<Map<String, Integer>> mapResults = new ArrayList<Map<String, Integer>>();
		int year = 0;
		if (curdate != null && !"".equals(curdate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date = sdf.parse(curdate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				year = cal.get(Calendar.YEAR);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String, Map<String, Integer>> results = this.collInfoServiceImpl
				.culTypeStat(year);

		Set<Entry<String, Map<String, Integer>>> entries = results.entrySet();
		for (Entry<String, Map<String, Integer>> entry : entries) {
			legend.add(entry.getKey());

			mapResults.add(entry.getValue());
		}
		// mapResults.add(results);
		return mapResults;

	}

}
