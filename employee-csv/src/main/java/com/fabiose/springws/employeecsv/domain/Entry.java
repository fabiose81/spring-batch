package com.fabiose.springws.employeecsv.domain;

public class Entry {

	private String file;
	private String header;
	private String body;
	private String footer;
	
	public Entry(String file, String header, String body, String footer) {
		this.file = file;
		this.header = header;
		this.body = body;
		this.footer = footer;
	}
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	
}
