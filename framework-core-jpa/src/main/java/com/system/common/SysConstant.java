package com.system.common;

public interface SysConstant {

	public static String INDEX_SIGN = "index"; // 首页存储标志
	public static String FIRST_LINK_SIGN = "FIRST"; // 一级连接存储标志
	public static String SECOUND_LINK_SIGN = "SECOUND"; // 二级连接存储标志

	/** 系统缺省管理员 */
	public static final String SYSDEFAULTMANAGER = "admin";

	/** 系统根菜单缺省值 */
	public static final String SYSDEFAULTROOTSUPPERID = "0";

	/** 系统缺省口令 */
	public static final String SYSDEFAULTPASSWORD = "123456";

	/** 系统缺省显示行数 */
	public static final int SYSDEFAULTROWNUM = 10; // 暂时测试行数

	/* 系统常用分隔常量* */
	public static final String SYSSEPARATOR = "SYSTEM_SEPARATOR";

	// 单个文件大小允许上传为10M
	public static long SINGLEFILESIZE = 1024 * 1024 * 10;

	// 总文件大小为100M
	public static long TOTALFILESIZE = 1024 * 1024 * 100;

	// 文件存储路径
	public static String filePath = "/upload/";

	// 存储文件分类方法
	public static int CLASSIFIEDTYPE_DATATYPE = 0; // 按数据分类
	public static int CLASSIFIEDTYPE_FILETYPE = 1; // 按文件类型分类

}
