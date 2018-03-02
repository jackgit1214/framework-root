package com.framework.activiti.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.framework.activiti.listener.IListenerService;
import com.framework.activiti.listener.IProcessInstanceForm;
import com.framework.activiti.model.WFBusiNodePerson;
import com.framework.activiti.model.WfProcessNodeSetup;
import com.framework.activiti.service.WFBusiNodePersonService;
import com.framework.activiti.service.WfProcessNodeSetupService;
import com.framework.mybatis.model.QueryModel;

@Component("listenerServiceImpl")
public class ListenerServiceImpl implements IListenerService {

    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    @Qualifier("WFBusiNodePersonServiceImpl")
    private WFBusiNodePersonService wFBusiNodePersonServiceImpl;

    @Autowired
    private WfProcessNodeSetupService wfProcessNodeSetupServiceImpl;

    @Autowired
    @Qualifier("wfProcessInstanceFormServiceImpl")
    private IProcessInstanceForm iProcessInstanceForm;

    @Override
    public List<String> getAllocatePersonByNodeId(String nodeId, String processDefId, String processInstanceId) {

	QueryModel queryModel = new QueryModel();
	queryModel.createCriteria().andEqualTo("process_Def_Id", processDefId).andEqualTo("node_Id", nodeId);

	try {
	    WfProcessNodeSetup wfProcessNodeSetup = this.wfProcessNodeSetupServiceImpl.singleObject(queryModel);
	    String nodeType = wfProcessNodeSetup.getNodeType();
	    if (nodeType.equals("0")) { // 节点的上级人，不需要指定部门或业务
		return this.getUserByNodeIdAndType(nodeId, processDefId);
	    } else { // 部门的上级，需要指定部门,即当前业务单据申请人的，上级领导人，以及业务的方式
		return this.getUserByNodeIdAndType(nodeId, processDefId, processInstanceId, nodeType);
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return null;
    }

    private List<String> getUserByNodeIdAndType(String nodeId, String processDefId) {

	QueryModel queryModel = new QueryModel();

	queryModel.createCriteria().andEqualTo("PROCESSDEFID", processDefId).andEqualTo("NODEID", nodeId);
	List<WFBusiNodePerson> datas = this.wFBusiNodePersonServiceImpl.findObjects(queryModel);
	List<String> userIds = new ArrayList<>();
	for (WFBusiNodePerson data : datas) {
	    userIds.add(data.getPersonid());
	}
	return userIds;
    }

    /**
     * 
     * getUserByNodeIdAndType1:取节点及业务单据申请人的，所在部门的上级审批人. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * 
     * @author lilj
     * @param nodeId
     * @param processDefId
     * @return
     * @since JDK 1.6
     */
    private List<String> getUserByNodeIdAndType(String nodeId, String processDefId, String processInstanceId,
	    String nodeType) {
	QueryModel queryModel = new QueryModel();
	QueryModel.Criteria criteria = queryModel.createCriteria();
	criteria.andEqualTo("PROCESSDEFID", processDefId).andEqualTo("NODEID", nodeId);
	if ("1".equals(nodeType)) {
	    String deptId = iProcessInstanceForm.getCurrentUserDeptId(processInstanceId);
	    criteria.andEqualTo("DEPTID", deptId);
	} else if ("2".equals(nodeType)) {
	    String bussType = iProcessInstanceForm.getBusinessType(processInstanceId);
	    criteria.andEqualTo("FORBUSINESS", bussType);
	} else {
	    String deptId = iProcessInstanceForm.getCurrentUserDeptId(processInstanceId);
	    String bussType = iProcessInstanceForm.getBusinessType(processInstanceId);
	    criteria.andEqualTo("DEPTID", deptId).andEqualTo("FORBUSINESS", bussType);
	}

	List<WFBusiNodePerson> datas = this.wFBusiNodePersonServiceImpl.findObjects(queryModel);
	List<String> userIds = new ArrayList<>();
	for (WFBusiNodePerson data : datas) {
	    userIds.add(data.getPersonid());
	}
	return userIds;
    }

    @Override
    public void afterAllocatePerson() {
	// TODO Auto-generated method stub

    }

    @Override
    public void afterDeleteTask() {
	// TODO Auto-generated method stub

    }

}
