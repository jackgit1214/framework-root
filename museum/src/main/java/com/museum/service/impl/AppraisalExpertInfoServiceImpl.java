package com.museum.service.impl;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.AppraisalExpertInfoMapper;
import com.museum.model.AppraisalExpertInfo;
import com.museum.service.AppraisalExpertInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppraisalExpertInfoServiceImpl extends AbstractBusinessService<AppraisalExpertInfo> implements AppraisalExpertInfoService {
    @Autowired
    private AppraisalExpertInfoMapper appraisalExpertInfoMapper;

    public BaseDao getDao() {
        return this.appraisalExpertInfoMapper;
    }

    public int delete(String recordId) {
        int rows = this.appraisalExpertInfoMapper.deleteByPrimaryKey(recordId);
        this.logger.debug("rows: {}",rows);
        return rows;
    }

    public int delete(String[] recordIds) {
        int rows=0;
        QueryModel queryModel = new QueryModel();
        for (String id : recordIds){
            QueryModel.Criteria criteria = queryModel.createCriteria();
            criteria.andEqualTo("Expert_ID",id);
            rows = rows + this.appraisalExpertInfoMapper.deleteByPrimaryKey(id);}
            this.logger.debug("rows: {}",rows);
            return rows;
        }

    public int save(AppraisalExpertInfo record) {
        int rows=0;
        if (record.getExpertId()==null || record.getExpertId()=="") {
            String uuid = UUIDUtil.getUUID();
            record.setExpertId(uuid);
            rows = this.appraisalExpertInfoMapper.insert(record);
        } else {
            rows = this.appraisalExpertInfoMapper.updateByPrimaryKey(record);
        }
        this.logger.debug("rows: {}",rows);
        return rows;
    }
}