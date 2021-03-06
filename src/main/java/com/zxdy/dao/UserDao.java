package com.zxdy.dao;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Repository;

import com.zxdy.domain.User;


@Repository
public class UserDao extends BaseDao{
	public JSONArray getMatchCount(String userName,String password){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("password", password);
		return returnData("getMatchCount", params,getJsonConfig());
	}
	public JSONArray findUserByUserNmae(final String userName){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		return returnData("findUserByUserNmae", params,getJsonConfig());
		
	}
	public void updateLoginInfo(User user){
		
	}
}
