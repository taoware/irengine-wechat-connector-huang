package com.irengine.wechat.connector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.wechat.connector.domain.OutMessage;
import com.irengine.wechat.connector.repository.OutMessageDao;

@Service
@Transactional
public class OutMessageService {

	@Autowired
	private OutMessageDao outMessageDao;

	public List<OutMessage> findAll() {
		return (List<OutMessage>) outMessageDao.findAll();
	}

	public OutMessage findOneById(long id) {
		return outMessageDao.findOne(id);
	}

	public OutMessage save(OutMessage message) {
		return outMessageDao.save(message);
	}

	public List<OutMessage> findAllByType(String type) {
		return outMessageDao.findAllByType(type);
	}

	public long count() {
		return outMessageDao.count();
	}

	public void deleteOneById(Long id) {
		outMessageDao.delete(id);
	}

}
