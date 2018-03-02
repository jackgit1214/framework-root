package com.system.model;

import java.io.Serializable;

import com.framework.model.TreeData;

public class SysModule implements Serializable, TreeData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9197444090286906221L;

	private String funcid;

	private String funcname;

	private int funtype;

	private String moddesc;

	private int isinuse;

	private String supermodid;

	private String funicon;

	private int funorder;

	private int system;

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

	public String getModdesc() {
		return moddesc;
	}

	public void setModdesc(String moddesc) {
		this.moddesc = moddesc;
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
		if (1 == this.getSystem())
			return true;
		else
			return false;
	}

	@Override
	public boolean getIsParent() {
		// TODO Auto-generated method stub
		return true;
	}

	public int getFuntype() {
		return funtype;
	}

	public void setFuntype(int funtype) {
		this.funtype = funtype;
	}

	public int getIsinuse() {
		return isinuse;
	}

	public void setIsinuse(int isinuse) {
		this.isinuse = isinuse;
	}

	public int getFunorder() {
		return funorder;
	}

	public void setFunorder(int funorder) {
		this.funorder = funorder;
	}

	public int getSystem() {
		return system;
	}

	public void setSystem(int system) {
		this.system = system;
	}

}