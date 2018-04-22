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

    public static String CULINDEXITEMROOT = "ABC";
    //

    public static String CURRENT_WEBSOCKET_USER = "CURRENT_WEBSOCKET_USER";

    public static String CURRENT_SESSIONMANAGER = "sessionManager";

    // 消息定阅及发送相关常量

    public final static String WEBSOCKREGISTRYPATH = "/message";
    public final static String WEBSOCK_STOMP_REGISTRYPATH = "/submsg";

    public final static String WEBSOCK_STOMP_CLIENT_ACCEPT_PATH_PREFIXES = "/msg";

    public final static String WEBSOCK_STOPM_CLIEN_SEND_PREFIXES = "/app";

    // 根据用户不同，其后添加用户id,为指定用户消息 ，表示消息数量变化
    public final static String WEBSOCK_STOMP_CLIENT_ACCEPT_MSGNUM_PATH = "/msgnum";

    // 变化类型，增加还是减少
    public enum CHANGETYPE {
	ADD, DEL
    }

    // actual time

    // 业务固定指标
    public static String BUSINESSINDEXA0102 = "A0102"; // 藏品名称
    public static String BUSINESSINDEXA0211 = "A0211"; // 藏品类型
    public static String BUSINESSINDEXB0401 = "B0401"; // 藏品级别
}
