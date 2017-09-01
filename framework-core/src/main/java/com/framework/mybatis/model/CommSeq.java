package com.framework.mybatis.model;

import com.framework.model.BaseModel;
import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table comm_seq
 *
 * @mbg.generated do_not_delete_during_merge Fri Sep 01 14:05:25 CST 2017
 */
public class CommSeq extends BaseModel implements Serializable {
    /**
     * Database Column Remarks:
     *   序列名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_seq.sequence_name
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    private String sequenceName;

    /**
     * Database Column Remarks:
     *   当前值
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_seq.current_val
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    private Integer currentVal;

    /**
     * Database Column Remarks:
     *   步长(跨度)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comm_seq.increment_val
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    private Integer incrementVal;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comm_seq
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_seq.sequence_name
     *
     * @return the value of comm_seq.sequence_name
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    public String getSequenceName() {
        return sequenceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_seq.sequence_name
     *
     * @param sequenceName the value for comm_seq.sequence_name
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName == null ? null : sequenceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_seq.current_val
     *
     * @return the value of comm_seq.current_val
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    public Integer getCurrentVal() {
        return currentVal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_seq.current_val
     *
     * @param currentVal the value for comm_seq.current_val
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    public void setCurrentVal(Integer currentVal) {
        this.currentVal = currentVal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comm_seq.increment_val
     *
     * @return the value of comm_seq.increment_val
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    public Integer getIncrementVal() {
        return incrementVal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comm_seq.increment_val
     *
     * @param incrementVal the value for comm_seq.increment_val
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    public void setIncrementVal(Integer incrementVal) {
        this.incrementVal = incrementVal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comm_seq
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    @Override
    public Object getPrimaryKey() {
        return this.getSequenceName();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comm_seq
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sequenceName=").append(sequenceName);
        sb.append(", currentVal=").append(currentVal);
        sb.append(", incrementVal=").append(incrementVal);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comm_seq
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
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
        CommSeq other = (CommSeq) that;
        return (this.getSequenceName() == null ? other.getSequenceName() == null : this.getSequenceName().equals(other.getSequenceName()))
            && (this.getCurrentVal() == null ? other.getCurrentVal() == null : this.getCurrentVal().equals(other.getCurrentVal()))
            && (this.getIncrementVal() == null ? other.getIncrementVal() == null : this.getIncrementVal().equals(other.getIncrementVal()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comm_seq
     *
     * @mbg.generated Fri Sep 01 14:05:25 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSequenceName() == null) ? 0 : getSequenceName().hashCode());
        result = prime * result + ((getCurrentVal() == null) ? 0 : getCurrentVal().hashCode());
        result = prime * result + ((getIncrementVal() == null) ? 0 : getIncrementVal().hashCode());
        return result;
    }
}