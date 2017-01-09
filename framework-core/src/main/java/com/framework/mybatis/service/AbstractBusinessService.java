package com.framework.mybatis.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import com.framework.mybatis.dao.Base.BaseDao;
import com.framework.mybatis.dao.Base.IDataMapper;
import com.framework.mybatis.dao.Base.IDataMapperByPage;
import com.framework.mybatis.dao.Base.IDataMapperWithBlob;
import com.framework.mybatis.model.QueryModel;
import com.framework.mybatis.util.PageResult;

public abstract class AbstractBusinessService<T> implements IBusinessService<T> {

	protected int DEFAULTROWNUMPERPAGE = 10;

	private IDataMapper<T> dataMapper;
	private IDataMapperWithBlob<T> dataMapperWithBlob;
	private IDataMapperByPage<T> dataMapperByPage;

	protected final Log log = LogFactory.getLog(this.getClass());
	
	SimpleKeyGenerator k = new SimpleKeyGenerator();

	@Cacheable(value = "CustomerCache", key = "#root.target")
	public List<T> findAllObjects() {
		this.initDataMapper();
		return this.dataMapper.selectByCondition(null);
	}

	@Override
	public T findObjectById(Object object) {
		this.initDataMapper();
		return this.dataMapper.selectByPrimaryKey(object);
	}

	@Override
	@Cacheable(value = "CustomerCache", key = "#queryModel.condition")
	public List<T> findObjects(QueryModel queryModel) {
		this.initDataMapper();
		return this.dataMapper.selectByCondition(queryModel);
	}

	@Override
	public PageResult<T> findObjectsByPage(QueryModel queryModel,
			PageResult<T> page) throws Exception {
		this.initDataMapper();
		if (this.dataMapperByPage == null)
			throw new Exception("未实现分页接口");

		List<T> pageDatas = this.dataMapperByPage.selectByCondition(queryModel,
				page);
		page.setPageDatas(pageDatas);
		return page;
	}

	
	public PageResult<T> findObjectsByPage(IDataMapper baseDao,String methodName,QueryModel queryModel,
			PageResult<T> page) throws Exception {
		Proxy.newProxyInstance(this.dataMapper.getClass().getClassLoader(), new Class[]{},
					new MapperProxyHandler(baseDao));
		
		List<T> pageDatas = this.dataMapperByPage.selectByCondition(queryModel,
				page);
		page.setPageDatas(pageDatas);
		return page;
	}
	
	@PostConstruct
	public abstract BaseDao getDao();

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void initDataMapper() {
		BaseDao baseDao = this.getDao();
		if (baseDao instanceof IDataMapper){
			this.dataMapper = (IDataMapper<T>) baseDao;
		}
		if (baseDao instanceof IDataMapperWithBlob)
			this.dataMapperWithBlob = (IDataMapperWithBlob<T>) baseDao;
		if (baseDao instanceof IDataMapperByPage){
			this.dataMapperByPage = (IDataMapperByPage<T>) baseDao;
		}
	}

	class MapperProxyHandler implements InvocationHandler {
		private Object proxied;
		public MapperProxyHandler(Object proxied) {
			this.proxied = proxied;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			return method.invoke(proxied, args);
		}
	}


	
}
