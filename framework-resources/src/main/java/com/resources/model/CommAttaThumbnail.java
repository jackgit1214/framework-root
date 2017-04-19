package com.resources.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.framework.model.BaseModel;

public class CommAttaThumbnail extends BaseModel implements Serializable {
	private String trumbnailid;

	private String attaid;

	/**
	 * 不存储文件路径，缩略图文件都存储在数据库 <br>
	 * 这里存储文件缩略图级别， <br>
	 * 级别值越大，缩略图越小<br>
	 * 
	 */
	private String filepath;

	private String filename;

	private BigDecimal filesize;

	private BigDecimal width;

	private String height;

	private BigDecimal remark;

	private byte[] filedata;

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

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	@Override
	public Object getPrimaryKey() {
		return this.getTrumbnailid();
	}
}