package com.framework.activiti.listener;

import java.util.List;

public interface IListenerService {

    /**
     * 
     * allocatePersonByNodeId：根据节点ID，为节点分配人员. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param nodeId
     *            流程定义中的任务ID
     * @since JDK 1.6
     */
    public List<String> getAllocatePersonByNodeId(String nodeId, String processDefId, String processInstanceId);

    public void afterAllocatePerson();

    public void afterDeleteTask();

}
