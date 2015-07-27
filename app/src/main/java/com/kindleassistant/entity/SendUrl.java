package com.kindleassistant.entity;


public class SendUrl {
	private String url;
	private String to_email;
	private String from_email;

	public SendUrl(String url, String to_email, String from_email) {
		this.url = url;
		this.to_email = to_email;
		this.from_email = from_email;
	}

}
