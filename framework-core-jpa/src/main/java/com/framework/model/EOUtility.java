package com.framework.model;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.framework.common.util.time.DateUtil;

/**
 * 实体bean工具类<br>
 * <b> 注意：此工具类只能应用与BaseEntityBean及其子类！ </b><br>
 * 实现功能： <li>实体bean转换为string <li>实体bean比较 <li>得到实体bean哈希值 <li>深克隆实体bean
 */
@SuppressWarnings("unchecked")
public class EOUtility {

	// /** 当前bean中所有的字段(包括此类以及父类中的公有和非公有字段) <get方法名,字段> **/
	// private HashMap<String,Field> hm_Field;

	/** 当前bean类以及父类中公有的get方法 <字段名,get方法> **/
	private HashMap<String, Method> hm_Geters;

	/** 当前bean类以及父类中公有的set方法 <字段名,get方法> **/
	private HashMap<String, Method> hm_Seters;
	
	/** 当前bean类以及父类中延迟加载字段的get方法 <字段名,get方法> **/
	private HashMap<String, Method> hm_LazyGeters;
	/** 当前bean类以及父类中延迟加载字段的get方法 <字段名,set方法> **/
	private HashMap<String, Method> hm_LazySeters;

	/** 应用本工具类的实体对象 **/
	private BaseModel bean;

	/** 应用本工具类的实体对象类型 **/
	private Class<? extends BaseModel> clazz;
	
	private String beanDispName;
	
	/** 存储字段对应中文名 **/
	private HashMap<String, String> hm_DispNames;

	public EOUtility(BaseModel bean) {
		init(bean);
	}

	private void init(BaseModel bean) {
		// 当前bean与新传入的bean是同一个bean时不必进行初始化
		if (this.bean == bean)
			return;

		this.bean = bean;
		clazz = bean.getClass();
		initGetterAndSetters();
	}
	
	private void buildGetterANDSetters(Class beanclass) {
		// 得到当前类字段名称
		Field[] fields = beanclass.getDeclaredFields();
		String fieldname = null;
		// 拼接字段对应的方法名
		for (Field field : fields) {
			// 一对多字段不要参加toString，hashcode和equals方法，不需要加载，一旦加载反而会引起数据级联更新时出错！！
			if (isLazyField(field.getAnnotations())) {
				continue;
			}
			fieldname = field.getName();
			for (PropertyDescriptor property : propertyDescriptors) {
				if (fieldname.equals(property.getName())) {
					Method reader = property.getReadMethod();
					Method writer = property.getWriteMethod();

					Transient t = reader.getAnnotation(Transient.class);
					if(t==null){
						if (reader != null
								&& !(isLazyField(reader.getAnnotations()))){
							hm_Geters.put(fieldname, reader);// 非延迟
							if (writer != null)
								hm_Seters.put(fieldname, writer);
						}else{
							hm_LazyGeters.put(fieldname, reader); //延迟
							if (writer != null)
								hm_LazySeters.put(fieldname, writer);
						}
					}
				}
			}
		}
		// 当前类不是 BaseEntityBean时，递归调用
		if (!beanclass.equals(BaseModel.class)) {
			buildGetterANDSetters((Class<? extends BaseModel>) beanclass
					.getSuperclass());
		}
	}
	
	/**
	 * 得到entity要显示的中文名
	 * @return
	 */
	public String getEODisplayName(){
		if(beanDispName == null){
		EODisplayName ea = clazz.getAnnotation(EODisplayName.class);
			if(ea != null){
				beanDispName = ea.value();
			}else{
				beanDispName = clazz.getSimpleName();
			}
		}
		return beanDispName;
	}

	/**
	 * 得到字段显示的名称
	 * @param fieldName
	 * @return
	 */
	public String getFieldDisplayName(String fieldName){
		String dispName = hm_DispNames.get(fieldName);
		if(dispName == null){
			dispName = getFieldDisplayName(clazz,fieldName);
			hm_DispNames.put(fieldName,dispName );
		}
		
		return dispName;
	}
	
	private String getFieldDisplayName(Class<?> clz ,String fieldName){
		String dispName = null;
		Field f;
		try {
			f = clz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			return fieldName;
		} catch (NoSuchFieldException e) {
			if(!clz.getSuperclass().equals(BaseModel.class))
				return getFieldDisplayName(clz.getSuperclass(),fieldName);
			else
				return fieldName;
		}
		FieldDisplayName am = f.getAnnotation(FieldDisplayName.class);
		if(am!=null){
			dispName = am.value();
		}else{
			dispName = fieldName;
		}
		return dispName;
	}
	
