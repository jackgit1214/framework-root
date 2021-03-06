package com.system.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;
import com.system.model.SysDepartmentTree;
import com.system.model.SysDept;

@Repository
public interface SysDeptMapper extends IDataMapper<SysDept>,IDataMapperByPage<SysDept> {

    int deleteByPrimaryKey(String deptid);

    List<SysDept> findSysDept(@Param("page") PageResult<SysDept> page);
    
    List<SysDept> selectSysDeptByPage(@Param("userid")String userid,@Param("page") PageResult<SysDept> page); 
    
    int insert(SysDept record);

    int insertSelective(SysDept record);

    int updateByPrimaryKeySelective(SysDept record);
    
    int updateByPrimaryKey(SysDept record);
    
    List<SysDepartmentTree>  selectDepartmentTree(@Param("queryModel") QueryModel queryModel);
    
}