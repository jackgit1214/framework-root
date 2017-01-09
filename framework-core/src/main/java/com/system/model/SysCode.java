package com.system.model;

import java.io.Serializable;

public class SysCode  implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3015174265002850416L;

	private String codeid;

    private String codetype;

    private String content;

    private String code;

    private String codename;

    private String superid;

    private Short leve;

    private Short orderby;

    private String remark;

    private int applyto;
    
    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }


    public Short getLeve() {
        return leve;
    }

    public void setLeve(Short leve) {
        this.leve = leve;
    }

    public Short getOrderby() {
        return orderby;
    }

    public void setOrderby(Short orderby) {
        this.orderby = orderby;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getSuperid() {
		return superid;
	}

	public void setSuperid(String superid) {
		this.superid = superid;
	}

	public int getApplyto() {
		return applyto;
	}

	public void setApplyto(int applyto) {
		this.applyto = applyto;
	}
    
    
}