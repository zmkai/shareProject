package com.snsoft.domain;

import java.sql.Timestamp;


public class FileElement {
	private String id;//id
	private String account;//账户
	private Timestamp uploadDate;//文件的下载路径
	private String fileName;//文件名
	private String href;//文件链接
	private String fileType;//文件类型
	private String absulatePath;//文件的绝对路径
	private String destPath;//解压路径
	
	public String getDestPath() {
		return destPath;
	}
	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}
	public String getAbsulatePath() {
		return absulatePath;
	}
	public void setAbsulatePath(String absulatePath) {
		this.absulatePath = absulatePath;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Timestamp getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String toString() {
		String result = "";
		result +=id+" "+account+" "+uploadDate+" "+fileName+" "+href+" "+fileType+"  "+absulatePath+"  "+destPath;
		return result;
	}
	
}
