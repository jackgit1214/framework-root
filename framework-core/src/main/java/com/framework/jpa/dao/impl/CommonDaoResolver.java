package com.framework.jpa.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.framework.jpa.dao.AbstractDaoUtil;
import com.framework.jpa.dao.ICommonDao;
import com.framework.jpa.dao.queryutil.IQueryProperty;
import com.framework.model.BaseModel;

@Repository
public class CommonDaoResolver<T extends BaseModel> extends AbstractDaoUtil
		implements ICommonDao<T> {

	@PersistenceContext
	private EntityManager em;

	protected String entityName;

	protected Class<T> entityClass;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.framework.dao.ICommonDao#save(com.company.simple.model.TbDataindex)
	 */
	@Override
	public void save(T entity)  {
		try {
			this.em.persist(entity);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.framework.dao.IPersistDao#batchSave(java.util.List)
	 */
	@Override
	public void batchSave(List<T> entities) {
		for (T entity : entities) {
			save(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.framework.dao.ICommonDao#delete(com.company.simple.model.TbDataindex)
	 */
	@Override
	public void delete(T entityObject) {

		Object entity = null;
		if (this.em.contains(entityObject)) //包含实体时不再从数据库中取
			entity = entityObject;
		else {
			Object entityID = null;
			if (entityObject instanceof BaseModel) {
				BaseModel businessModel = (BaseModel) entityObject;
				entityID = businessModel.getPrimaryKey();
			} else {
				entityID = this.exeGetID(entityObject);
			}
			// Object entity = this.em.getReference(entityClass,entityID);
			// 这里不能应用 entityObject.getClass()，可能是延迟加载的对象
			entity = this.em.find(entityClass, entityID);
		}
		try {
			this.em.remove(entity);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void batchDelete(List<T> entities) {
		for (T entity : entities) {
			delete(entity);
		}
	}

	@Override
	public void batchDelete(Class<T> classz, Object[] objects) {
		if (objects == null)
			return;
		for (Object o : objects) {
			T entity = this.em.getReference(classz, o);
			this.em.remove(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.framework.dao.ICommonDao#update(com.company.simple.model.TbDataindex)
	 */
	@Override
	public Object update(T entityObject) {
		try {
			Object result = this.em.merge(entityObject);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void batchUpdate(List<T> entities) {
		// TODO Auto-generated method stub
		for (T entity : entities) {
			update(entity);
		}
	}

	@Override
	public T findById(Object objectId, Class<T> clasz) {
		try {
			T instance = this.em.find(clasz, objectId);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public T findById(Object objectId) {
		return this.findById(objectId, entityClass);
	}

	@Override
	public List<T> findByProperty(String propertyName, final Object value,
			final int... rowStartIdxAndCount) {
		try {
			final String queryString = "select model from " + entityName
					+ " model where model." + propertyName + "= '" + value
					+ "'";
			return this.findBySql(queryString, rowStartIdxAndCount);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.framework.dao.ICommonDao#findAll(int)
	 */
	@Override
	public List<T> findAll(final int... rowStartIdxAndCount) {

		try {
			final String queryString = "select model from " + entityName
					+ " model ";
			return this.findBySql(queryString, rowStartIdxAndCount);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public T load(Object entityId) {
		return this.load(entityId, entityClass);
	}

	@Override
	public T load(Object entityId, Class<T> clasz) {
		T instance = this.em.getReference(clasz, entityId);
		return instance;
	}

	@Override
	public int getQueryCount(IQueryProperty queryProperty) {
		
		String expression= queryProperty.getExpression("model")==null?"":queryProperty.getExpression("model");
		
		String queryString = "select count(*) from " + entityName
				+ " model ";
		if (null!=expression && !"".equals(expression)){
			queryString = queryString + " where " + expression;
		}
		Query query = this.em.createQuery(queryString);
		
		
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public int getTotalCount() {

		try {
			final String queryString = "select count(*) from " + entityName
					+ " model";
			Query query = this.em.createQuery(queryString);
			return Integer.parseInt(query.getSingleResult().toString());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findByProperty(IQueryProperty queryProperty,
			int... rowStartIdxAndCount) {

		final String queryString = "select model from " + entityName
				+ " model where " + queryProperty.getExpression("model");
		return this.findBySql(queryString, rowStartIdxAndCount);
	}

	@Override
	public List<T> findByProperty(IQueryProperty queryProperty,IQueryProperty orderPro,
			int... rowStartIdxAndCount) {
		if (orderPro==null)
			return this.findByProperty(queryProperty, rowStartIdxAndCount);
		
		final String queryString = "select model from " + entityName
				+ " model where " + queryProperty.getExpression("model")+" order by "+orderPro.getExpression();
		return this.findBySql(queryString, rowStartIdxAndCount);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.framework.dao.IQueryDao#queryResult(java.lang.String, int[])
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> queryResult(String jpql, int... rowStartIdxAndCount ) throws Exception {
		List datas = this.findBySql(jpql, rowStartIdxAndCount);
		return this.castEntity(datas, this.entityClass);
	}

	/**
	 * 查询部分字段，必要条件，返回结果独立实体
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> queryNativeResult(String sqlString,Class<?> clazz,String fields,int... rowStartIdxAndCount) throws Exception{
		
		Query query = this.em.createNativeQuery(sqlString);
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
			int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
			if (rowStartIdx > 0) {
				query.setFirstResult(rowStartIdx);
			}

			if (rowStartIdxAndCount.length > 1) {
				int rowCount = Math.max(0, rowStartIdxAndCount[1]);
				if (rowCount > 0) {
					query.setMaxResults(rowCount);
				}
			}
		}
		List objects = query.getResultList();
		List<T> datas = this.castEntity(objects, clazz, this.getSelectField(fields));
		return datas;
	}
	
	@SuppressWarnings("unchecked")
	private List<T> findBySql(String queryString, int... rowStartIdxAndCount) {
		Query query = this.em.createQuery(queryString);
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
			int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
			if (rowStartIdx > 0) {
				query.setFirstResult(rowStartIdx);
			}

			if (rowStartIdxAndCount.length > 1) {
				int rowCount = Math.max(0, rowStartIdxAndCount[1]);
				if (rowCount > 0) {
					query.setMaxResults(rowCount);
				}
			}
		}
		return query.getResultList();

	}

	@Override
	public void setEntityName(Class<T> clasz, String entityName) {
		// TODO Auto-generated method stub
		this.entityClass = clasz;
		this.entityName = entityName;
	}

	@Override
	public EntityManager getEm() {
		return em;
	}


}