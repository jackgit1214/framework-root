package com.system.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.framework.mybatis.service.IBusinessService;
import com.system.model.SysCode;
import com.system.model.SysCodeTree;

@Service
public interface ISysCodeService extends IBusinessService<SysCode> {

	public int saveCode(SysCode sysCode);

	public int delete(String[] ids);

	public int delete(String id);

	List<SysCodeTree> getCodeDataByCodeid(String codeid, String codetype);

	List<SysCode> getCodeData(String[] codeTypes);
}
