package com.system.model;

import java.util.Date;

public class SysLog {
	
    private String logid;

    private Date logtime;

    private String userid;

    private String requestip;

    private String requestmethod;

    private String reqparam;

    private String modelname;

    private String modelopertype;

    private Integer logtype;

    private String logdescription;

    private String excecode;

    private String excedescription;

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRequestip() {
        return requestip;
    }

    public void setRequestip(String requestip) {
        this.requestip = requestip;
    }

    public String getRequestmethod() {
        return requestmethod;
    }

    public void setRequestmethod(String requestmethod) {
        this.requestmethod = requestmethod;
    }

    public String getReqparam() {
        return reqparam;
    }

    public void setReqparam(String reqparam) {
        this.reqparam = reqparam;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getModelopertype() {
        return modelopertype;
    }

    public void setModelopertype(String modelopertype) {
        this.modelopertype = modelopertype;
    }

    public Integer getLogtype() {
        return logtype;
    }

    public void setLogtype(Integer logtype) {
        this.logtype = logtype;
    }

    public String getLogdescription() {
        return logdescription;
    }

    public void setLogdescription(String logdescription) {
        this.logdescription = logdescription;
    }

    public String getExcecode() {
        return excecode;
    }

    public void setExcecode(String excecode) {
        this.excecode = excecode;
    }

    public String getExcedescription() {
        return excedescription;
    }

    public void setExcedescription(String excedescription) {
        this.excedescription = excedescription;
    }
    
}