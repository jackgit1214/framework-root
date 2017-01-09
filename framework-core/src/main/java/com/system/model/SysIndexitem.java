package com.system.model;

import java.io.Serializable;


public class SysIndexitem  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8894630589820300023L;

	private String indexid;

    private String indexname;

    private String idatatype;

    private String iseltype;

    private String endflag;

    private String superid;

    private String sortid;

    private String isSingleLevel;

    private String iscore;

    private String isrequired;

    private Integer indexwidth;

    private String dataformat;

    private String breviary;

    private String remark;

    private String id;
    
    public String getIndexid() {
        return indexid;
    }

    public void setIndexid(String indexid) {
        this.indexid = indexid;
    }

    public String getIndexname() {
        return indexname;
    }

    public void setIndexname(String indexname) {
        this.indexname = indexname;
    }

    public String getIdatatype() {
        return idatatype;
    }

    public void setIdatatype(String idatatype) {
        this.idatatype = idatatype;
    }

    public String getIseltype() {
        return iseltype;
    }

    public void setIseltype(String iseltype) {
        this.iseltype = iseltype;
    }

    public String getEndflag() {
        return endflag;
    }

    public void setEndflag(String endflag) {
        this.endflag = endflag;
    }

    public String getSuperid() {
        return superid;
    }

    public void setSuperid(String superid) {
        this.superid = superid;
    }

    public String getSortid() {
        return sortid;
    }

    public void setSortid(String sortid) {
        this.sortid = sortid;
    }

    public String getIsSingleLevel() {
        return isSingleLevel;
    }

    public void setIsSingleLevel(String isSingleLevel) {
        this.isSingleLevel = isSingleLevel;
    }

    public String getIscore() {
        return iscore;
    }

    public void setIscore(String iscore) {
        this.iscore = iscore;
    }

    public String getIsrequired() {
        return isrequired;
    }

    public void setIsrequired(String isrequired) {
        this.isrequired = isrequired;
    }

    public Integer getIndexwidth() {
        return indexwidth;
    }

    public void setIndexwidth(Integer indexwidth) {
        this.indexwidth = indexwidth;
    }

    public String getDataformat() {
        return dataformat;
    }

    public void setDataformat(String dataformat) {
        this.dataformat = dataformat;
    }

    public String getBreviary() {
        return breviary;
    }

    public void setBreviary(String breviary) {
        this.breviary = breviary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}