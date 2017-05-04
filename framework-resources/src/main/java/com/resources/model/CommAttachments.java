package com.resources.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.framework.model.BaseModel;

public class CommAttachments extends BaseModel implements Serializable {
	private String attaid;

	private String dataid;

	/**
	 * 
	 */
	private String busstype;

	/**
	 * 附件类型: 0 视频、1图片、2音频、3文档（pdf与word）等
	 */
	private String attatype;

	private Integer attano;

	private String attaname;

	private String attadesc;

	private String filepath;

	private String filename;

	private BigDecimal filesize;

	private BigDecimal width;

	private BigDecimal height;

	private String attaJson;

	private String remark;

	private byte[] fileblob;

	private static final long serialVersionUID = 1L;

	public String getAttaid() {
		return attaid;
	}

	public void setAttaid(String attaid) {
		this.attaid = attaid == null ? null : attaid.trim();
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid == null ? null : dataid.trim();
	}

	public String getBusstype() {
		return busstype;
	}

	public void setBusstype(String busstype) {
		this.busstype = busstype == null ? null : busstype.trim();
	}

	public String getAttatype() {
		return attatype;
	}

	public void setAttatype(String attatype) {
		this.attatype = attatype == null ? null : attatype.trim();
	}

	public Integer getAttano() {
		return attano;
	}

	public void setAttano(Integer attano) {
		this.attano = attano;
	}

	public String getAttaname() {
		return attaname;
	}

	public void setAttaname(String attaname) {
		this.attaname = attaname == null ? null : attaname.trim();
	}

	public String getAttadesc() {
		return attadesc;
	}

	public void setAttadesc(String attadesc) {
		this.attadesc = attadesc == null ? null : attadesc.trim();
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath == null ? null : filepath.trim();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename == null ? null : filename.trim();
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

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getAttaJson() {
		return attaJson;
	}

	public void setAttaJson(String attaJson) {
		this.attaJson = attaJson == null ? null : attaJson.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public byte[] getFileblob() {
		return fileblob;
	}

	public void setFileblob(byte[] fileblob) {
		this.fileblob = fileblob;
	}

	@Override
	public Object getPrimaryKey() {
		return this.getAttaid();
	}
}