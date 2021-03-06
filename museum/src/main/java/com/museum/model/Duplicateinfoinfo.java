package com.museum.model;

import com.framework.model.BaseModel;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table duplicateinfoinfo
 *
 * @mbg.generated do_not_delete_during_merge Wed Aug 30 17:15:10 CST 2017
 */
public class Duplicateinfoinfo extends BaseModel implements Serializable {
    /**
     * Database Column Remarks:
     *   复制登记明细ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.DuplicateInfoID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String duplicateinfoid;

    /**
     * Database Column Remarks:
     *   从文物基础表 Cul_MainCul1/2/3（文物一、二、三）中提取文物信息
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.CulID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String culid;

    /**
     * Database Column Remarks:
     *   原总登记号，作为复制品编号（手工管理的编号）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.B0213
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String b0213;

    /**
     * Database Column Remarks:
     *   复制后可能发生变化
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.A0102
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String a0102;

    /**
     * Database Column Remarks:
     *   与系统代码表 Sys_Code 的 CodeType（='cpjb'）关联（Superior_Code <> 'xxx'）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.B0401
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String b0401;

    /**
     * Database Column Remarks:
     *   复制品质地类别
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.A0601
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String a0601;

    /**
     * Database Column Remarks:
     *   复制品尺寸
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.A1611
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String a1611;

    /**
     * Database Column Remarks:
     *   复制品尺寸单位
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.A1612
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String a1612;

    /**
     * Database Column Remarks:
     *   复制品容积
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.A1621
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String a1621;

    /**
     * Database Column Remarks:
     *   复制品容积单位
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.A1622
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String a1622;

    /**
     * Database Column Remarks:
     *   复制品质量
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.A1631
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String a1631;

    /**
     * Database Column Remarks:
     *   复制品质量单位
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.A1632
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String a1632;

    /**
     * Database Column Remarks:
     *   复制后可能发生变化
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.Destination
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String destination;

    /**
     * Database Column Remarks:
     *   作为业务信息的录入者，与系统用户表 Sys_User 关联
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.Updating_UID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String updatingUid;

    /**
     * Database Column Remarks:
     *   更新日期
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.Updating_Date
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String updatingDate;

    /**
     * Database Column Remarks:
     *   对应复制任务信息，可以为空
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.CulturalRelicCopyID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private String culturalreliccopyid;

    /**
     * Database Column Remarks:
     *   remark
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column duplicateinfoinfo.remark
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private byte[] remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table duplicateinfoinfo
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.DuplicateInfoID
     *
     * @return the value of duplicateinfoinfo.DuplicateInfoID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getDuplicateinfoid() {
        return duplicateinfoid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.DuplicateInfoID
     *
     * @param duplicateinfoid the value for duplicateinfoinfo.DuplicateInfoID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setDuplicateinfoid(String duplicateinfoid) {
        this.duplicateinfoid = duplicateinfoid == null ? null : duplicateinfoid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.CulID
     *
     * @return the value of duplicateinfoinfo.CulID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getCulid() {
        return culid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.CulID
     *
     * @param culid the value for duplicateinfoinfo.CulID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setCulid(String culid) {
        this.culid = culid == null ? null : culid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.B0213
     *
     * @return the value of duplicateinfoinfo.B0213
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getB0213() {
        return b0213;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.B0213
     *
     * @param b0213 the value for duplicateinfoinfo.B0213
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setB0213(String b0213) {
        this.b0213 = b0213 == null ? null : b0213.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.A0102
     *
     * @return the value of duplicateinfoinfo.A0102
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getA0102() {
        return a0102;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.A0102
     *
     * @param a0102 the value for duplicateinfoinfo.A0102
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setA0102(String a0102) {
        this.a0102 = a0102 == null ? null : a0102.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.B0401
     *
     * @return the value of duplicateinfoinfo.B0401
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getB0401() {
        return b0401;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.B0401
     *
     * @param b0401 the value for duplicateinfoinfo.B0401
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setB0401(String b0401) {
        this.b0401 = b0401 == null ? null : b0401.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.A0601
     *
     * @return the value of duplicateinfoinfo.A0601
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getA0601() {
        return a0601;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.A0601
     *
     * @param a0601 the value for duplicateinfoinfo.A0601
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setA0601(String a0601) {
        this.a0601 = a0601 == null ? null : a0601.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.A1611
     *
     * @return the value of duplicateinfoinfo.A1611
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getA1611() {
        return a1611;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.A1611
     *
     * @param a1611 the value for duplicateinfoinfo.A1611
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setA1611(String a1611) {
        this.a1611 = a1611 == null ? null : a1611.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.A1612
     *
     * @return the value of duplicateinfoinfo.A1612
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getA1612() {
        return a1612;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.A1612
     *
     * @param a1612 the value for duplicateinfoinfo.A1612
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setA1612(String a1612) {
        this.a1612 = a1612 == null ? null : a1612.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.A1621
     *
     * @return the value of duplicateinfoinfo.A1621
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getA1621() {
        return a1621;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.A1621
     *
     * @param a1621 the value for duplicateinfoinfo.A1621
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setA1621(String a1621) {
        this.a1621 = a1621 == null ? null : a1621.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.A1622
     *
     * @return the value of duplicateinfoinfo.A1622
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getA1622() {
        return a1622;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.A1622
     *
     * @param a1622 the value for duplicateinfoinfo.A1622
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setA1622(String a1622) {
        this.a1622 = a1622 == null ? null : a1622.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.A1631
     *
     * @return the value of duplicateinfoinfo.A1631
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getA1631() {
        return a1631;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.A1631
     *
     * @param a1631 the value for duplicateinfoinfo.A1631
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setA1631(String a1631) {
        this.a1631 = a1631 == null ? null : a1631.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.A1632
     *
     * @return the value of duplicateinfoinfo.A1632
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getA1632() {
        return a1632;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.A1632
     *
     * @param a1632 the value for duplicateinfoinfo.A1632
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setA1632(String a1632) {
        this.a1632 = a1632 == null ? null : a1632.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.Destination
     *
     * @return the value of duplicateinfoinfo.Destination
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getDestination() {
        return destination;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.Destination
     *
     * @param destination the value for duplicateinfoinfo.Destination
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.Updating_UID
     *
     * @return the value of duplicateinfoinfo.Updating_UID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getUpdatingUid() {
        return updatingUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.Updating_UID
     *
     * @param updatingUid the value for duplicateinfoinfo.Updating_UID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setUpdatingUid(String updatingUid) {
        this.updatingUid = updatingUid == null ? null : updatingUid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.Updating_Date
     *
     * @return the value of duplicateinfoinfo.Updating_Date
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getUpdatingDate() {
        return updatingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.Updating_Date
     *
     * @param updatingDate the value for duplicateinfoinfo.Updating_Date
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setUpdatingDate(String updatingDate) {
        this.updatingDate = updatingDate == null ? null : updatingDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.CulturalRelicCopyID
     *
     * @return the value of duplicateinfoinfo.CulturalRelicCopyID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public String getCulturalreliccopyid() {
        return culturalreliccopyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.CulturalRelicCopyID
     *
     * @param culturalreliccopyid the value for duplicateinfoinfo.CulturalRelicCopyID
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setCulturalreliccopyid(String culturalreliccopyid) {
        this.culturalreliccopyid = culturalreliccopyid == null ? null : culturalreliccopyid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column duplicateinfoinfo.remark
     *
     * @return the value of duplicateinfoinfo.remark
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public byte[] getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column duplicateinfoinfo.remark
     *
     * @param remark the value for duplicateinfoinfo.remark
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    public void setRemark(byte[] remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duplicateinfoinfo
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    @Override
    public Object getPrimaryKey() {
        return this.getDuplicateinfoid();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duplicateinfoinfo
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", duplicateinfoid=").append(duplicateinfoid);
        sb.append(", culid=").append(culid);
        sb.append(", b0213=").append(b0213);
        sb.append(", a0102=").append(a0102);
        sb.append(", b0401=").append(b0401);
        sb.append(", a0601=").append(a0601);
        sb.append(", a1611=").append(a1611);
        sb.append(", a1612=").append(a1612);
        sb.append(", a1621=").append(a1621);
        sb.append(", a1622=").append(a1622);
        sb.append(", a1631=").append(a1631);
        sb.append(", a1632=").append(a1632);
        sb.append(", destination=").append(destination);
        sb.append(", updatingUid=").append(updatingUid);
        sb.append(", updatingDate=").append(updatingDate);
        sb.append(", culturalreliccopyid=").append(culturalreliccopyid);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duplicateinfoinfo
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
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
        Duplicateinfoinfo other = (Duplicateinfoinfo) that;
        return (this.getDuplicateinfoid() == null ? other.getDuplicateinfoid() == null : this.getDuplicateinfoid().equals(other.getDuplicateinfoid()))
            && (this.getCulid() == null ? other.getCulid() == null : this.getCulid().equals(other.getCulid()))
            && (this.getB0213() == null ? other.getB0213() == null : this.getB0213().equals(other.getB0213()))
            && (this.getA0102() == null ? other.getA0102() == null : this.getA0102().equals(other.getA0102()))
            && (this.getB0401() == null ? other.getB0401() == null : this.getB0401().equals(other.getB0401()))
            && (this.getA0601() == null ? other.getA0601() == null : this.getA0601().equals(other.getA0601()))
            && (this.getA1611() == null ? other.getA1611() == null : this.getA1611().equals(other.getA1611()))
            && (this.getA1612() == null ? other.getA1612() == null : this.getA1612().equals(other.getA1612()))
            && (this.getA1621() == null ? other.getA1621() == null : this.getA1621().equals(other.getA1621()))
            && (this.getA1622() == null ? other.getA1622() == null : this.getA1622().equals(other.getA1622()))
            && (this.getA1631() == null ? other.getA1631() == null : this.getA1631().equals(other.getA1631()))
            && (this.getA1632() == null ? other.getA1632() == null : this.getA1632().equals(other.getA1632()))
            && (this.getDestination() == null ? other.getDestination() == null : this.getDestination().equals(other.getDestination()))
            && (this.getUpdatingUid() == null ? other.getUpdatingUid() == null : this.getUpdatingUid().equals(other.getUpdatingUid()))
            && (this.getUpdatingDate() == null ? other.getUpdatingDate() == null : this.getUpdatingDate().equals(other.getUpdatingDate()))
            && (this.getCulturalreliccopyid() == null ? other.getCulturalreliccopyid() == null : this.getCulturalreliccopyid().equals(other.getCulturalreliccopyid()))
            && (Arrays.equals(this.getRemark(), other.getRemark()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duplicateinfoinfo
     *
     * @mbg.generated Wed Aug 30 17:15:10 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDuplicateinfoid() == null) ? 0 : getDuplicateinfoid().hashCode());
        result = prime * result + ((getCulid() == null) ? 0 : getCulid().hashCode());
        result = prime * result + ((getB0213() == null) ? 0 : getB0213().hashCode());
        result = prime * result + ((getA0102() == null) ? 0 : getA0102().hashCode());
        result = prime * result + ((getB0401() == null) ? 0 : getB0401().hashCode());
        result = prime * result + ((getA0601() == null) ? 0 : getA0601().hashCode());
        result = prime * result + ((getA1611() == null) ? 0 : getA1611().hashCode());
        result = prime * result + ((getA1612() == null) ? 0 : getA1612().hashCode());
        result = prime * result + ((getA1621() == null) ? 0 : getA1621().hashCode());
        result = prime * result + ((getA1622() == null) ? 0 : getA1622().hashCode());
        result = prime * result + ((getA1631() == null) ? 0 : getA1631().hashCode());
        result = prime * result + ((getA1632() == null) ? 0 : getA1632().hashCode());
        result = prime * result + ((getDestination() == null) ? 0 : getDestination().hashCode());
        result = prime * result + ((getUpdatingUid() == null) ? 0 : getUpdatingUid().hashCode());
        result = prime * result + ((getUpdatingDate() == null) ? 0 : getUpdatingDate().hashCode());
        result = prime * result + ((getCulturalreliccopyid() == null) ? 0 : getCulturalreliccopyid().hashCode());
        result = prime * result + (Arrays.hashCode(getRemark()));
        return result;
    }
}