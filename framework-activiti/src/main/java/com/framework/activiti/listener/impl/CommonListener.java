package com.framework.activiti.listener.impl;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.framework.activiti.listener.ActivitiBaseService;
import com.framework.activiti.listener.ICommonListener;
import com.framework.activiti.listener.IListenerService;

@Component
public class CommonListener extends ActivitiBaseService implements ICommonListener {

    @Autowired
    private IListenerService listenerServiceImpl;

    public void notify(DelegateTask delegateTask) {
	String eventName = delegateTask.getEventName();

	List<String> userList = delegateTask.getVariable(this.USERLISTS, List.class);

	if (TaskListener.EVENTNAME_CREATE.equals(eventName)) {
	    this.log.debug("execute 创建监听器");
	    // 指定用户时，直接取用户信息
	    if (userList != null && userList instanceof List) {
		this.notifyCreate(delegateTask, userList);
	    } else
		this.notifyCreate(delegateTask);
	} else if (TaskListener.EVENTNAME_ASSIGNMENT.equals(eventName)) {
	    this.log.debug("execute 分派监听器");
	    this.notifyAssignment(delegateTask);
	} else if (TaskListener.EVENTNAME_COMPLETE.equals(eventName)) {
	    this.log.debug("execute 任务完成监听器");
	    this.notifyComplete(delegateTask);
	} else if (TaskListener.EVENTNAME_DELETE.equals(eventName)) {
	    this.log.debug("execute 删除监听器");
	    this.notifyDelete(delegateTask);
	}
    }

    /**
     * 
     */
    private static final long serialVersionUID = -1L;

    public void notifyCreate(DelegateTask delegateTask, List<String> userDatas) {

	for (String userId : userDatas) {
	    delegateTask.addCandidateUser(userId);
	}
    }

    public void notifyCreate(DelegateTask delegateTask) {

	String processDefId = delegateTask.getProcessDefinitionId();

	ProcessDefinition processDefinition = this.repositoryService.getProcessDefinition(processDefId);

	String processDefKey = processDefinition.getKey();
	String taskId = delegateTask.getTaskDefinitionKey();
	String processInstanceId = delegateTask.getProcessInstanceId();

	// 根据节点id,取得节点上的审批人
	List<String> userIds = listenerServiceImpl.getAllocatePersonByNodeId(taskId, processDefKey, processInstanceId);
	for (String userId : userIds) {
	    delegateTask.addCandidateUser(userId);
	}
    }

    public void notifyAssignment(DelegateTask delegateTask) {
	this.log.debug("===============" + delegateTask.getName() + "==========================");
    }

    public void notifyComplete(DelegateTask delegateTask) {
	// Object var = delegateTask.getVariable("input");
	this.log.debug("===============" + delegateTask.getName() + "==========================");
    }

    public void notifyDelete(DelegateTask delegateTask) {
	this.log.debug("==================" + delegateTask.getName() + "=======================");
    }

}
