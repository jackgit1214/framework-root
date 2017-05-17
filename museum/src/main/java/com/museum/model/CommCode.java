package com.museum.model;

import com.framework.model.BaseModel;
import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table comm_code
 *
 * @mbg.generated do_not_delete_during_merge Wed May 17 11:29:23 CST 2017
 */
public class CommCode extends BaseModel implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_code.codeid
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    private String codeid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_code.tablename
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    private String tablename;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_code.fieldname
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    private String fieldname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_code.CODENAME
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    private String codename;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_code.CODE
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_code.ORDERBY
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    private Short orderby;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_code.REMARK
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comm_code
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_code.codeid
     *
     * @return the value of comm_code.codeid
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public String getCodeid() {
        return codeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_code.codeid
     *
     * @param codeid the value for comm_code.codeid
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public void setCodeid(String codeid) {
        this.codeid = codeid == null ? null : codeid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_code.tablename
     *
     * @return the value of comm_code.tablename
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_code.tablename
     *
     * @param tablename the value for comm_code.tablename
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_code.fieldname
     *
     * @return the value of comm_code.fieldname
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public String getFieldname() {
        return fieldname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_code.fieldname
     *
     * @param fieldname the value for comm_code.fieldname
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public void setFieldname(String fieldname) {
        this.fieldname = fieldname == null ? null : fieldname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_code.CODENAME
     *
     * @return the value of comm_code.CODENAME
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public String getCodename() {
        return codename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_code.CODENAME
     *
     * @param codename the value for comm_code.CODENAME
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public void setCodename(String codename) {
        this.codename = codename == null ? null : codename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_code.CODE
     *
     * @return the value of comm_code.CODE
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_code.CODE
     *
     * @param code the value for comm_code.CODE
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_code.ORDERBY
     *
     * @return the value of comm_code.ORDERBY
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public Short getOrderby() {
        return orderby;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_code.ORDERBY
     *
     * @param orderby the value for comm_code.ORDERBY
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public void setOrderby(Short orderby) {
        this.orderby = orderby;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_code.REMARK
     *
     * @return the value of comm_code.REMARK
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_code.REMARK
     *
     * @param remark the value for comm_code.REMARK
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comm_code
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    @Override
    public Object getPrimaryKey() {
        return this.getCodeid();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comm_code
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", codeid=").append(codeid);
        sb.append(", tablename=").append(tablename);
        sb.append(", fieldname=").append(fieldname);
        sb.append(", codename=").append(codename);
        sb.append(", code=").append(code);
        sb.append(", orderby=").append(orderby);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comm_code
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
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
        CommCode other = (CommCode) that;
        return (this.getCodeid() == null ? other.getCodeid() == null : this.getCodeid().equals(other.getCodeid()))
            && (this.getTablename() == null ? other.getTablename() == null : this.getTablename().equals(other.getTablename()))
            && (this.getFieldname() == null ? other.getFieldname() == null : this.getFieldname().equals(other.getFieldname()))
            && (this.getCodename() == null ? other.getCodename() == null : this.getCodename().equals(other.getCodename()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getOrderby() == null ? other.getOrderby() == null : this.getOrderby().equals(other.getOrderby()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comm_code
     *
     * @mbg.generated Wed May 17 11:29:23 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCodeid() == null) ? 0 : getCodeid().hashCode());
        result = prime * result + ((getTablename() == null) ? 0 : getTablename().hashCode());
        result = prime * result + ((getFieldname() == null) ? 0 : getFieldname().hashCode());
        result = prime * result + ((getCodename() == null) ? 0 : getCodename().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getOrderby() == null) ? 0 : getOrderby().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}