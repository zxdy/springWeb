package com.zxdy.dao;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Repository;

import com.zxdy.domain.User;


@Repository
public class TaskDao extends BaseDao{
	public JSONArray getScheduledTask(){
		Map<String, Object> params = new HashMap<String, Object>();
		return returnData("getScheduledTask", params,getJsonConfig());
	}
}
