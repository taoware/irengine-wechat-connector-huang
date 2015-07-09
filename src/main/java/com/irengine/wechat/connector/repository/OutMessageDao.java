package com.irengine.wechat.connector.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.irengine.wechat.connector.domain.OutMessage;

public interface OutMessageDao extends CrudRepository<OutMessage, Long>{

	@Query("select o from OutMessage o where o.type=:type order by o.id desc")
	List<OutMessage> findAllByType(String type);

}
