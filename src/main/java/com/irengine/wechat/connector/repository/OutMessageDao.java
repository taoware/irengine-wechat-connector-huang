package com.irengine.wechat.connector.repository;

import org.springframework.data.repository.CrudRepository;

import com.irengine.wechat.connector.domain.OutMessage;

public interface OutMessageDao extends CrudRepository<OutMessage, Long>{

}
