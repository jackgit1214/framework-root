package com.system.model;

import java.util.List;

import com.framework.common.model.ITreeData;

public class SysCodeTree extends SysCode implements ITreeData {

	private boolean isParent;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3865291059393520985L;
	
	@Override
	public String getId() {

		return this.getCode();
	}

	@Override
	public String getPId() {
		return this.getSuperid();
	}

	@Override
	public String getName() {
		return this.getCodename();
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return this.getCodeid();
	}

	@Override
	public boolean getIsParent() {
		// TODO Auto-generated method stub
		return isParent;
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
