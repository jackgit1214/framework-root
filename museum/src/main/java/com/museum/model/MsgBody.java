package com.museum.model;

public class MsgBody {

	private String fromUser;
	private String toUser;
	private String content;

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MsgBody [fromUser=" + fromUser + ", toUser=" + toUser
				+ ", content=" + content + "]";
	}

}
