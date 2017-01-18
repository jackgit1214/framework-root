package com.museum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.SysDepotMapper;
import com.museum.model.SysDepot;
import com.museum.model.SysDepotTree;
import com.museum.service.ISysDepotService;

@Service
@Transactional(readOnly = true)
public class SysDepotServiceImpl extends AbstractBusinessService<SysDepot>
		implements ISysDepotService {

	@Autowired
	private SysDepotMapper sysDepotMapper;

	@Override
	public int updateDepot(SysDepot record) {
		// TODO Auto-generated method stub
		int rows = 0;
		if (record.getDepotId() == "" || record.getDepotId() == null) {
			String uuid = UUIDUtil.getUUID();

			record.setDepotId(uuid);
			rows = this.sysDepotMapper.insert(record);
		} else {
			rows = this.sysDepotMapper.updateByPrimaryKey(record);
		}
		return rows;
	}

	@Override
	public int delete(String[] deptids) {
		// TODO Auto-generated method stub

		int rows = 0;
		QueryModel queryModel = new QueryModel();
		for (String id : deptids) {
			QueryModel.Criteria criteria = queryModel.createCriteria();

			criteria.andEqualTo("deptid", id);
			rows = rows + this.sysDepotMapper.deleteByPrimaryKey(id);
			this.sysDepotMapper.deleteByCondition(queryModel);
		}
		return rows;
	}

	@Override
	public int delete(String depotid) {
		int rows = this.sysDepotMapper.deleteByPrimaryKey(depotid);
		return rows;
	}

	@Override
	public List<SysDepotTree> getDepotBySupperId(String superid) {

		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("superid", superid);
		queryModel.setOrderByClause("depot_id");

		List<SysDepotTree> datas = this.sysDepotMapper
				.selectDepotTree(queryModel);
		return datas;

	}

	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return this.sysDepotMapper;
	}

}
