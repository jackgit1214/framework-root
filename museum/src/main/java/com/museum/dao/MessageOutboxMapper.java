package com.museum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.museum.model.MessageOutbox;

public interface MessageOutboxMapper extends IDataMapperByPage<MessageOutbox>,
		IDataMapperCRUD<MessageOutbox> {

	public List<MessageOutbox> selectByConditionJoinUser(
			@Param("queryModel") QueryModel queryModel,
			@Param("page") PageResult<MessageOutbox> page);

	public List<MessageOutbox> selectByConditionJoinUser(
			@Param("queryModel") QueryModel queryModel);
}