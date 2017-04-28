package com.museum.service.impl;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.CollClueMapper;
import com.museum.model.CollClue;
import com.museum.service.CollClueService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CollClueServiceImpl extends AbstractBusinessService<CollClue> implements CollClueService {
    @Autowired
    private CollClueMapper collClueMapper;

    public BaseDao getDao() {
        return this.collClueMapper;
    }

    public int delete(String recordId) {
        int rows = this.collClueMapper.deleteByPrimaryKey(recordId);
        this.logger.debug("rows: {}",rows);
        return rows;
    }

    public int delete(String[] recordIds) {
        int rows=0;
        QueryModel queryModel = new QueryModel();
        for (String id : recordIds){
            QueryModel.Criteria criteria = queryModel.createCriteria();
            criteria.andEqualTo("clueID",id);
            rows = rows + this.collClueMapper.deleteByPrimaryKey(id);}
            this.logger.debug("rows: {}",rows);
            return rows;
        }

    public int save(CollClue record) {
        int rows=0;
        if (record.getClueid()==null || record.getClueid()=="") {
            String uuid = UUIDUtil.getUUID();
            record.setClueid(uuid);
            rows = this.collClueMapper.insert(record);
        } else {
            rows = this.collClueMapper.updateByPrimaryKey(record);
        }
        this.logger.debug("rows: {}",rows);
        return rows;
    }
}