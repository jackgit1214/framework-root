package com.museum.model;

import java.io.Serializable;

import com.framework.model.BaseModel;

public class CollClue extends BaseModel implements Serializable {
	private String clueid;

	private String name;

	private String sources;

	private String contacts;

	private String phone;

	private String address;

	private String email;

	private String qq;

	private String sourcetype;

	private String status;

	private String collCollid;

	private String unit;

	private String remark;

	private static final long serialVersionUID = 1L;

	public String getClueid() {
		return clueid;
	}

	public void setClueid(String clueid) {
		this.clueid = clueid == null ? null : clueid.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources == null ? null : sources.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq == null ? null : qq.trim();
	}

	public String getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype == null ? null : sourcetype.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getCollCollid() {
		return collCollid;
	}

	public void setCollCollid(String collCollid) {
		this.collCollid = collCollid == null ? null : collCollid.trim();
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	@Override
	public Object getPrimaryKey() {
		return this.getClueid();
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
}