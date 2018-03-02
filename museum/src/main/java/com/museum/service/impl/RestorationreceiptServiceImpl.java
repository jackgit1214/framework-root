package com.museum.service.impl;

import com.framework.common.util.UUIDUtil;
import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.service.AbstractBusinessService;
import com.museum.dao.RestorationreceiptMapper;
import com.museum.model.Restorationreceipt;
import com.museum.service.RestorationreceiptService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestorationreceiptServiceImpl extends AbstractBusinessService<Restorationreceipt> implements RestorationreceiptService {
    @Autowired
    private RestorationreceiptMapper restorationreceiptMapper;

    public BaseDao getDao() {
        return this.restorationreceiptMapper;
    }

    public int delete(String recordId) {
        int rows = this.restorationreceiptMapper.deleteByPrimaryKey(recordId);
        this.logger.debug("rows: {}",rows);
        return rows;
    }

    public int delete(String[] recordIds) {
        int rows=0;
        QueryModel queryModel = new QueryModel();
        for (String id : recordIds){
            QueryModel.Criteria criteria = queryModel.createCriteria();
            criteria.andEqualTo("RestorationReceiptID",id);
            rows = rows + this.restorationreceiptMapper.deleteByPrimaryKey(id);}
            this.logger.debug("rows: {}",rows);
            return rows;
        }

    public int save(Restorationreceipt record) {
        int rows=0;
        if (record.getRestorationreceiptid()==null || record.getRestorationreceiptid()=="") {
            String uuid = UUIDUtil.getUUID();
            record.setRestorationreceiptid(uuid);
            rows = this.restorationreceiptMapper.insert(record);
        } else {
            rows = this.restorationreceiptMapper.updateByPrimaryKey(record);
        }
        this.logger.debug("rows: {}",rows);
        return rows;
    }
}