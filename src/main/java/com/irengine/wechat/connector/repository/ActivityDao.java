package com.irengine.wechat.connector.repository;

import org.springframework.data.repository.CrudRepository;

import com.irengine.wechat.connector.domain.Activity;

public interface ActivityDao extends CrudRepository<Activity,Long>{

}
