package com.irengine.wechat.connector.repository;

import org.springframework.data.repository.CrudRepository;

import com.irengine.wechat.connector.domain.WCUser;

public interface WCUserDao extends CrudRepository<WCUser, Long>{

	WCUser findOneByOpenId(String openId);

}
