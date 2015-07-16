package com.irengine.wechat.connector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.wechat.connector.domain.Admin;
import com.irengine.wechat.connector.repository.AdminDao;

@Service
@Transactional
public class AdminService {
	
	@Autowired
	AdminDao adminDao;

	public Admin findOneByUsername(String username) {
		return adminDao.findOneByUsername(username);
	}
	
}
