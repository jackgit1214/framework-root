package com.framework.activiti.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ActivitiAopService extends ActivitiBaseService {

    /**
     * 
     * taskDelegate:(委派任务). <br/>
     * TODO(设置任务代理人.<br/>
     * 
     * @author lilj
     * @since JDK 1.6
     */
    @Pointcut(value = "target(com.activiti.listener.IProcessInstanceExpandForm) &&  execution(* setTaskDelegate*(..))")
    private void taskDelegate() {

    }

    @Around(value = "taskDelegate()")
    public Object setTaskDelegate(ProceedingJoinPoint joinPoint) throws Throwable {
	Object[] params = joinPoint.getArgs();
	if (params.length < 3)
	    throw new Exception("流程任务参数错误！必须指定流程实例，原办理人，新办理人");
	String processInstanceId = (String) params[0];
	String ori_personId = (String) params[1];
	String new_personId = (String) params[2];

	Task task = this.taskService.createTaskQuery().processInstanceId(processInstanceId)
		.taskCandidateOrAssigned(ori_personId).singleResult();
	this.taskService.delegateTask(task.getId(), new_personId);
	return joinPoint.proceed();
    }

    /**
     * 
     * taskResolve:(委派任务，处理). <br/>
     * TODO(设置任务代理人.<br/>
     * 
     * @author lilj
     * @since JDK 1.6
     */
    @Pointcut(value = "target(com.activiti.listener.IProcessInstanceExpandForm) &&  execution(* resolveTask*(..))")
    private void taskResolve() {

    }

    @Around(value = "taskResolve()")
    public Object resolveTask(ProceedingJoinPoint joinPoint) throws Throwable {

	Object[] params = joinPoint.getArgs();
	if (params.length < 2)
	    throw new Exception("流程任务参数错误！必须指定流程实例，办理人");
	String processInstanceId = (String) params[0];
	String personId = (String) params[1];
	Task task = this.taskService.createTaskQuery().processInstanceId(processInstanceId)
		.taskCandidateOrAssigned(personId).singleResult();

	this.taskService.resolveTask(task.getId());
	return joinPoint.proceed();
    }

    /**
     * 
     * taskAssignee:(任务转办). <br/>
     * TODO(设置任务办理人.<br/>
     * 
     * @author lilj
     * @since JDK 1.6
     */
    @Pointcut(value = "target(com.activiti.listener.IProcessInstanceExpandForm) &&  execution(* setTaskAssignee*(..))")
    private void taskAssignee() {

    }

    @Around(value = "taskAssignee()")
    public Object setTaskAssignee(ProceedingJoinPoint joinPoint) throws Throwable {

	Object[] params = joinPoint.getArgs();
	if (params.length < 3)
	    throw new RuntimeException("流程任务参数错误！必须指定流程实例，原办理人，新办理人");
	String processInstanceId = (String) params[0];
	String personId = (String) params[1];
	String new_personId = (String) params[2];
	Task task = this.taskService.createTaskQuery().processInstanceId(processInstanceId)
		.taskCandidateOrAssigned(personId).singleResult();

	this.taskService.setAssignee(task.getId(), new_personId);
	return joinPoint.proceed();
    }

    // 取得任务列表，根据指定人
    @Pointcut(value = "target(com.activiti.listener.IProcessInstanceForm) &&  execution(* getTaskList*(..))")
    private void taskList() {

    }

    @Around(value = "taskList()")
    public Object getTaskList(ProceedingJoinPoint joinPoint) throws Throwable {

	Object[] params = joinPoint.getArgs();
	if (params.length < 2)
	    throw new RuntimeException("流程任务参数错误！");
	String processKey = (String) params[0];
	String personId = (String) params[1];
	List<Task> tasks = new ArrayList<Task>();
	IProcessInstanceForm businessServiceImpl = (IProcessInstanceForm) joinPoint.getTarget();

	// 根据当前人的ID查询
	List<Task> todoList = this.taskService.createTaskQuery().processDefinitionKey(processKey).taskAssignee(personId)
		.list();
	// 根据当前人未签收的任务
	List<Task> unsignedTasks = this.taskService.createTaskQuery().processDefinitionKey(processKey)
		.taskCandidateUser(personId).list();
	// 合并
	tasks.addAll(todoList);
	tasks.addAll(unsignedTasks);
	List<Object> results = new ArrayList<>();
	// 根据流程的业务ID查询实体并关联
	joinPoint.proceed();
	for (Task task : tasks) {
	    String processInstanceId = task.getProcessInstanceId();
	    this.log.debug(processInstanceId);
	    ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
		    .processInstanceId(processInstanceId).singleResult();
	    String businessKey = processInstance.getBusinessKey();
	    Object object = businessServiceImpl.getBusinessDataById(businessKey);
	    this.log.debug(object);
	    results.add(object);
	}
	return results;
    }

    /**
     * 
     * startProcessInstance:(开始流程). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @since JDK 1.6
     */
    @Pointcut(value = "target(com.activiti.listener.IProcessInstanceForm) && execution(* startProcessInstance(..))")
    private void startProcessInstance() {

    }

    @Around(value = "startProcessInstance()")
    public Object startProcess(ProceedingJoinPoint joinPoint) throws Throwable {

	this.log.debug("---------Processing execute------------------");
	Object[] params = joinPoint.getArgs();
	if (params.length < 2)
	    throw new RuntimeException("流程开始参数错误");
	String processKey = (String) params[0];
	String formId = (String) params[1];
	ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(processKey, formId);
	IProcessInstanceForm businessServiceImpl = (IProcessInstanceForm) joinPoint.getTarget();
	businessServiceImpl.fillFormInstanceId(processInstance.getId(), formId);

	Object rtnObj = joinPoint.proceed();
	rtnObj = processInstance.getId();
	return rtnObj;

    }

    // 流程任务处理，即完成任务
    @Pointcut(value = "target(com.activiti.listener.IProcessInstanceForm) &&  execution(* handleTask(..))")
    private void handleTask() {

    }

    @AfterReturning(returning = "rtnMap", pointcut = "handleTask()")
    public Object completeTask(JoinPoint joinPoint, Object rtnMap) {
	this.log.debug("--------------------complete task------------------------");
	Object[] params = joinPoint.getArgs();
	if (params.length < 2)
	    throw new RuntimeException("流程任务参数错误！");
	if (!(rtnMap instanceof Map))
	    throw new RuntimeException("返回参数类型不对，需要返回Map");

	String processInstanceId = (String) params[0];
	String personId = (String) params[1];

	@SuppressWarnings("unchecked")
	Map<String, Object> mapParam = (Map<String, Object>) rtnMap;

	Task task = this.taskService.createTaskQuery().processInstanceId(processInstanceId)
		.taskCandidateOrAssigned(personId).singleResult();

	this.log.debug("the name of Task is " + task.getName() + ",and owner is " + task.getAssignee());
	this.taskService.complete(task.getId(), mapParam);
	return rtnMap;
    }

}
