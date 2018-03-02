package com.system.model;

import java.io.Serializable;

public class SysDeptUser  implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6815614959276131996L;

	private String id;

    private String deptid;

    private String userid;

    private String updatingUid;

    private String updatingDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUpdatingUid() {
        return updatingUid;
    }

    public void setUpdatingUid(String updatingUid) {
        this.updatingUid = updatingUid;
    }

    public String getUpdatingDate() {
        return updatingDate;
    }

    public void setUpdatingDate(String updatingDate) {
        this.updatingDate = updatingDate;
    }


}