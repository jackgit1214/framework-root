package com.museum.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * This class was generated by MyBatis Generator. This class corresponds to the
 * database table cul_indexdetaildata
 *
 * @mbg.generated do_not_delete_during_merge Sun Apr 01 10:45:25 CST 2018
 */
public class CulIndexDetailDataWithBLOBs extends CulIndexDetailData implements Serializable {
    /**
     * Database Column Remarks: 大文本数据存储
     *
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column cul_indexdetaildata.clobData
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private byte[] clobdata;

    private String stringClobData;
    /**
     * Database Column Remarks: 备注
     *
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column cul_indexdetaildata.remark
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private byte[] remark;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database table cul_indexdetaildata
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column cul_indexdetaildata.clobData
     *
     * @return the value of cul_indexdetaildata.clobData
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public byte[] getClobdata() {
	return clobdata;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column cul_indexdetaildata.clobData
     *
     * @param clobdata
     *            the value for cul_indexdetaildata.clobData
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public void setClobdata(byte[] clobdata) {
	this.clobdata = clobdata;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column cul_indexdetaildata.remark
     *
     * @return the value of cul_indexdetaildata.remark
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public byte[] getRemark() {
	return remark;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column cul_indexdetaildata.remark
     *
     * @param remark
     *            the value for cul_indexdetaildata.remark
     *
     * @mbg.generated Sun Apr 01 10:45:25 CST 2018
     */
    public void setRemark(byte[] remark) {
	this.remark = remark;
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
	sb.append(", clobdata=").append(clobdata);
	sb.append(", remark=").append(remark);
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
	CulIndexDetailDataWithBLOBs other = (CulIndexDetailDataWithBLOBs) that;
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
			: this.getSeltypevalue().equals(other.getSeltypevalue()))
		&& (Arrays.equals(this.getClobdata(), other.getClobdata()))
		&& (Arrays.equals(this.getRemark(), other.getRemark()));
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
	result = prime * result + (Arrays.hashCode(getClobdata()));
	result = prime * result + (Arrays.hashCode(getRemark()));
	return result;
    }

    public String getStringClobData() {
	String tmpStr = new String(this.clobdata);
	return tmpStr;
    }

    public void setStringClobData(String stringClobData) {
	this.stringClobData = stringClobData;
    }
}