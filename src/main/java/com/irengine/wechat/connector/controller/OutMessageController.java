package com.irengine.wechat.connector.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.irengine.wechat.connector.domain.OutMessage;
import com.irengine.wechat.connector.service.ActivityService;
import com.irengine.wechat.connector.service.OutMessageService;

@Controller
@RequestMapping("/message")
public class OutMessageController {

	private static Logger logger = LoggerFactory
			.getLogger(OutMessageController.class);

	@Autowired
	private OutMessageService outMessageService;

	@Autowired
	private ActivityService activityService;

	/**
	 * 查询推送消息 GET ->/message
	 */
	@RequestMapping("")
	public ResponseEntity<?> getAll(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "type", required = false) String type) {
		List<OutMessage> messages = new ArrayList<OutMessage>();
		if (id != null) {
			logger.debug("按id查询推送消息,id=" + id);
			OutMessage message = outMessageService.findOneById(id);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else if (type != null) {
			logger.debug("按type查询推送消息,type=" + type);
			messages = outMessageService.findAllByType(type);
			return new ResponseEntity<>(messages, HttpStatus.OK);
		} else {
			logger.debug("查询所有消息");
			messages = outMessageService.findAll();
			return new ResponseEntity<>(messages, HttpStatus.OK);
		}
	}

	/**
	 * 禁用或启用推送消息 GET ->/message/{id}/disable
	 */
	@RequestMapping(value = "/{id}/disable", method = RequestMethod.GET)
	public ResponseEntity<?> disable(@PathVariable("id") Long id) {
		OutMessage message = outMessageService.findOneById(id);
		if (message.isDisable()) {
			logger.debug("启用id为" + id + "的推送信息");
			message.setDisable(false);
		} else {
			logger.debug("禁用id为" + id + "的推送信息");
			message.setDisable(true);
		}
		outMessageService.save(message);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/** 新建推送消息 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Transactional
	public String create(
			@RequestParam("type") String type,
			@RequestParam("content") String content,
			@RequestParam(value = "url", required = false, defaultValue = " ") String url,
			@RequestParam(value = "picUrl", required = false, defaultValue = " ") String picUrl,
			@RequestParam(value = "title", required = false, defaultValue = " ") String title,
			@RequestParam("menuName") String menuName,
			@RequestParam("startDate") String startDateString,
			@RequestParam("endDate") String endDateString, Model model) {
		logger.debug("调用新建推送消息接口");
		/* 处理日期 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		Date endDate;
		try {
			startDate = sdf.parse(startDateString);
			endDate = sdf.parse(endDateString);
		} catch (ParseException e1) {
			logger.debug("日期转换错误");
			e1.printStackTrace();
			model.addAttribute("msg", "日期转换错误");
			return "error";
		}
		OutMessage message = new OutMessage(type, content, url, picUrl, title,
				menuName, startDate, endDate, false);
		outMessageService.save(message);
		/** 更新菜单 */
		try {
			activityService.updateMenu();
		} catch (UnsupportedEncodingException e) {
			logger.debug("更新菜单出错");
			e.printStackTrace();
			model.addAttribute("msg", "更新菜单出错");
			return "success";
		}
		model.addAttribute("msg", "i got it");
		return "success";
	}
}
