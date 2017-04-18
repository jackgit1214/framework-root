package com.resources.model;

import com.framework.model.BaseModel;
import java.io.Serializable;
import java.math.BigDecimal;

public class CommAttaThumbnail extends BaseModel implements Serializable {
    private String trumbnailid;

    private String attaid;

    private String filepath;

    private BigDecimal filesize;

    private BigDecimal width;

    private String height;

    private BigDecimal remark;

    private byte[] filename;

    private static final long serialVersionUID = 1L;

    public String getTrumbnailid() {
        return trumbnailid;
    }

    public void setTrumbnailid(String trumbnailid) {
        this.trumbnailid = trumbnailid == null ? null : trumbnailid.trim();
    }

    public String getAttaid() {
        return attaid;
    }

    public void setAttaid(String attaid) {
        this.attaid = attaid == null ? null : attaid.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public BigDecimal getFilesize() {
        return filesize;
    }

    public void setFilesize(BigDecimal filesize) {
        this.filesize = filesize;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    public BigDecimal getRemark() {
        return remark;
    }

    public void setRemark(BigDecimal remark) {
        this.remark = remark;
    }

    public byte[] getFilename() {
        return filename;
    }

    public void setFilename(byte[] filename) {
        this.filename = filename;
    }

    @Override
    public Object getPrimaryKey() {
        return this.getTrumbnailid();
    }
}