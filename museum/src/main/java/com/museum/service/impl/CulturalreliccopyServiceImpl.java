package com.museum.service.impl;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.CulturalreliccopyMapper;
import com.museum.model.Culturalreliccopy;
import com.museum.service.CulturalreliccopyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CulturalreliccopyServiceImpl extends AbstractBusinessService<Culturalreliccopy> implements CulturalreliccopyService {
    @Autowired
    private CulturalreliccopyMapper culturalreliccopyMapper;

    public BaseDao getDao() {
        return this.culturalreliccopyMapper;
    }

    public int delete(String recordId) {
        int rows = this.culturalreliccopyMapper.deleteByPrimaryKey(recordId);
        this.logger.debug("rows: {}",rows);
        return rows;
    }

    public int delete(String[] recordIds) {
        int rows=0;
        QueryModel queryModel = new QueryModel();
        for (String id : recordIds){
            QueryModel.Criteria criteria = queryModel.createCriteria();
            criteria.andEqualTo("CulturalRelicCopyID",id);
            rows = rows + this.culturalreliccopyMapper.deleteByPrimaryKey(id);}
            this.logger.debug("rows: {}",rows);
            return rows;
        }

    public int save(Culturalreliccopy record) {
        int rows=0;
        if (record.getCulturalreliccopyid()==null || record.getCulturalreliccopyid()=="") {
            String uuid = UUIDUtil.getUUID();
            record.setCulturalreliccopyid(uuid);
            rows = this.culturalreliccopyMapper.insert(record);
        } else {
            rows = this.culturalreliccopyMapper.updateByPrimaryKey(record);
        }
        this.logger.debug("rows: {}",rows);
        return rows;
    }
}