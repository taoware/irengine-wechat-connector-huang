package com.irengine.wechat.connector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.irengine.wechat.connector.domain.WCUser;
import com.irengine.wechat.connector.service.WCUserService;

@Controller
@RequestMapping("/wcUser")
public class WCUserController {

	@Autowired
	WCUserService wcUserService;

	@RequestMapping(value = "/openId/{openId}/mobile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String updateMobile(@PathVariable("openId") String openId,
			@RequestParam("mobile") String mobile) {
		WCUser wcUser=wcUserService.findOneByOpenId(openId);
		wcUser.setMobile(mobile);
		wcUserService.save(wcUser);
		return "success";
	}
	
}
