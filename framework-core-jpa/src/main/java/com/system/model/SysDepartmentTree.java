package com.system.model;

import com.framework.model.TreeData;

public class SysDepartmentTree extends SysDept implements TreeData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isParent;

	@Override
	public String getId() {
		return this.getDeptid();
	}

	@Override
	public String getPId() {
		// TODO Auto-generated method stub
		return this.getSuperid();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getDeptName();
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return this.getDeptid();
	}

	@Override
	public boolean getIsParent() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

}
