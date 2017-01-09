package com.system.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.framework.mybatis.service.IBusinessService;
import com.system.model.DataIndexTree;
import com.system.model.SysIndexitem;

@Service
public interface IIndexItemService extends IBusinessService<SysIndexitem> {


	public int saveIndexItem(SysIndexitem sysIndexItem);
	
	public int delete(String[] ids);

	public int delete(String id);
	
	public List<DataIndexTree> getIndexTreeByIndexid(String indexid);
	
}