	/*
	 * 根据注解判断是否是延迟加载的字段
	 */
	public static boolean isLazyField(Annotation[] annotations) {
		// 满足循环内任何一个条件则为延迟加载字段
		for (Annotation annotation : annotations) {
			if (annotation instanceof OneToOne) {
				if (FetchType.LAZY.equals(((OneToOne) annotation).fetch())) {
					return true;
				}
			}
			if (annotation instanceof ManyToOne) {
				if (FetchType.LAZY.equals(((ManyToOne) annotation).fetch())) {
					return true;
				}
			}
			// OneToMany 默认为延迟加载,如果没有标注立即加载则都是延迟加载
			if (annotation instanceof OneToMany) {
				if (!FetchType.EAGER.equals(((OneToMany) annotation).fetch())) {
					return true;
				}
			}
			// ManyToMany 同上
			if (annotation instanceof ManyToMany) {
				if (!FetchType.EAGER.equals(((ManyToMany) annotation).fetch())) {
					return true;
				}
			}

			// Lob字段内容庞大,不管是不是延迟加载,全部不进行处理
			if (annotation instanceof Lob) {
				return true;
			}

			// 非持久字段不处理
			if (annotation instanceof Transient) {
				return true;
			}
		}

		return false;
	}

	PropertyDescriptor[] propertyDescriptors = null;

	/**
	 * 初始化get和set方法
	 */
	private void initGetterAndSetters() {
		try {
			propertyDescriptors = Introspector.getBeanInfo(clazz)
					.getPropertyDescriptors();
			if (hm_Geters == null)
				hm_Geters = new HashMap<String, Method>();
			hm_Geters.clear();
			if (hm_LazyGeters == null)
				hm_LazyGeters = new HashMap<String, Method>();
			hm_LazyGeters.clear();
			if (hm_Seters == null)
				hm_Seters = new HashMap<String, Method>();
			hm_Seters.clear();
			if (hm_LazySeters == null)
				hm_LazySeters = new HashMap<String, Method>();
			hm_LazySeters.clear();
			if (hm_DispNames == null)
				hm_DispNames = new HashMap<String, String>();
			hm_DispNames.clear();
			buildGetterANDSetters(clazz);
		} catch (IntrospectionException e) {
		}
	}

	/**
	 * 设置指定属性的值
	 * @param attName  属性名
	 * @param value  值
	 */
	public void setAttributeValue(String attName, Object value) {
		try {
			Method m = hm_Seters.get(attName);
			if(m == null) m = hm_LazySeters.get(attName);
				m.invoke(bean, new Object[] { value });
		} catch (Exception e) {
		}
	}

	/**
	 * 得到指定属性的值
	 * @param attName  属性名
	 * @return 值
	 */
	public Object getAttributeValue(String attName) {
		Object o = null;
		try {
			Object[] os = null;
			Method m = hm_Geters.get(attName);
			if(m == null) m = hm_LazyGeters.get(attName);
				o = m.invoke(bean, os);
		} catch (Exception e) {
		}
		return o;
	}

	/**
	 * 得到表名称
	 * @return 表名
	 */
	public String getTableName() {
		javax.persistence.Table table = clazz
				.getAnnotation(javax.persistence.Table.class);
		String tablename = null;
		if (table == null) {
			Class clazzp = clazz.getSuperclass();
			while (!clazzp.equals(BaseModel.class)) {
				table = (Table) clazzp
						.getAnnotation(javax.persistence.Table.class);
				if (table != null) {
					tablename = table.name();
					break;
				} else {
					clazzp = clazzp.getSuperclass();
				}
			}
		} else {
			tablename = table.name();
		}
		if (tablename == null || tablename.length() == 0) {
			tablename = clazz.getSimpleName().toUpperCase();
		}
		return tablename;
	}

	private String[] fieldNames;

	/**
	 * 得到实体中所有持久化字段名
	 * @return 字段名称数组
	 */
	public String[] getAttributeNames() {
		if (fieldNames == null) {
			Set<String> set_fieldnames = hm_Geters.keySet();
			fieldNames = new String[0];
			fieldNames = set_fieldnames.toArray(fieldNames);
		}
		return fieldNames;
	}

