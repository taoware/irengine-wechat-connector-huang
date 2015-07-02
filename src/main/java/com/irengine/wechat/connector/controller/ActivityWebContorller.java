package com.irengine.wechat.connector.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.irengine.wechat.connector.domain.Activity;
import com.irengine.wechat.connector.service.ActivityService;
import com.irengine.wechat.connector.service.OutMessageService;
import com.irengine.wechat.util.UploadFileUtil;

@Controller
@RequestMapping("/activity")
public class ActivityWebContorller {

	@Autowired
	private ActivityService activityService;

	@Autowired
	private OutMessageService outMessageService;

	private static Logger logger = LoggerFactory
			.getLogger(ActivityWebContorller.class);

	/**
	 * 新建活动 POST ->/activity/test
	 */
	@RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String test(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		logger.debug("调用新建活动接口");
		/* 上传文件并解压到指定路径 */
		try {
			UploadFileUtil.uploadAndExtractFile(file, request);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/**
	 * 新建活动 POST ->/activity
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String save(@RequestParam("name") String name,
			@RequestParam("type") String type,
			@RequestParam("description") String description,
			@RequestParam("startDate") String startDateString,
			@RequestParam("endDate") String endDateString,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request, Model model) {
		logger.debug("调用新建活动接口");
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
		/* 上传文件 */
		try {
			/* 得到解压后文件名,不能重名 */
			String folderName = UploadFileUtil.uploadAndExtractFile(file,
					request).get("name");
			Activity activity = new Activity(false, name, "index",
					folderName, type, description, startDate, endDate," ");
			activityService.save(activity);
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("msg", "上传并解压文件失败");
			return "error";
		}
		/* 调用更新菜单接口 */
		try {
			activityService.updateMenu();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			model.addAttribute("msg", "更新菜单失败");
			return "error";
		}
		model.addAttribute("msg", "i got it!");
		return "success";
	}

}
