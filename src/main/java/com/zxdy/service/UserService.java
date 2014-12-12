package com.zxdy.service;


import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxdy.dao.LoginLogDao;
import com.zxdy.dao.UserDao;
import com.zxdy.domain.LoginLog;
import com.zxdy.domain.User;
@Service("UserService")
@Path(value = "/user")
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginLogDao loginLogDao;
	
	public boolean hasMatchUser(String userName,String password){
		JSONArray matchCountArr=new JSONArray();
		int matchCount=0;
		matchCountArr=userDao.getMatchCount(userName, password);
		if (matchCountArr != null && matchCountArr.size() > 0) {
			JSONObject tempItem = matchCountArr.getJSONObject(0);
			matchCount=tempItem.getInt("COUNT");
		}
		return matchCount>0;
	}
	public User findUserByUserNmae(String userName){
		JSONArray userArr=new JSONArray();
		final User user=new User();
		userArr= userDao.findUserByUserNmae(userName);
		if (userArr != null && userArr.size() > 0) {
			for (int i = 0; i < userArr.size(); i++) {
				JSONObject tempItem = userArr.getJSONObject(i);
					user.setUserId(tempItem.getInt("USER_ID"));
					user.setUserName(tempItem.getString("USERNAME").toString());
					user.setCredits(tempItem.getInt("CREDITS"));
			}
		}
		return user;
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/1.0/helloworld")
	public String helloWorld(
			@DefaultValue("01/01/1970") @FormParam("startDate") String startDate,
			@DefaultValue("01/01/1970") @FormParam("endDate") String endDate,
			@DefaultValue("50") @FormParam("count") int count) {
		String result="hello world!";
		return result.toString();
	}
}
