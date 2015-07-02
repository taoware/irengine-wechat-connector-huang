package com.irengine.wechat.connector.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "ss_message")
public class OutMessage extends EntityBase{

	@Column(nullable=false)
	private String type;//news,text
	
	@Column(nullable=false)
	private String content;//文本内容
	
	private String url;//点击图文消息跳转的链接
	
	private String picUrl;//图片url
	
	private String title;//图文消息标题
	
	@Column(nullable=false)
	private String menuName;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date startDate;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date endDate;

	private boolean disable;// 是否被禁用(false为启用)
	
	public OutMessage() {
		super();
	}

	public OutMessage(String type, String content, String url, String picUrl,
			String title, String menuName, Date startDate, Date endDate,
			boolean disable) {
		super();
		this.type = type;
		this.content = content;
		this.url = url;
		this.picUrl = picUrl;
		this.title = title;
		this.menuName = menuName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.disable = disable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

}















