package com.system.model;

import java.io.Serializable;
import java.util.Date;

import com.framework.web.util.SessionUser;

public class SysUser implements SessionUser, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3671175425228288047L;

	private String userid;

	private String logincode;

	private Byte valid;

	private String username;

	private String password;

	private Byte gender;

	private Byte age;

	private Date birthday;

	private byte[] avatar;

	private String qq;

	private String email;

	private String address;

	private String phone;

	private Byte userorder;

	private Byte userstatus;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
		this.setId(userid);
		this.setpId(userid);
	}

	public String getLogincode() {
		return logincode;
	}

	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		this.setName(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	public Byte getAge() {
		return age;
	}

	public void setAge(Byte age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Byte getUserorder() {
		return userorder;
	}

	public void setUserorder(Byte userorder) {
		this.userorder = userorder;
	}

	public Byte getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(Byte userstatus) {
		this.userstatus = userstatus;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	// 以下属性为树中展示使用
	private String id;

	private String pId;

	private String name;

	public String getId() {
		return this.userid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPId() {
		return this.userid;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return this.username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsParent() {
		// TODO Auto-generated method stub
		return false;
	}

}