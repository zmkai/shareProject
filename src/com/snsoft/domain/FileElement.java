package com.snsoft.domain;

import java.sql.Timestamp;


public class FileElement {
	private String id;//id
	private String account;//�˻�
	private Timestamp uploadDate;//�ļ�������·��
	private String fileName;//�ļ���
	private String href;//�ļ�����
	private String fileType;//�ļ�����
	private String absulatePath;//�ļ��ľ���·��
	private String destPath;//��ѹ·��
	
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
