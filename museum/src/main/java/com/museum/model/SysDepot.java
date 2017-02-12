package com.museum.model;

import java.io.Serializable;

import com.framework.model.BaseModel;

public class SysDepot extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8630830841123217121L;

	private String depotId;

	private String depotCode;

	private String depotName;

	private String superid;

	private Short sortid;

	private String endflag;

	private String dutyMan;

	private String position;

	private String note;

	private String superName;

	public String getDepotId() {
		return depotId;
	}

	public void setDepotId(String depotId) {
		this.depotId = depotId;
	}

	public String getDepotCode() {
		return depotCode;
	}

	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getSuperid() {
		return superid;
	}

	public void setSuperid(String superid) {
		this.superid = superid;
	}

	public Short getSortid() {
		return sortid;
	}

	public void setSortid(Short sortid) {
		this.sortid = sortid;
	}

	public String getEndflag() {
		return endflag;
	}

	public void setEndflag(String endflag) {
		this.endflag = endflag;
	}

	public String getDutyMan() {
		return dutyMan;
	}

	public void setDutyMan(String dutyMan) {
		this.dutyMan = dutyMan;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getDepotId();
	}

	public String getSuperName() {
		return superName;
	}

	public void setSuperName(String superName) {
		this.superName = superName;
	}
}