package com.system.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.framework.model.BaseModel;


@Entity
@Table(name = "sys_dept")
public class SysDept extends BaseModel  implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3021406553407662010L;
	

	private String deptid;

    private String deptName;

    private String superid;

    private Short sortid;

    private Byte endflag;

    private String deptDesc;

    
	@Id
	@Column(name = "deptid", unique = true, nullable = false, length = 40)
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

	@Column(name = "dept_Name", nullable = false, length = 50)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

	@Column(name = "superid", nullable = true, length = 40)    
    public String getSuperid() {
        return superid;
    }

    public void setSuperid(String superid) {
        this.superid = superid;
    }

	@Column(name = "sortid",  length = 2)        
    public Short getSortid() {
        return sortid;
    }

    public void setSortid(Short sortid) {
        this.sortid = sortid;
    }

	@Column(name = "endflag",  length = 2)   
    public Byte getEndflag() {
        return endflag;
    }

    public void setEndflag(Byte endflag) {
        this.endflag = endflag;
    }

    @Column(name = "dept_Desc", nullable = true, length = 2000)
    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

	@Override
	@Transient
	public Object getPrimaryKey() {
		return this.getDeptid();
		// TODO Auto-generated method stub
		//return null;
	}


}