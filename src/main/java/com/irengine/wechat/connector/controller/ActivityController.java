package com.irengine.wechat.connector.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.irengine.wechat.connector.SmsHelper;
import com.irengine.wechat.connector.WeChatConnector;
import com.irengine.wechat.connector.domain.Activity;
import com.irengine.wechat.connector.domain.Coupon;
import com.irengine.wechat.connector.domain.User;
import com.irengine.wechat.connector.domain.WCUser;
import com.irengine.wechat.connector.service.ActivityService;
import com.irengine.wechat.connector.service.UserService;
import com.irengine.wechat.connector.service.WCUserService;

@Controller
public class ActivityController {

	private static final Logger logger = LoggerFactory
			.getLogger(ActivityController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private WCUserService wcUserService;
	
	// @RequestMapping("/greeting")
	// public String greeting(
	// @RequestParam(value = "name", required = false, defaultValue = "World")
	// String name,
	// Model model) {
	// model.addAttribute("name", name);
	// return "greeting";
	// }

	// @RequestMapping("/test")
	// public String test(HttpServletRequest request, Model model)
	// throws WxErrorException {
	// /* 用code能取得accesstoken,然后用accesstoken得到登录用户信息? */
	// String code = request.getParameter("code");
	// // AccessToken:用户的访问令牌
	// WxMpOAuth2AccessToken wxMpOAuth2AccessToken = WeChatConnector
	// .getMpService().oauth2getAccessToken(code);
	// // WxMpUser:微信用户信息
	// WxMpUser wxMpUser = WeChatConnector.getMpService().oauth2getUserInfo(
	// wxMpOAuth2AccessToken, null);
	// logger.debug("用户信息:" + wxMpUser.toString());
	// model.addAttribute("openid", wxMpUser.getOpenId());
	// return "html/index";
	// }

	// @RequestMapping("/today2")
	// public String today2(HttpServletRequest request, Model model)
	// throws WxErrorException {
	// logger.debug("跳转到/01/index.html");
	//
	// /* 用code能取得accesstoken,然后用accesstoken得到登录用户信息? */
	// String code = request.getParameter("code");
	// // AccessToken:用户的访问令牌
	// WxMpOAuth2AccessToken wxMpOAuth2AccessToken = WeChatConnector
	// .getMpService().oauth2getAccessToken(code);
	// // WxMpUser:微信用户信息
	// WxMpUser wxMpUser = WeChatConnector.getMpService().oauth2getUserInfo(
	// wxMpOAuth2AccessToken, null);
	// logger.debug("用户信息:" + wxMpUser.toString());
	// model.addAttribute("nickname", wxMpUser.getNickname());
	// return "today2";
	// }

	// /**今日派赠?
	// * 输入手机号得到提货码?
	// * (点击今日派赠按钮所连接的url)
	// * 点击今日派赠,传递该用户的openId*/
	// @RequestMapping("/today3")
	// public String today3(HttpServletRequest request, Model model) throws
	// WxErrorException {
	// logger.debug("调/today3接口");
	// /*用code能取得accesstoken,然后用accesstoken得到登录用户信息?*/
	// String code = request.getParameter("code");
	// //AccessToken:用户的访问令牌
	// WxMpOAuth2AccessToken wxMpOAuth2AccessToken = WeChatConnector
	// .getMpService().oauth2getAccessToken(code);
	// //WxMpUser:微信用户信息
	// WxMpUser wxMpUser = WeChatConnector.getMpService()
	// .oauth2getUserInfo(wxMpOAuth2AccessToken, null);
	//
	// String openId = wxMpUser.getOpenId();
	// /*传递openId*/
	// model.addAttribute("openid", openId);
	// return "today";
	// }

	/** 得到提货码 */
	@RequestMapping("/coupon")
	public String getCoupon(HttpServletRequest request, Model model)
			throws Exception {

		String openId = request.getParameter("openid");
		logger.debug("获取提货码 openId:" + openId);
		/* 判断是否已被注册 */
		User user = userService.findOneByOpenId(openId);
		if (user != null) {
			/* 已被注册 */
			logger.debug("------userId:" + user.getId());
			model.addAttribute("msg", "您已领取过提货码:");
			model.addAttribute("coupon", user.getCoupons().get(0).getCode());
			return "2015070901_ad/success";
		} else {
			/* 没被注册,绑定一个提货码 */
			Coupon coupon = userService.registerActivity(
					"" + System.currentTimeMillis(), openId);
			logger.debug("------coupon:" + coupon.getCode());
			model.addAttribute("msg", "恭喜，您获得一枚提取码:");
			model.addAttribute("coupon", coupon.getCode());
			return "2015070901_ad/success";
		}
	}

	/** 输入用户信息得到提货码 */
	@RequestMapping("/register")
	public String register(HttpServletRequest request, Model model)
			throws Exception {

		String mobile = request.getParameter("username");
		String openId = request.getParameter("openid");

		logger.info("mobile: " + mobile + " openId: " + openId);
		/* 判断是否已被注册 */
		if (!userService.verfiyMobileAndOpenId(mobile, openId))
			/* 已被注册 */
			return "fail";
		else {
			/* 没被注册,绑定一个提货码 */
			Coupon coupon = userService.registerActivity(mobile, openId);
			model.addAttribute("coupon", coupon.getCode());
			return "succeed";
		}
	}

	/**
	 * 查询赠卷? (查询赠卷按钮连接的url)
	 */
	@RequestMapping("/query")
	public String query(HttpServletRequest request, Model model)
			throws Exception {
		/* 通过code得到登录用户信息 */
		String code = request.getParameter("code");

		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = WeChatConnector
				.getMpService().oauth2getAccessToken(code);
		WxMpUser wxMpUser = WeChatConnector.getMpService().oauth2getUserInfo(
				wxMpOAuth2AccessToken, null);

		String openId = wxMpUser.getOpenId();

		Coupon coupon = userService.queryActivity(openId);
		if (null == coupon)
			return "fail";
		else {
			model.addAttribute("coupon", coupon.getCode());
			return "succeed";
		}
	}

	/**
	 * 短信验证 从前端得到正确的验证码和手机号,并向该手机发送验证短信
	 */
	@RequestMapping("/notify")
	public void notify(@RequestParam("mobile") String mobile,
			@RequestParam("message") String message,
			HttpServletResponse response) throws IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		// 发送验证短信
		String result = SmsHelper.send(mobile, message);

		response.getWriter().println(result);
	}

