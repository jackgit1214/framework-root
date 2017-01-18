package com.museum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.framework.mybatis.service.IBusinessService;
import com.museum.model.SysDepot;
import com.museum.model.SysDepotTree;

@Service
public interface ISysDepotService extends IBusinessService<SysDepot> {

	public int updateDepot(SysDepot record);
	
	public int delete(String[] depotids);

	public int delete(String depotid);
	
	public List<SysDepotTree> getDepotBySupperId(String superid);
	

}
