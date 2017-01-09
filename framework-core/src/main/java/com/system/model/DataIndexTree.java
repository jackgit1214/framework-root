package com.system.model;

import com.framework.common.model.ITreeData;

public class DataIndexTree extends SysIndexitem implements ITreeData {

	private boolean isParent;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3846793378813471605L;

	@Override
	public String getPId() {
		// TODO Auto-generated method stub
		//org.apache.ibatis.type.JdbcType.BOOLEAN
		return this.getSuperid();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getIndexname();
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return super.getId();
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

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.getIndexid();
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean getIsParent(){
		return isParent;
	}

}
