package com.irengine.wechat.connector.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.irengine.wechat.connector.domain.OutMessage;
import com.irengine.wechat.connector.service.ActivityService;
import com.irengine.wechat.connector.service.OutMessageService;

@Controller
@RequestMapping("/message")
public class OutMessageController {

	@Autowired
	private OutMessageService outMessageService;
	
	@Autowired
	private ActivityService activityService;

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
		/* 处理日期 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		Date endDate;
		try {
			startDate = sdf.parse(startDateString);
			endDate = sdf.parse(endDateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
			model.addAttribute("msg", "日期转换错误");
			return "error";
		}
		OutMessage message = new OutMessage(type, content, url, picUrl, title,
				menuName, startDate, endDate, false);
		outMessageService.save(message);
		/**更新菜单*/
		try {
			activityService.updateMenu();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			model.addAttribute("msg", "更新菜单出错");
			return "success";
		}
		model.addAttribute("msg", "i got it");
		return "success";
	}
}
