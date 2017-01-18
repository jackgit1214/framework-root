package com.museum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.framework.mybatis.model.QueryModel;
import com.museum.model.SysDepot;
import com.museum.model.SysDepotTree;

@Repository
public interface SysDepotMapper extends IDataMapperCRUD<SysDepot>,IDataMapperByPage<SysDepot> {
	
   
	  List<SysDepotTree>  selectDepotTree(@Param("queryModel") QueryModel queryModel);
}