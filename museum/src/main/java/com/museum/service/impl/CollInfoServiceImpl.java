package com.museum.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.CollInfoMapper;
import com.museum.model.CollInfo;
import com.museum.service.CollInfoService;

@Service
@Transactional
public class CollInfoServiceImpl extends AbstractBusinessService<CollInfo>
		implements CollInfoService {

	@Autowired
	private CollInfoMapper collInfoMapper;

	public BaseDao getDao() {
		return this.collInfoMapper;
	}

	public int delete(String recordId) {
		int rows = this.collInfoMapper.deleteByPrimaryKey(recordId);
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int delete(String[] recordIds) {
		int rows = 0;
		QueryModel queryModel = new QueryModel();
		for (String id : recordIds) {
			QueryModel.Criteria criteria = queryModel.createCriteria();
			criteria.andEqualTo("collinfoID", id);
			rows = rows + this.collInfoMapper.deleteByPrimaryKey(id);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int save(CollInfo record) {
		int rows = 0;
		if (record.getCollinfoid() == null || record.getCollinfoid() == "") {
			String uuid = UUIDUtil.getUUID();
			record.setCollinfoid(uuid);

			if (record.getBatch() == null || "".equals(record.getBatch()))
				record.setBatch(record.getColldate().toString()); // 暂时日期相同为一批

			rows = this.collInfoMapper.insert(record);
		} else {
			rows = this.collInfoMapper.updateByPrimaryKeySelective(record);
			// rows = this.collInfoMapper.updateByPrimaryKey(record);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	@Override
	public Map<String, Map<String, Integer>> getCollNum(int type, int condition) {

		Map<String, Map<String, Integer>> results = this.collInfoMapper
				.getCollNum(type, condition);
		return results;
	}

	@Override
	public Map<String, Map<String, Integer>> culTypeStat(int year) {

		Map<String, Map<String, Integer>> results = this.collInfoMapper
				.culTypeStatistic(year);
		return results;
	}

}