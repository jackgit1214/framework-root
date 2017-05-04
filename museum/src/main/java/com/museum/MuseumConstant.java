package com.museum;

import com.system.common.SysConstant;

public interface MuseumConstant extends SysConstant {

	// 存储文件分类方法
	public static int CLASSIFIEDTYPE_DATATYPE = 0; // 按数据分类
	public static int CLASSIFIEDTYPE_FILETYPE = 1; // 按文件类型分类

	// 业务类型分类
	public static int BUSINESSTYPE_COLLECT_CLUE = 1; // 征集线索
	public static int BUSINESSTYPE_COLLECT = 2; // 征集
	public static int BUSINESSTYPE_REPAIR = 3; // 修复
	public static int BUSINESSTYPE_AUTHENTICATE = 4; // 鉴定

	public static int BUSINESSTYPE_CULTRUE_RELIC = 5; // 藏口

	public static int BUSINESSTYPE_EXHIBIT = 6; // 陈列

}
