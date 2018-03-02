package com.museum.service.impl;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.DuplicateinfoinfoMapper;
import com.museum.model.Duplicateinfoinfo;
import com.museum.service.DuplicateinfoinfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DuplicateinfoinfoServiceImpl extends AbstractBusinessService<Duplicateinfoinfo> implements DuplicateinfoinfoService {
    @Autowired
    private DuplicateinfoinfoMapper duplicateinfoinfoMapper;

    public BaseDao getDao() {
        return this.duplicateinfoinfoMapper;
    }

    public int delete(String recordId) {
        int rows = this.duplicateinfoinfoMapper.deleteByPrimaryKey(recordId);
        this.logger.debug("rows: {}",rows);
        return rows;
    }

    public int delete(String[] recordIds) {
        int rows=0;
        QueryModel queryModel = new QueryModel();
        for (String id : recordIds){
            QueryModel.Criteria criteria = queryModel.createCriteria();
            criteria.andEqualTo("DuplicateInfoID",id);
            rows = rows + this.duplicateinfoinfoMapper.deleteByPrimaryKey(id);}
            this.logger.debug("rows: {}",rows);
            return rows;
        }

    public int save(Duplicateinfoinfo record) {
        int rows=0;
        if (record.getDuplicateinfoid()==null || record.getDuplicateinfoid()=="") {
            String uuid = UUIDUtil.getUUID();
            record.setDuplicateinfoid(uuid);
            rows = this.duplicateinfoinfoMapper.insert(record);
        } else {
            rows = this.duplicateinfoinfoMapper.updateByPrimaryKey(record);
        }
        this.logger.debug("rows: {}",rows);
        return rows;
    }
}