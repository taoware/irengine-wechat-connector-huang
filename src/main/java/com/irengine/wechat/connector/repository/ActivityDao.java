package com.irengine.wechat.connector.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.irengine.wechat.connector.domain.Activity;

public interface ActivityDao extends CrudRepository<Activity, Long> {
	
	@Query("select a from Activity a where a.type=:type order by a.id desc")
	List<Activity> findAllByType(@Param("type") String type);

}
