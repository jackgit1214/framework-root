/**
 * 
 */
package com.framework.common.model;


/**
 * @author lilj
 * 建立树模型，与ZTREE树相对应，
 * 
 */

public interface ITreeData  {

	public String getId();

	public String getPId() ;

	public String getName() ;

//	public String getIconOpen() ;

//	public String getIconClose() ;

	public Object getData() ;

	public boolean getIsParent() ;

	public String getUrl() ;
	
	public boolean isChecked();

}
