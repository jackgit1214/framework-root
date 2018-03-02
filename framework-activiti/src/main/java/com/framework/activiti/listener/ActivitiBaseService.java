package com.framework.activiti.listener;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ActivitiBaseService {

    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected ProcessEngineFactoryBean factoryBean;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected FormService formService;

    @Autowired
    protected IdentityService identityService;

    @Autowired
    protected HistoryService historyService;

    /**
     * 
     */
    private static final long serialVersionUID = -4346755598990005840L;

}