	@RequestMapping("/today/{id}")
	public String today(@PathVariable("id") Long id,
			HttpServletRequest request, Model model) throws WxErrorException {
		logger.debug("跳转活动,活动id=" + id);
		/* 用code能取得accesstoken,然后用accesstoken得到登录用户信息? */
		String code = request.getParameter("code");
		// AccessToken:用户的访问令牌
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = WeChatConnector
				.getMpService().oauth2getAccessToken(code);
		// WxMpUser:微信用户信息
		WxMpUser wxMpUser = WeChatConnector.getMpService().oauth2getUserInfo(
				wxMpOAuth2AccessToken, null);
		model.addAttribute("nickname", wxMpUser.getNickname());
		Activity activity = activityService.findOneById(id);
		if (activity != null) {
			/*保存wcUser并且不重复记录*/
			WCUser user=wcUserService.findOneByOpenId(wxMpUser.getOpenId());
			if(user==null){
				WCUser wcUser = new WCUser(wxMpUser.getOpenId(),
						wxMpUser.getNickname(), wxMpUser.getSex(),
						wxMpUser.getCity(), wxMpUser.getProvince(),
						wxMpUser.getCountry(), wxMpUser.getUnionId());
				user = wcUserService.save(wcUser);
			}
			/*检测该用户是否参加过该活动*/
			boolean j=true;
			if(activity.getWcUsers().size()>0){
				for(WCUser wcUser1:activity.getWcUsers()){
					if(wcUser1.getId()==user.getId()){
						logger.debug("该用户已经浏览过该活动");
						j=false;
						break;
					}
				}
			}
			if(j==true){
				logger.debug("该活动人数加1");
				activity.getWcUsers().add(user);
				activityService.save(activity);
			}
			logger.debug("跳转页面:" + activity.getFolderName() + "/"
					+ activity.getIndexName());
			return activity.getFolderName() + "/" + activity.getIndexName();
		} else {
			return "error";
		}
	}
	// @RequestMapping("/test/today/{id}")
	// public String testSend(@PathVariable("id") Long id, HttpServletRequest
	// request,
	// Model model) throws WxErrorException {
	// Activity activity=activityService.findOneById(id);
	// if(activity!=null){
	// logger.debug("跳转页面:"+activity.getFolderName()+"/"+activity.getIndexName());
	// return activity.getFolderName()+"/"+activity.getIndexName();
	// }else{
	// return "error";
	// }
	// }

}
