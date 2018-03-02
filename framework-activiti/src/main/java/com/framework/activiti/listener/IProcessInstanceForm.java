package com.framework.activiti.listener;

import java.util.Map;

public interface IProcessInstanceForm {

    Object getBusinessDataById(String id);

    /**
     * 
     * getCurrentUserDeptId:当前业务单据创建人的申报人的所在部门. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param processInstanceId
     *            流程实例ID
     * @return 部门ID
     * @since JDK 1.6
     */

    String getCurrentUserDeptId(String processInstanceId);

    /**
     * getBusinessType:当前业务单据的业务类型. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param processInstanceId
     *            流程实例ID
     * @return 业务类型
     * @since JDK 1.6
     */
    String getBusinessType(String processInstanceId);

    Map<String, Object> handleTask(String processInstanceId, String personId, Map<String, Object> map);

    /**
     * 
     * startProcessInstance:(流程启动). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param processKey
     *            流程的key
     * @param formId
     *            表单的id
     * @return 流程实例
     * @since JDK 1.6
     */
    String startProcessInstance(String processKey, String formId);

    void fillFormInstanceId(String processInstanceId, String formId);

}
