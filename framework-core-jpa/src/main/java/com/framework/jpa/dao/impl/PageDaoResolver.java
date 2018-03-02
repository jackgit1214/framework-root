/**
 * 
 */
package com.framework.jpa.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.framework.jpa.dao.IQueryPageDao;
import com.framework.jpa.dao.queryutil.IQueryProperty;
import com.framework.jpa.dao.queryutil.IQueryProperty.OperType;
import com.framework.jpa.dao.queryutil.QueryProperty;
import com.framework.model.BaseModel;
import com.framework.web.page.PageResult;

/**
 * @author lilj
 *
 */
public class PageDaoResolver<T extends BaseModel> extends CommonDaoResolver<T> implements
		IQueryPageDao<T> {

	
	public static final int DEFAULT_PAGEROWNUM = 20;
	private int pageRowNum = DEFAULT_PAGEROWNUM;
	
	/**
	 * 
	 */
	public PageDaoResolver() {
		super();
	}

	/**
	 * @param pageRowNum
	 */
	public PageDaoResolver(int pageRowNum) {
		super();
		this.pageRowNum = pageRowNum;
	}

	
	@Override
	public PageResult findByProperty(String propertyName,
			Object value, int pageNum) {
		IQueryProperty queryProperty = new QueryProperty(propertyName,value,OperType.equals); 
		return this.findByProperty(queryProperty, pageNum);
	}

	@Override
	public PageResult findByProperty(IQueryProperty queryProperty,
			int pageNum) {
		int queryCount = this.getQueryCount(queryProperty);
		PageResult pageResult = this.calculatePage(queryCount, pageNum);
		List<T> pageDatas = this.findByProperty(queryProperty, pageResult.getStartRow()-1,pageResult.getEndRow() - pageResult.getStartRow()+1);
		pageResult.setPageDatas(pageDatas);
		return pageResult;
	}

	@Override
	public PageResult findByProperty(IQueryProperty queryProperty,IQueryProperty orderPro,
			int pageNum) {
		int queryCount = this.getQueryCount(queryProperty);
		PageResult pageResult = this.calculatePage(queryCount, pageNum);
		List<T> pageDatas = this.findByProperty(queryProperty,orderPro, pageResult.getStartRow()-1,pageResult.getEndRow() - pageResult.getStartRow()+1);
		pageResult.setPageDatas(pageDatas);
		return pageResult;
	}	
	
	@Override
	public PageResult queryResult(String jpql, int pageNum)
			throws Exception {
		String totalCountJpql = "select count(*) from ( "+jpql+" )";
		Query totalQuery = this.getEm().createQuery(totalCountJpql);
		int totalCount = Integer.parseInt(totalQuery.getSingleResult().toString());
		PageResult pageResult = this.calculatePage(totalCount, pageNum);
		List<T> pageDatas = this.queryResult(jpql,  pageResult.getStartRow()-1,pageResult.getEndRow() - pageResult.getStartRow()+1);
		pageResult.setPageDatas(pageDatas);
		return pageResult;
	}

	@Override
	public PageResult queryNativeResult(String sql,Class<?> clazz,String fields, int pageNum) throws Exception {
		
		String totalCountSql = "select count(*) from ( "+sql+" ) model";
		Query totalQuery = this.getEm().createNativeQuery(totalCountSql);
		int totalCount = Integer.parseInt(totalQuery.getSingleResult().toString());
		PageResult pageResult = this.calculatePage(totalCount, pageNum);
		List<?> pageDatas = this.queryNativeResult(sql, clazz, fields, 
			pageResult.getStartRow()-1,pageResult.getEndRow() - pageResult.getStartRow()+1);
		pageResult.setPageDatas(pageDatas);
		
		return pageResult;
	}

	@Override
	public PageResult findAll(int pageNum) {
		//总行数
		int totalCount = this.getTotalCount();
		PageResult pageResult = this.calculatePage(totalCount, pageNum);
		List<T> pageDatas = this.findAll(pageResult.getStartRow()-1,pageResult.getEndRow() - pageResult.getStartRow()+1);
		pageResult.setPageDatas(pageDatas);
		return pageResult;
	}

	/**
	 * 
	 * @param totalCount 总行数
	 * @param pageNum 当前页
	 * @return
	 */
	private PageResult calculatePage(int totalCount,int pageNum){
		PageResult pageResult = new PageResult(pageNum, totalCount, this.pageRowNum);
		return pageResult;
	}
	
	public int getPageRowNum() {
		return pageRowNum;
	}

	public void setPageRowNum(int pageRowNum) {
		this.pageRowNum = pageRowNum != 0?pageRowNum:DEFAULT_PAGEROWNUM;
	}
	
}
