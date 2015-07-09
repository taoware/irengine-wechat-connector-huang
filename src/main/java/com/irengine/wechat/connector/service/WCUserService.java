package com.irengine.wechat.connector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.wechat.connector.domain.WCUser;
import com.irengine.wechat.connector.repository.WCUserDao;

@Service
@Transactional
public class WCUserService {

	@Autowired
	private WCUserDao wcUserDao;

	public WCUser save(WCUser wcUser) {
		return wcUserDao.save(wcUser);
	}

	public WCUser findOneByOpenId(String openId) {
		return wcUserDao.findOneByOpenId(openId);
	}
	
}
