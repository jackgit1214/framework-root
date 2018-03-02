package com.museum.service.impl;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.RestorationinfoMapper;
import com.museum.model.Restorationinfo;
import com.museum.service.RestorationinfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestorationinfoServiceImpl extends AbstractBusinessService<Restorationinfo> implements RestorationinfoService {
    @Autowired
    private RestorationinfoMapper restorationinfoMapper;

    public BaseDao getDao() {
        return this.restorationinfoMapper;
    }

    public int delete(String recordId) {
        int rows = this.restorationinfoMapper.deleteByPrimaryKey(recordId);
        this.logger.debug("rows: {}",rows);
        return rows;
    }

    public int delete(String[] recordIds) {
        int rows=0;
        QueryModel queryModel = new QueryModel();
        for (String id : recordIds){
            QueryModel.Criteria criteria = queryModel.createCriteria();
            criteria.andEqualTo("RestorationInfoID",id);
            rows = rows + this.restorationinfoMapper.deleteByPrimaryKey(id);}
            this.logger.debug("rows: {}",rows);
            return rows;
        }

    public int save(Restorationinfo record) {
        int rows=0;
        if (record.getRestorationinfoid()==null || record.getRestorationinfoid()=="") {
            String uuid = UUIDUtil.getUUID();
            record.setRestorationinfoid(uuid);
            rows = this.restorationinfoMapper.insert(record);
        } else {
            rows = this.restorationinfoMapper.updateByPrimaryKey(record);
        }
        this.logger.debug("rows: {}",rows);
        return rows;
    }
}