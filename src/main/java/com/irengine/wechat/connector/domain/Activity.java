package com.irengine.wechat.connector.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ss_activity")
public class Activity extends EntityBase {

	private boolean disable;// 是否被禁用(false为启用)

	@Column(nullable = false)
	private String name;// 活动名称,决定菜单名

	@JsonIgnore
	@Column(nullable = false)
	private String indexName;// 主页名(不带.html)

	@JsonIgnore
	@Column(nullable = false, unique = true)
	private String folderName;// 文件名(不重名)

	private String type;// 类型(url为跳转活动)

	private String description;// 活动描述

	@JsonIgnore
	@Column(length=1000)
	private String url;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date startDate;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date endDate;
	

	private List<WCUser> wcUsers=new ArrayList<WCUser>();
	
    @ManyToMany
    @JoinTable(name="ss_activity_wcuser",
          joinColumns=@JoinColumn(name="activity_id"),
          inverseJoinColumns=@JoinColumn(name="wcuser_id"))
	public List<WCUser> getWcUsers() {
		return wcUsers;
	}

	public void setWcUsers(List<WCUser> wcUsers) {
		this.wcUsers = wcUsers;
	}

	@Transient
	public long getCount() {
		return wcUsers.size();
	}
	
	@Transient
	public String getStatusText(){
		Date now = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		endDate = calendar.getTime();
		if(disable==true){
			if(now.after(endDate)){
				return "已结束";
			}else{
				return "已关闭";
			}
		}else{
			if(now.before(startDate)){
				return "未进行";
			}else if(now.after(endDate)){
				return "已结束";
			}else{
				return "进行中";
			}
		}
	}
	
	public Activity() {
		super();
	}

	public Activity(boolean disable, String name, String indexName,
			String folderName, String type, String description, String url,
			Date startDate, Date endDate) {
		super();
		this.disable = disable;
		this.name = name;
		this.indexName = indexName;
		this.folderName = folderName;
		this.type = type;
		this.description = description;
		this.url = url;
		this.startDate = startDate;
		this.endDate = endDate;
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
