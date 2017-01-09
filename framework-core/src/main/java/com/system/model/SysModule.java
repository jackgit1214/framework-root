package com.system.model;

import java.io.Serializable;

import com.framework.common.model.ITreeData;

public class SysModule implements Serializable,ITreeData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9197444090286906221L;

	private String funcid;

	private String funcname;

	private Byte funtype;

	private String moddesc;

	private Byte isinuse;

	private String supermodid;

	private String funicon;

	private Byte funorder;

	private Byte system;

	private String targetDiv;

	private String urllink;

	public String getFuncid() {
		return funcid;
	}

	public void setFuncid(String funcid) {
		this.funcid = funcid;
	}

	public String getFuncname() {
		return funcname;
	}

	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}

	public Byte getFuntype() {
		return funtype;
	}

	public void setFuntype(Byte funtype) {
		this.funtype = funtype;
	}

	public String getModdesc() {
		return moddesc;
	}

	public void setModdesc(String moddesc) {
		this.moddesc = moddesc;
	}

	public Byte getIsinuse() {
		return isinuse;
	}

	public void setIsinuse(Byte isinuse) {
		this.isinuse = isinuse;
	}

	public String getSupermodid() {
		return supermodid;
	}

	public void setSupermodid(String supermodid) {
		this.supermodid = supermodid;
	}

	public String getFunicon() {
		return funicon;
	}

	public void setFunicon(String funicon) {
		this.funicon = funicon;
	}

	public Byte getFunorder() {
		return funorder;
	}

	public void setFunorder(Byte funorder) {
		this.funorder = funorder;
	}

	public Byte getSystem() {
		return system;
	}

	public void setSystem(Byte system) {
		this.system = system;
	}

	public String getTargetDiv() {
		return targetDiv;
	}

	public void setTargetDiv(String targetDiv) {
		this.targetDiv = targetDiv;
	}

	public String getUrllink() {
		return urllink;
	}

	public void setUrllink(String urllink) {
		this.urllink = urllink;
	}


	public String getId() {
	
		return this.getFuncid();
	}

	public String getPId() {
		// TODO Auto-generated method stub
		return this.getSupermodid();
	}



	public String getName() {
		// TODO Auto-generated method stub
		return this.getFuncname();
	}


	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getUrl() {
		// TODO Auto-generated method stub
		return this.getUrllink();
	}

	@Override
	public boolean isChecked() {
		// 这里用system字段，标明树节点是否选中
		if (1==this.getSystem())
			return true;
		else
			return false;
	}

	@Override
	public boolean getIsParent() {
		// TODO Auto-generated method stub
		return true;
	}	
}