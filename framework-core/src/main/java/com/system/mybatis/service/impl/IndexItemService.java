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
import com.system.model.DataIndexTree;
import com.system.model.SysIndexitem;
import com.system.mybatis.dao.SysIndexitemMapper;
import com.system.mybatis.service.IIndexItemService;

@Transactional(readOnly = true)
@Service
public class IndexItemService extends AbstractBusinessService<SysIndexitem>
		implements IIndexItemService {

	@Autowired
	public SysIndexitemMapper sysIndexitemMapper;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int saveIndexItem(SysIndexitem sysIndexItem) {
		// TODO Auto-generated method stub

		int rows = 0;
		if (sysIndexItem.getId() == null || "".equals(sysIndexItem.getId())) {
			String uuid = UUIDUtil.getUUID();
			sysIndexItem.setId(uuid);
			rows = this.sysIndexitemMapper.insert(sysIndexItem);
		} else{
			//SysIndexItem 
			rows = this.sysIndexitemMapper.updateByPrimaryKey(sysIndexItem);
		}
		return rows;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int delete(String[] ids) {
		// TODO Auto-generated method stub

		int rows = 0;
		QueryModel queryModel = new QueryModel();
		for (String id : ids) {
			QueryModel.Criteria criteria = queryModel.createCriteria();

			criteria.andEqualTo("indexid", id);
			rows = rows + this.sysIndexitemMapper.deleteByPrimaryKey(id);
			this.sysIndexitemMapper.deleteByCondition(queryModel);
		}
		return rows;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int delete(String id) {
		// TODO Auto-generated method stub

		int rows = this.sysIndexitemMapper.deleteByPrimaryKey(id);
		return rows;
	}

	@Override
	public BaseDao getDao() {
		// TODO Auto-generated method stub
		return this.sysIndexitemMapper;
	}

	/***
	 * 加载所有数据，暂时不作异步加载
	 * @author lilj
	 * @param indexid 要查询的父级指标ID
	 */
	@Override
	public  List<DataIndexTree>  getIndexTreeByIndexid(String indexid){
		
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("superid",indexid);
		queryModel.setOrderByClause("indexid");
		List<DataIndexTree> datas = this.sysIndexitemMapper.selectIndexTree(queryModel);
		
		return datas;
	}
}
