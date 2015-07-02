package com.irengine.wechat.connector.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.wechat.connector.WeChatConnector;
import com.irengine.wechat.connector.domain.Activity;
import com.irengine.wechat.connector.domain.OutMessage;
import com.irengine.wechat.connector.repository.ActivityDao;
import com.irengine.wechat.util.DealWithMenuJson;

@Service
@Transactional
public class ActivityService {

	private static final Logger logger = LoggerFactory
			.getLogger(ActivityService.class);
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private OutMessageService outMessageService;
	
	public Activity save(Activity activity) {
		Activity returnActivity=activityDao.save(activity);
		return returnActivity;
	}

	public List<Activity> findAll() {
		return (List<Activity>) activityDao.findAll();
	}

	public Activity findOneById(Long id) {
		return activityDao.findOne(id);
	}

	@Scheduled(cron = "0 5 0 * * ?")
	public void updateMenu() throws UnsupportedEncodingException {
		/* 检测所有活动 */
		List<Activity> activitys = findAll();
		List<OutMessage> messages = outMessageService.findAll();
		/* 时间符合即更新到第一个以及菜单按钮 */
		Date now = new Date();
		// 待添加的活动
		List<Activity> addActivitys = new ArrayList<Activity>();
		List<OutMessage> addMessages = new ArrayList<OutMessage>();
		/* 查找要正在实行的活动 */
		for (Activity activity : activitys) {
			// 结束日期加一天
			Date endDate = activity.getEndDate();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endDate);
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			endDate = calendar.getTime();
			if (!activity.isDisable()
					&& (now.after(activity.getStartDate()) || now
							.equals(activity.getStartDate()))
					&& (now.before(endDate) || now.equals(endDate))) {
				addActivitys.add(activity);
			}
		}
		/* 查找正在实行的推送 */
		for (OutMessage message : messages) {
			// 结束日期加一天
			Date endDate = message.getEndDate();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endDate);
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			endDate = calendar.getTime();
			if (!message.isDisable()
					&& (now.after(message.getStartDate()) || now.equals(message
							.getStartDate()))
					&& (now.before(endDate) || now.equals(endDate))) {
				addMessages.add(message);
			}
		}
		/* 生成菜单文件 */
		String menuJson = DealWithMenuJson.setActivity(addActivitys,
				addMessages);
		/* 更新菜单 */
		InputStream isMenu = new ByteArrayInputStream(
				menuJson.getBytes("UTF-8"));
		try {
			WeChatConnector.getMpService().menuDelete();
			WxMenu menu = WxMenu.fromJson(isMenu);
			WeChatConnector.getMpService().menuCreate(menu);
			logger.info("create menu succeed.");
		} catch (WxErrorException e) {
			logger.error("create menu failed.");
		}
	}

}








