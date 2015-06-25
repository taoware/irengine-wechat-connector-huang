package com.irengine.wechat.connector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.wechat.connector.domain.Coupon;
import com.irengine.wechat.connector.domain.User;
import com.irengine.wechat.connector.repository.CouponDao;
import com.irengine.wechat.connector.repository.UserDao;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private CouponDao couponDao;
	
	/**验证该用户是否已经存在,如果存在返回false,不存在返回true(是否领取过提货码)*/
	public boolean verfiyMobileAndOpenId(String mobile, String openId) {
		
		List<User> mobileUsers = userDao.findByMobile(mobile);
		List<User> openIdUsers = userDao.findByMobile(mobile);
		if (mobileUsers.size() > 0 || openIdUsers.size() > 0)
			return false;

		return true;
	}
	
	/**注册用户(记录下该用户,防止重复领取提货码),并返回一个提货码*/
	public Coupon registerActivity(String mobile, String openId) throws Exception {
		
		User user = new User(mobile, openId);
		/*取一个unused状态的提货码给user,并把这个提货码标注为used*/
		List<Coupon> coupons = couponDao.findByCategoryAndStatus(1L, Coupon.STATUS.Unused); 
		/*可用的提货码已空,抛异常*/
		if (0 == coupons.size()) throw new Exception("code unavailable");
		/*得到查找到的第一个提货码*/
		Coupon coupon = coupons.get(0);
		/*多对多关系*/
		user.getCoupons().add(coupon);
		userDao.save(user);
		/*该条提货码状态改为"已被使用"*/
		coupon.setStatus(Coupon.STATUS.Used);
		couponDao.save(coupon);

		return coupon;
	}
	
	/**返回该用户的提货码*/
	public Coupon queryActivity(String openId) {
		
		List<User> users = userDao.findByOpenId(openId);
		
		if (users.size() > 0) {
			User user = users.get(0);
			if (null != user.getCoupons() && user.getCoupons().size() > 0)
				return user.getCoupons().get(0);
		}
		
		return null;
	}

	public User findOneByOpenId(String openId) {
		User user=null;
		List<User> users=userDao.findByOpenId(openId);
		if(users.size()>0){
			user=users.get(0);
		}
		return user;
	}
	
}
