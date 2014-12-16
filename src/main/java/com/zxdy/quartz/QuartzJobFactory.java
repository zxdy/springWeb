package com.zxdy.quartz;

import java.util.Date;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("QuartzJobFactory")
public class QuartzJobFactory implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
	}
	
}
