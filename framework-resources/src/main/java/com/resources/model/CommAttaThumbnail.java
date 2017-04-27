package com.resources.model;

import java.io.Serializable;

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
	private String rank;

	private String filename;

	private String filesize;

	private String width;

	private String height;

	private String remark;

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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename == null ? null : filename.trim();
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height == null ? null : height.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
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

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
}