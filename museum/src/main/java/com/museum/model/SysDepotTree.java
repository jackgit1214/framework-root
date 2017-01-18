package com.museum.model;

import com.framework.common.model.ITreeData;

public class SysDepotTree extends SysDepot implements ITreeData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2676673129373317392L;

	private boolean isParent;
	
	@Override
	public String getId() {
		return this.getDepotId();
	}

	@Override
	public String getPId() {
		// TODO Auto-generated method stub
		return this.getSuperid();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getDepotName();
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return this.getDepotId();
	}

	@Override
	public boolean getIsParent() {
		// TODO Auto-generated method stub
		return false;
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
