package com.irengine.wechat.connector.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "ss_activity")
public class Activity extends EntityBase {

	private boolean disable;// 是否被禁用(false为启用)

	@Column(nullable = false)
	private String name;// 活动名称,决定菜单名

	@Column(nullable = false)
	private String indexName;// 主页名(不带.html)

	@Column(nullable = false, unique = true)
	private String folderName;// 文件名(不重名)

	private String type;// 类型(url为跳转活动)

	private String description;// 活动描述

	private String url;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date startDate;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date endDate;

	public Activity() {
		super();
	}

	public Activity(boolean disable, String name, String indexName,
			String folderName, String type, String description, Date startDate,
			Date endDate,String url) {
		super();
		this.disable = disable;
		this.name = name;
		this.indexName = indexName;
		this.folderName = folderName;
		this.type = type;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.url=url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
