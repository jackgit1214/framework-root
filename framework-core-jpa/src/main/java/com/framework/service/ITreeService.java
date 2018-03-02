package com.framework.service;

import java.util.List;

import com.framework.model.TreeData;

public interface ITreeService {

	public List<TreeData> getTreeData();

	public List<TreeData> getTreeData(String superid);

}
