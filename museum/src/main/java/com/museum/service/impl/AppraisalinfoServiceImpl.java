package com.museum.service.impl;

import java.util.Calendar;

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
import com.museum.dao.AppraisalinfoMapper;
import com.museum.model.Appraisalinfo;
import com.museum.service.AppraisalinfoService;
import com.system.model.SysUser;

@Service
@Transactional
public class AppraisalinfoServiceImpl extends
		AbstractBusinessService<Appraisalinfo> implements AppraisalinfoService {

	@Autowired
	private AppraisalinfoMapper appraisalinfoMapper;

	public BaseDao getDao() {
		return this.appraisalinfoMapper;
	}

	public int delete(String recordId) {
		int rows = this.appraisalinfoMapper.deleteByPrimaryKey(recordId);
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int delete(String[] recordIds) {
		int rows = 0;
		QueryModel queryModel = new QueryModel();
		for (String id : recordIds) {
			QueryModel.Criteria criteria = queryModel.createCriteria();
			criteria.andEqualTo("AppraisalID", id);
			rows = rows + this.appraisalinfoMapper.deleteByPrimaryKey(id);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
	}

	public int save(Appraisalinfo record) {
		int rows = 0;
		if (record.getAppraisalid() == null || record.getAppraisalid() == "") {
			String uuid = UUIDUtil.getUUID();

			int seqVal = this.getNextVal(sequenceName);
			record.setApprBatchNo(record.getApprType()
					+ DateFormatUtils.format(Calendar.getInstance(), "yyyyMM")
					+ String.format("%04d", seqVal)); // 批次号，自动生成，类型加年月，加顺序号
			record.setAppraisalid(uuid);
			record.setUpdatingDate(DateFormatUtils.format(
					Calendar.getInstance(), "yyyyMMddHHmmsss"));
			record.setUpdatingUid(this.getSessionUser().getUserid());
			rows = this.appraisalinfoMapper.insert(record);
		} else {
			rows = this.appraisalinfoMapper.updateByPrimaryKey(record);
		}
		this.logger.debug("rows: {}", rows);
		return rows;
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
}