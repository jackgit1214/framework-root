package com.museum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.CommCodeMapper;
import com.museum.model.CommCode;
import com.museum.service.CommCodeService;

@Service
@Transactional
public class CommCodeServiceImpl extends AbstractBusinessService<CommCode>
		implements CommCodeService {
	@Autowired
	private CommCodeMapper commCodeMapper;

	public BaseDao getDao() {
		return this.commCodeMapper;
	}

	public int delete(String recordId) {
		int rows = this.commCodeMapper.deleteByPrimaryKey(recordId);
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int delete(String[] recordIds) {
		int rows = 0;
		QueryModel queryModel = new QueryModel();
		for (String id : recordIds) {
			QueryModel.Criteria criteria = queryModel.createCriteria();
			criteria.andEqualTo("codeid", id);
			rows = rows + this.commCodeMapper.deleteByPrimaryKey(id);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int save(CommCode record) {
		int rows = 0;
		if (record.getCodeid() == null || record.getCodeid() == "") {
			String uuid = UUIDUtil.getUUID();
			record.setCodeid(uuid);
			rows = this.commCodeMapper.insert(record);
		} else {
			rows = this.commCodeMapper.updateByPrimaryKey(record);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	@Override
	public List<CommCode> getCommCodes(String table, String field) {

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("tablename", table)
				.andEqualTo("fieldname", field);
		return this.findObjects(queryModel);
		// TODO Auto-generated method stub
	}

	@Override
	public List<CommCode> getCommCodes(String[] codeTypes) {

		QueryModel queryModel = new QueryModel();

		for (String codeType : codeTypes) {
			queryModel.or().andEqualTo("tablename", codeType);
		}
		List<CommCode> datas = this.findObjects(queryModel);
		return datas;
	}
}