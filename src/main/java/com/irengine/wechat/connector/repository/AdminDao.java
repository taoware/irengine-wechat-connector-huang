package com.irengine.wechat.connector.repository;

import org.springframework.data.repository.CrudRepository;

import com.irengine.wechat.connector.domain.Admin;

public interface AdminDao extends CrudRepository<Admin, Long>{

	Admin findOneByUsername(String username);
	
}
