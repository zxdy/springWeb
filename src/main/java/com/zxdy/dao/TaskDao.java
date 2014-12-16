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


	public boolean addNewTask(String jobName, String jobGroup,
			String poolName, String scheduleTime, String jobDesc) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobName", jobName);
		params.put("jobGroup", jobGroup);
		params.put("poolName", poolName);
		params.put("scheduleTime", scheduleTime);
		params.put("jobDesc", jobDesc);
		return insertData("addNewTask", params);
	}
}
