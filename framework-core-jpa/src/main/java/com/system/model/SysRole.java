package com.system.model;

import java.io.Serializable;

public class SysRole  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3740685907374382736L;

	private String roleid;

    private String rolename;

    private String roledesc;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    
}