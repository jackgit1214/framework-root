package com.framework.activiti.listener;

import java.util.Map;

/**
 * 增加两个接口</br>
 * 1、任务委派接口 </br>
 * 2、委派任务处理接口 </br>
 * 3、任务转发接口
 * 
 * @author lilj
 *
 */
public interface IProcessInstanceExpandForm extends IProcessInstanceForm {

    /**
     * 
     * delegateTask: 委派任务，指定新人进行任务的处理. <br/>
     * TODO(被委派人，解决处理任务后，任务回归原来处理人员).<br/>
     * 
     * @author lilj
     * @param instanceId
     * @param oriUserId
     * @param newUserId
     * @since JDK 1.6
     */
    void delegateTask(String instanceId, String oriUserId, String[] newUserId);

    /**
     * 
     * resolveTask:委派任务的解决，解决后任务将回归原有任务人. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param instanceId
     * @param userId
     * @param variables
     * @since JDK 1.6
     */
    void resolveTask(String instanceId, String userId, Map<String, Object> variables);

    /**
     * 
     * assigneeTask:转发任务，转发后任务将不属于自己. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param instanceId
     * @param oriUserId
     * @param newUserId
     * @since JDK 1.6
     */
    void assigneeTask(String instanceId, String oriUserId, String newUserId);

}
