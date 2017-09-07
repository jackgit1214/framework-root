package com.system.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.system.model.SysCode;
import com.system.model.SysCodeTree;
import com.system.mybatis.dao.SysCodeMapper;
import com.system.mybatis.service.ISysCodeService;

@Service
public class SysCodeService extends AbstractBusinessService<SysCode> implements
		ISysCodeService {

	@Autowired
	private SysCodeMapper sysCodeMapper;

	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return this.sysCodeMapper;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int saveCode(SysCode sysCode) {
		// TODO Auto-generated method stub
		int rows = 0;
		if (sysCode.getCodeid() == null || "".equals(sysCode.getCodeid())) {
			String uuid = UUIDUtil.getUUID();
			sysCode.setCodeid(uuid);
			rows = this.sysCodeMapper.insert(sysCode);
		} else
			rows = this.sysCodeMapper.updateByPrimaryKey(sysCode);

		return rows;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int delete(String[] ids) {
		int rows = 0;
		// QueryModel queryModel = new QueryModel();
		for (String id : ids) {
			// QueryModel.Criteria criteria = queryModel.createCriteria();

			// criteria.andEqualTo("codeid", id);
			rows = rows + this.sysCodeMapper.deleteByPrimaryKey(id);
			// this.sysCodeMapper.deleteByCondition(queryModel);
		}
		return rows;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int delete(String id) {
		// TODO Auto-generated method stub

		int rows = this.sysCodeMapper.deleteByPrimaryKey(id);
		return rows;
	}

	@Override
	public List<SysCodeTree> getCodeDataByCodeid(String codeid, String codetype) {
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();

		if (codeid != null && !"".equals(codeid))
			criteria.andEqualTo("superid", codeid);

		// 2代码是指标型管理数据，需要动态添加
		criteria.andEqualTo("applyto", 2);
		if (codetype != null && !"".equals(codetype))
			criteria.andEqualTo("codetype", codetype);

		queryModel.setOrderByClause("code");
		List<SysCodeTree> datas = this.sysCodeMapper.selectCodeTree(queryModel);

		return datas;
	}

	@Override
	public List<SysCode> getCodeData(String[] codeTypes) {

		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();

		// 2代码是指标型管理数据，需要动态添加
		criteria.andEqualTo("applyto", 2);
		for (String codeType : codeTypes) {
			queryModel.or().andEqualTo("codetype", codeType);
		}

		queryModel.setOrderByClause("code");

		List<SysCode> datas = this.sysCodeMapper.selectByCondition(queryModel);

		return datas;
	}
}
