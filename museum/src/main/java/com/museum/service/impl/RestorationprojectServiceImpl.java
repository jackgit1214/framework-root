package com.museum.service.impl;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.RestorationprojectMapper;
import com.museum.model.Restorationproject;
import com.museum.service.RestorationprojectService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestorationprojectServiceImpl extends AbstractBusinessService<Restorationproject> implements RestorationprojectService {
    @Autowired
    private RestorationprojectMapper restorationprojectMapper;

    public BaseDao getDao() {
        return this.restorationprojectMapper;
    }

    public int delete(String recordId) {
        int rows = this.restorationprojectMapper.deleteByPrimaryKey(recordId);
        this.logger.debug("rows: {}",rows);
        return rows;
    }

    public int delete(String[] recordIds) {
        int rows=0;
        QueryModel queryModel = new QueryModel();
        for (String id : recordIds){
            QueryModel.Criteria criteria = queryModel.createCriteria();
            criteria.andEqualTo("RestorationProjectid",id);
            rows = rows + this.restorationprojectMapper.deleteByPrimaryKey(id);}
            this.logger.debug("rows: {}",rows);
            return rows;
        }

    public int save(Restorationproject record) {
        int rows=0;
        if (record.getRestorationprojectid()==null || record.getRestorationprojectid()=="") {
            String uuid = UUIDUtil.getUUID();
            record.setRestorationprojectid(uuid);
            rows = this.restorationprojectMapper.insert(record);
        } else {
            rows = this.restorationprojectMapper.updateByPrimaryKey(record);
        }
        this.logger.debug("rows: {}",rows);
        return rows;
    }
}