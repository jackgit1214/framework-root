package com.museum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperCRUD;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.museum.model.MessageInbox;

public interface MessageInboxMapper extends IDataMapperByPage<MessageInbox>,
		IDataMapperCRUD<MessageInbox> {

	public List<MessageInbox> selectByConditionJoinUser(
			@Param("queryModel") QueryModel queryModel,
			@Param("page") PageResult<MessageInbox> page);

	public List<MessageInbox> selectByConditionJoinUser(
			@Param("queryModel") QueryModel queryModel);
	//
	// public List<MessageInbox> selectMsgByFromUserAndToUser(
	// @Param("queryModel") QueryModel queryModel,
	// @Param("page") PageResult<MessageInbox> page);
	//
	// public List<MessageInbox> selectMsgByFromUserAndToUser(
	// @Param("queryModel") QueryModel queryModel);
}