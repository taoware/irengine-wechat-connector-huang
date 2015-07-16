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
@Table(name = "ss_out_message")
public class OutMessage extends EntityBase{

	@Column(nullable=false)
	private String type;//news,text
	
	@Column(nullable=false)
	private String content;//文本内容
	
	@Column(length=1000)
	private String url;//点击图文消息跳转的链接
	
	@Column(length=1000)
	private String picUrl;//图片url
	
	private String title;//图文消息标题
	
	@Column(nullable=false)
	private String menuName;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date startDate;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date endDate;

	private boolean disable;// 是否被禁用(false为启用)
	
	@JsonIgnore
    @ManyToMany
    @JoinTable(name="ss_message_wcuser",
          joinColumns=@JoinColumn(name="message_id"),
          inverseJoinColumns=@JoinColumn(name="wcuser_id"))
	private List<WCUser> wcUsers=new ArrayList<WCUser>();
	
	@Transient
	public long getCount() {
		return wcUsers.size();
	}

	@Transient
	public String getReturnContent(){
		return getsubstring(content, 60);
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

	/**处理文本消息字符串过长显示不美观问题*/
	private String getsubstring(String content, int sublength) {
		int length = content.getBytes().length;
		if (sublength >= length) {
			return content;
		} else {
				int i = 0;
				int j = 0;
				for (; i < length; i++) {
					if (content.substring(0, i + 1).getBytes().length >= sublength) {
						j = content.substring(0, i + 1).getBytes().length;
						break;
					}

				}
				String substring = j > sublength ? content.substring(0, i)
						: content.substring(0, i + 1);
				return substring+"...";
		}
	}
	
}















