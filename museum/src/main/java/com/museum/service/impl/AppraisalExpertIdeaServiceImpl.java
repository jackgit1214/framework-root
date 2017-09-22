package com.museum.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.framework.common.util.UUIDUtil;
import com.framework.common.util.time.DateFormatUtils;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.framework.web.util.SessionManager;
import com.museum.dao.AppraisalExpertIdeaMapper;
import com.museum.model.AppraisalExpertIdea;
import com.museum.service.AppraisalExpertIdeaService;
import com.system.model.SysUser;

@Service
@Transactional
public class AppraisalExpertIdeaServiceImpl extends
		AbstractBusinessService<AppraisalExpertIdea> implements
		AppraisalExpertIdeaService {

	@Autowired
	private AppraisalExpertIdeaMapper appraisalExpertIdeaMapper;

	public BaseDao getDao() {
		return this.appraisalExpertIdeaMapper;
	}

	public int delete(String recordId) {
		int rows = this.appraisalExpertIdeaMapper.deleteByPrimaryKey(recordId);
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int delete(String[] recordIds) {
		int rows = 0;
		QueryModel queryModel = new QueryModel();
		for (String id : recordIds) {
			QueryModel.Criteria criteria = queryModel.createCriteria();
			criteria.andEqualTo("AppraisalIdeaID", id);
			rows = rows + this.appraisalExpertIdeaMapper.deleteByPrimaryKey(id);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int save(AppraisalExpertIdea record) {
		int rows = 0;
		if (record.getAppraisalideaid() == null
				|| record.getAppraisalideaid() == "") {
			String uuid = UUIDUtil.getUUID();
			record.setAppraisalideaid(uuid);
			record.setUpdatingDate(DateFormatUtils.format(
					Calendar.getInstance(), "yyyyMMddHHmmsss"));
			record.setUpdatingUid(this.getSessionUser().getUserid());
			rows = this.appraisalExpertIdeaMapper.insert(record);
		} else {
			// rows = this.appraisalExpertIdeaMapper.updateByPrimaryKey(record);
			rows = this.appraisalExpertIdeaMapper
					.updateByPrimaryKeySelective(record);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	@Override
	public List<AppraisalExpertIdea> getExpertIdeaByBussId(String id) {

		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("DataId", id);
		List<AppraisalExpertIdea> datas = this.findObjects(queryModel);
		// TODO Auto-generated method stub
		return datas;
	}

	private SysUser getSessionUser() {

		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		this.log.debug(req);
		SessionManager session = (SessionManager) req.getSession()
				.getAttribute("sessionManager");
		if (session == null)
			return null;
		return (SysUser) session.getUser();
	}

	@Override
	public int deleteByDataId(String id) {

		int rows = 0;
		QueryModel queryModel = new QueryModel();
		QueryModel.Criteria criteria = queryModel.createCriteria();
		criteria.andEqualTo("dataId", id);
		rows = rows
				+ this.appraisalExpertIdeaMapper.deleteByCondition(queryModel);

		this.logger.debug("删除 专家意见数据: {}条，dataid为：{}", rows, id);
		// TODO Auto-generated method stub
		return rows;
	}
}