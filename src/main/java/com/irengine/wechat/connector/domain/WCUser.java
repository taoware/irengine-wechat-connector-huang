package com.irengine.wechat.connector.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ss_wc_user")
public class WCUser  extends EntityBase{
	
	private String openId;
	
	private String nickname;
	
	private String sex;
	
	private String city;
	
	private String province;
	
	private String country;
	
	private String unionId;

	private String mobile;
	
	public WCUser() {
		super();
	}

	public WCUser(String openId, String nickname, String sex, String city,
			String province, String country, String unionId) {
		super();
		this.openId = openId;
		this.nickname = nickname;
		this.sex = sex;
		this.city = city;
		this.province = province;
		this.country = country;
		this.unionId = unionId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
}
