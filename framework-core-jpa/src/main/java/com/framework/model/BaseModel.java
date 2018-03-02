/**
 * 
 */
package com.framework.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author lilj
 * 
 */
@MappedSuperclass
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 7801466218972694763L;

	private transient EOUtility eoutil;

	protected boolean selected;

	@Transient
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Transient
	protected EOUtility getBeanUtility() {
		if (eoutil == null) {
			eoutil = new EOUtility(this);
		}
		return eoutil;
	}

	@Override
	public String toString() {
		return getBeanUtility().beanToString();
	}

	@Override
	public boolean equals(Object obj) {
		return getBeanUtility().equalsBean(obj);
	}

	@Override
	public int hashCode() {
		return getBeanUtility().hashCodeBean();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Object obj = null;
		try {
			obj = getBeanUtility().cloneBean();
		} catch (Exception e) {
			throw new CloneNotSupportedException(e.getMessage());
		}

		return obj;
	}

	/**
	 * 得到所有可持久化字段的名称
	 * 
	 * @author lilj
	 * @return 名称列表
	 */
	@Transient
	public String[] getAttributeNames() {
		return getBeanUtility().getAttributeNames();
	}

	/**
	 * 得到某个字段的值
	 * 
	 * @author lilj
	 * @param attributeName     字段名
	 * @return 值
	 */
	@Transient
	public Object getAttributeValue(String attributeName) {
		return getBeanUtility().getAttributeValue(attributeName);
	}

	/**
	 * 设置某个字段的值
	 * @param attributeName 字段名
	 * @param value 值
	 */
	@Transient
	public void setAttributeValue(String attributeName, Object value) {
		getBeanUtility().setAttributeValue(attributeName, value);
	}

	@SuppressWarnings("static-access")
	@Transient
	public String getEnumDescription(String enumAttributeName) {
		Object value = getAttributeValue(enumAttributeName);
		return getBeanUtility().getEnumDescription(value);
	}

	/**
	 * 获得实体对应的表名
	 * @return
	 */
	@Transient
	public String getTableName() {
		return getBeanUtility().getTableName();
	}

	/**
	 * 比较此对象与另一个对象的差别，并返回值不同的字段的名称。
	 * @param antherBean 将要比较的对象
	 * @return 值不同的字段名
	 */
	@Transient
	public List<String> getDifferentField(BaseModel anotherBean) {
		return getBeanUtility().getDifferentField(anotherBean);
	}



	/**
	 * 比较主键值是否相同
	 * @param obj
	 * @return
	 */
	@Transient
	public boolean equalsPK(Object obj) {
		if (obj == null)// 对象为空不比较
			return false;
		// 类型不同不必进行比较
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}

		// 不是BaseEO，不必比较
		if (!(obj instanceof BaseModel)) {
			return false;
		}

		BaseModel eo = (BaseModel) obj;

		if (getPrimaryKey() != null && eo.getPrimaryKey() != null) {
			if (getPrimaryKey().equals(eo.getPrimaryKey()))
				return true;
			return false;
		} else {
			return false;
		}

	}

	/**
	 * 拷贝另一个对象中的字段值到当前对象中
	 * @param fromEO 被复制对象
	 * @param copyAttributes  需要复制的字段
	 */
	public void copyAttributeValue(BaseModel fromEO, String[] copyAttributes) {
		if (copyAttributes == null)
			return;

		for (String attr : copyAttributes) {
			this.setAttributeValue(attr, fromEO.getAttributeValue(attr));
		}
	}

	/**
	 * 加载所有延迟加载字段
	 */
	public void loadLazyAttributes() {
		getBeanUtility().loadLazyField();
	}

	@Transient
    public boolean isNew() {
        return (this.getPrimaryKey() == null);
    }

	/** 
	 * 获取主键值
	 * @return 主键值
	 */
	@Transient
	public abstract Object getPrimaryKey();


	
}
