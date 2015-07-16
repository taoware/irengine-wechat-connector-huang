package com.irengine.wechat.connector.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.irengine.wechat.connector.domain.Admin;
import com.irengine.wechat.connector.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static Logger logger = LoggerFactory
			.getLogger(AdminController.class);
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String login(HttpServletRequest request,
			@RequestParam("username")String username,
			@RequestParam("password")String password,
			Model model) {
		logger.debug("管理员登录");
		Admin admin=adminService.findOneByUsername(username);
		if(admin!=null){
			if(admin.getPassword().equals(password)){
				/*登录成功,session绑定信息*/
				HttpSession session=request.getSession();
				session.setAttribute("username", username);
				model.addAttribute("msg","登录成功");
				return "index";
			}else{
				/*密码错误*/
				model.addAttribute("msg", "密码错误");
				return "login";
			}
		}else{
			logger.debug("用户不存在");
			model.addAttribute("msg", "用户名不存在");
			return "login";
		}
	}
	
}
