package com.zxdy.dao;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Repository;

import com.zxdy.domain.User;


@Repository
public class PoolDao extends BaseDao{
	public JSONArray getPoolList(String serverType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serverType", serverType);
		return returnData("getPoolList", params,getJsonConfig());
	}
}
