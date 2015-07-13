package com.irengine.wechat.connector.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ss_admin")
public class Admin extends EntityBase{

	private String Username;
	
	private String password;

	
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