	/**
	 * 将一个对象String化 <br>
	 * 格式如下： <br>
	 * TABLE_NAME::表名 <br>
	 * 字段名::字段值 字段名::字段值
	 * 
	 * @param bean
	 * @return
	 */
	public String beanToString() {
		StringBuffer sb_tostring = new StringBuffer();
		sb_tostring.append("对象:[").append(getEODisplayName())
				.append("] ");

		String[] fieldnames = getAttributeNames();

		for (String fieldname : fieldnames) {
			Object obj_value = null;
			try {
				obj_value = getAttributeValue(fieldname);
				if (obj_value instanceof Date) {
					obj_value = DateUtil.formatDate((Date) obj_value, null);
				} else if (obj_value instanceof BaseModel) {
					obj_value = ((BaseModel) obj_value).getPrimaryKey();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sb_tostring.append(getFieldDisplayName(fieldname)).append(":[").append(obj_value)
					.append("]\t");
		}

		return sb_tostring.toString();
	}

	/**
	 * 判断当前bean是否与参数对象相同
	 * 
	 * @param obj
	 * @return
	 */
	public boolean equalsBean(Object obj) {
		if (obj == null)// 对象为空不比较
			return false;

		// 不是BaseEO，不必比较
		if (!(obj instanceof BaseModel)) {
			return false;
		}

		// 类型不同不必进行比较
		if (!clazz.equals(obj.getClass())) {
			return false;
		}

		// 依次比较字段值，遇到不同的则返回false
		String[] fieldnames = getAttributeNames();
		for (String fieldname : fieldnames) {
			boolean same = equalsField(fieldname, bean, obj);
			if (!same) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 比较当前对象与另一个对象的差别，并返回值不同的字段的名称。
	 * @param antherBean
	 *            将要比较的对象
	 * @return 值不同的字段名
	 */
	public List<String> getDifferentField(BaseModel anotherBean) {
		// 类型不同不必进行比较
		if (!clazz.equals(anotherBean.getClass())) {
			throw new ClassCastException(anotherBean.getClass().getName()
					+ "Cann't Cast to " + clazz.getName());
		}
		List<String> differents = new ArrayList<String>();
		String[] fieldnames = getAttributeNames();
		for (String fieldname : fieldnames) {
			boolean same = equalsField(fieldname, bean, anotherBean);
			if (!same) {
				differents.add(fieldname);
			}
		}
		return differents;
	}

	/**
	 * 比较两个对象，指定的字段值是否相同
	 * @param fieldName 需要比较的字段
	 * @param obj1  对象1
	 * @param obj2 对象2
	 * @return 值相同则为true
	 */
	private boolean equalsField(String fieldName, Object obj1, Object obj2) {
		try {
			Object obj_value = null;
			Object current_value = null;
			Method getter = hm_Geters.get(fieldName);
			Object[] os = null;
			current_value = getter.invoke(obj1, os);
			obj_value = getter.invoke(obj2, os);

			if (current_value == null && obj_value == null) {
				return true;
			}else if(current_value != null && obj_value != null){
				if(current_value instanceof BaseModel && obj_value instanceof BaseModel){
					return ((BaseModel)current_value).equalsPK(obj_value);
				}
				if(current_value instanceof Date && obj_value instanceof Date){ // 日期类型比较特殊处理
					return DateUtil.equalsDate((Date)current_value, (Date)obj_value);
				}
				
				return obj_value.equals(current_value);
			}else if (current_value != null) {
				return current_value.equals(obj_value);
			} else if (obj_value != null) {
				return obj_value.equals(current_value);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	/**
	 * 返回该对象的哈希码值
	 */
	public int hashCodeBean() {

		// 生成简单的位运算hash散列码
		String key = bean.toString();
		int prime = key.hashCode();
		int hash = prime;
		for (int i = 0; i < key.length(); i++) {
			hash ^= (hash << 23 >> 17) ^ key.charAt(i) * 13131;
		}
		// 返回结果
		return (hash % prime) * 33;
	}

	/**
	 * 利用流深度克隆实体类
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object cloneBean() throws IOException, ClassNotFoundException {
		return cloneObject(bean);
	}

	/**
	 * 取得一个枚举值上的描述注解.
	 * @param emumValue 枚举值
	 * @return 如果传入的不是枚举值，则返回空串,或者枚举没有标注注解,则返回枚举toString.
	 */
	public static String getEnumDescription(Object emumValue) {
		String desValue = "";
		if (emumValue != null) {
			try {
				String enumName = ((Enum) emumValue).name();
				desValue = emumValue.getClass().getField(enumName)
						.getAnnotation(EnumDescription.class).value();
			} catch (Exception e) {
				return emumValue.toString();
			}
		}
		return desValue;
	}

	/**
	 * 实现深clone
	 * @param obj
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object cloneObject(Object obj) throws IOException,
			ClassNotFoundException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(obj);
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		Object cloneObj = (oi.readObject());
		bo.close();
		oo.close();
		bi.close();
		oi.close();
		return cloneObj;
	}
	
	/**
	 * 加载所有延迟加载的字段.
	 */
	void loadLazyField(){
		Iterator<Method> i_mds = hm_LazyGeters.values().iterator();
		while(i_mds.hasNext()){
			Method m = i_mds.next();
			try {
				Object[] os = null;
				Object o = m.invoke(bean, os);
				if(o!=null)
					o.toString();
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}
}
