package com.zxdy.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.zxdy.dao.LoginLogDao;
import com.zxdy.dao.UserDao;
import com.zxdy.domain.LoginLog;
import com.zxdy.domain.User;

public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginLogDao loginLogDao;
	
	public boolean hasMatchUser(String userName,String password){
		int matchCount=userDao.getMatchCount(userName, password);
		return matchCount>0;
	}
	public User findUserByUserNmae(String userName){
		return userDao.findUserByUserNmae(userName);
	}
	public void loginSuccess(User user){
		user.setCredits(5 + user.getCredits());
		LoginLog loginLog=new LoginLog();
		loginLog.setUserId(user.getUserId());
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(user.getLastVisit());
		userDao.updateLoginInfo(user);
		loginLogDao.insertLoginLog(loginLog);
	}
}
