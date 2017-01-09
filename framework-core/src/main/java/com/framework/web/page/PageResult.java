package com.framework.web.page;

import java.io.Serializable;
import java.util.List;


/**
 * 分页的数据模型类，用于页面展示分页数据
 * @author lilj
 *
 */
public class   PageResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6405087297327140471L;

	/**
	 * 当前页的记录列表
	 */
	private List<?> pageDatas;
    
	/**
	 * 缓冲数据存储上页及下页，是否可提高速度有待验证
	 */
	private List<?> nextPageDatas;
	private List<?> priorPageDatas;
	
	/**
	 * 当前页开始行
	 */
	private int startRow;

	/**
	 * 当前页结束行
	 */
	private int endRow;

	/**
	 * 页行数
	 */
	private int pageRowNum;

	/**
	 * 全部记录的数量
	 */
	private int totalRowNum;

	/**
	 * 第一页
	 */
	private int firstPage=1;
	
	/**
	 * 上一页的页码
	 */
	private int prevPage;

	/**
	 * 下一页的页码
	 */
	private int nextPage;

	/**
	 * 最后一页的页码，就是总页数
	 */
	private int lastPage;

	/**
	 * 当前页是第几页
	 */
	private int curPage;


	public PageResult(int curPage,  int totalRowNum,int pageRowNum) {
		
		this.pageRowNum = pageRowNum;
		this.totalRowNum = totalRowNum;
		
		//总页数，即最后一页的页码，总行数除以每页行数
		//IEEEremainder
		if (totalRowNum%pageRowNum >0 )
			this.lastPage = totalRowNum / pageRowNum + 1;
		else
			this.lastPage = totalRowNum / pageRowNum;
		
		if (this.lastPage == 0)
			this.lastPage = 1;
		
		if (curPage > this.lastPage)
			this.curPage = this.lastPage;
		else
			this.curPage = curPage;
		
		
		//当前页的开始行
		this.startRow = (this.curPage-1)  * pageRowNum + 1;
		//当前页的结束行
		this.endRow = this.curPage * pageRowNum;
		if (this.endRow > this.totalRowNum)
			this.endRow = this.totalRowNum;
		
		if (this.curPage == this.firstPage)
			this.prevPage = 0; //为0是表示没有前页
		else
			this.prevPage = this.curPage - 1;
		
		if (this.curPage == this.lastPage)
			this.nextPage = 0;
		else
			this.nextPage = this.curPage+1;
	}


	public List<?> getPageDatas() {
		return pageDatas;
	}


	public void setPageDatas(List<?> pageDatas) {
		this.pageDatas = pageDatas;
	}


	public List<?> getNextPageDatas() {
		return nextPageDatas;
	}


	public void setNextPageDatas(List<?> nextPageDatas) {
		this.nextPageDatas = nextPageDatas;
	}


	public List<?> getPriorPageDatas() {
		return priorPageDatas;
	}


	public void setPriorPageDatas(List<?> priorPageDatas) {
		this.priorPageDatas = priorPageDatas;
	}


	public int getStartRow() {
		return startRow;
	}


	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}


	public int getEndRow() {
		return endRow;
	}


	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}


	public int getPageRowNum() {
		return pageRowNum;
	}


	public void setPageRowNum(int pageRowNum) {
		this.pageRowNum = pageRowNum;
	}


	public int getTotalRowNum() {
		return totalRowNum;
	}


	public void setTotalRowNum(int totalRowNum) {
		this.totalRowNum = totalRowNum;
	}


	public int getFirstPage() {
		return firstPage;
	}


	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}


	public int getPrevPage() {
		return prevPage;
	}


	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}


	public int getLastPage() {
		return lastPage;
	}


	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}


	public int getCurPage() {
		return curPage;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	//get set method
	
	
}
