package com.museum.model;

import java.io.Serializable;

import com.framework.model.BaseModel;
import com.system.model.SysIndexitem;

/**
 *
 * This class was generated by MyBatis Generator. This class corresponds to the
 * database table cul_indexdetaildata
 *
 * @mbg.generated do_not_delete_during_merge Sun Apr 01 10:45:24 CST 2018
 */
public class CulIndexDetailData extends BaseModel implements Serializable {
    /**
     * Database Column Remarks: dataDetailId
     *
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column cul_indexdetaildata.dataDetailId
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private String datadetailid;

    private String id;

    /**
     * Database Column Remarks: 文物id
     *
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column cul_indexdetaildata.CULID
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private String culid;

    /**
     * Database Column Remarks: 文物指标ID
     *
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column cul_indexdetaildata.CULINDEXID
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private String culindexid;

    /**
     * 指标名称
     */
    private String indexName;

    private String indexId;

    /**
     * Database Column Remarks: 指标数据值
     *
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column cul_indexdetaildata.normalData
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private String normaldata;

    /**
     * Database Column Remarks: 1、正常文本数据 2、选择类型数据，此时culc01存储选择代码值，typename存储代码
     * 3、大文本数据，此时存储cult01,
     *
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column cul_indexdetaildata.dataType
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private String datatype;

    /**
     * Database Column Remarks: 选择类型代码值
     *
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column cul_indexdetaildata.selTypeValue
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private String seltypevalue;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database table cul_indexdetaildata
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private static final long serialVersionUID = 1L;

    private SysIndexitem sysIndexitem;

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column cul_indexdetaildata.dataDetailId
     *
     * @return the value of cul_indexdetaildata.dataDetailId
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public String getDatadetailid() {
	return datadetailid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column cul_indexdetaildata.dataDetailId
     *
     * @param datadetailid
     *            the value for cul_indexdetaildata.dataDetailId
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public void setDatadetailid(String datadetailid) {
	this.datadetailid = datadetailid == null ? null : datadetailid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column cul_indexdetaildata.CULID
     *
     * @return the value of cul_indexdetaildata.CULID
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public String getCulid() {
	return culid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column cul_indexdetaildata.CULID
     *
     * @param culid
     *            the value for cul_indexdetaildata.CULID
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public void setCulid(String culid) {
	this.culid = culid == null ? null : culid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column cul_indexdetaildata.CULINDEXID
     *
     * @return the value of cul_indexdetaildata.CULINDEXID
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public String getCulindexid() {
	return culindexid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column cul_indexdetaildata.CULINDEXID
     *
     * @param culindexid
     *            the value for cul_indexdetaildata.CULINDEXID
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public void setCulindexid(String culindexid) {
	this.culindexid = culindexid == null ? null : culindexid.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column cul_indexdetaildata.normalData
     *
     * @return the value of cul_indexdetaildata.normalData
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public String getNormaldata() {
	return normaldata;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column cul_indexdetaildata.normalData
     *
     * @param normaldata
     *            the value for cul_indexdetaildata.normalData
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public void setNormaldata(String normaldata) {
	this.normaldata = normaldata == null ? null : normaldata.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column cul_indexdetaildata.dataType
     *
     * @return the value of cul_indexdetaildata.dataType
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public String getDatatype() {
	return datatype;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column cul_indexdetaildata.dataType
     *
     * @param datatype
     *            the value for cul_indexdetaildata.dataType
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public void setDatatype(String datatype) {
	this.datatype = datatype == null ? null : datatype.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column cul_indexdetaildata.selTypeValue
     *
     * @return the value of cul_indexdetaildata.selTypeValue
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public String getSeltypevalue() {
	return seltypevalue;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column cul_indexdetaildata.selTypeValue
     *
     * @param seltypevalue
     *            the value for cul_indexdetaildata.selTypeValue
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public void setSeltypevalue(String seltypevalue) {
	this.seltypevalue = seltypevalue == null ? null : seltypevalue.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table cul_indexdetaildata
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    @Override
    public Object getPrimaryKey() {
	return this.getDatadetailid();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table cul_indexdetaildata
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(getClass().getSimpleName());
	sb.append(" [");
	sb.append("Hash = ").append(hashCode());
	sb.append(", datadetailid=").append(datadetailid);
	sb.append(", culid=").append(culid);
	sb.append(", culindexid=").append(culindexid);
	sb.append(", normaldata=").append(normaldata);
	sb.append(", datatype=").append(datatype);
	sb.append(", seltypevalue=").append(seltypevalue);
	sb.append(", serialVersionUID=").append(serialVersionUID);
	sb.append("]");
	return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table cul_indexdetaildata
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    @Override
    public boolean equals(Object that) {
	if (this == that) {
	    return true;
	}
	if (that == null) {
	    return false;
	}
	if (getClass() != that.getClass()) {
	    return false;
	}
	CulIndexDetailData other = (CulIndexDetailData) that;
	return (this.getDatadetailid() == null ? other.getDatadetailid() == null
		: this.getDatadetailid().equals(other.getDatadetailid()))
		&& (this.getCulid() == null ? other.getCulid() == null : this.getCulid().equals(other.getCulid()))
		&& (this.getCulindexid() == null ? other.getCulindexid() == null
			: this.getCulindexid().equals(other.getCulindexid()))
		&& (this.getNormaldata() == null ? other.getNormaldata() == null
			: this.getNormaldata().equals(other.getNormaldata()))
		&& (this.getDatatype() == null ? other.getDatatype() == null
			: this.getDatatype().equals(other.getDatatype()))
		&& (this.getSeltypevalue() == null ? other.getSeltypevalue() == null
			: this.getSeltypevalue().equals(other.getSeltypevalue()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table cul_indexdetaildata
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((getDatadetailid() == null) ? 0 : getDatadetailid().hashCode());
	result = prime * result + ((getCulid() == null) ? 0 : getCulid().hashCode());
	result = prime * result + ((getCulindexid() == null) ? 0 : getCulindexid().hashCode());
	result = prime * result + ((getNormaldata() == null) ? 0 : getNormaldata().hashCode());
	result = prime * result + ((getDatatype() == null) ? 0 : getDatatype().hashCode());
	result = prime * result + ((getSeltypevalue() == null) ? 0 : getSeltypevalue().hashCode());
	return result;
    }

    public String getIndexName() {
	return indexName;
    }

    public void setIndexName(String indexName) {
	this.indexName = indexName;
    }

    public String getIndexId() {
	return indexId;
    }

    public void setIndexId(String indexId) {
	this.indexId = indexId;
    }

    public SysIndexitem getSysIndexitem() {
	return sysIndexitem;
    }

    public void setSysIndexitem(SysIndexitem sysIndexitem) {
	this.sysIndexitem = sysIndexitem;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }
}