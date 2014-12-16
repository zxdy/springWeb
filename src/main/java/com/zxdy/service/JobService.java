package com.zxdy.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.zxdy.dao.TaskDao;
import com.zxdy.domain.ScheduledJob;
import com.zxdy.quartz.QuartzJobFactory;

@Service("JobService")
@Path(value = "/job")
public class JobService{
	
	@Autowired
	private TaskDao taskDao;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/1.0/add")
	public String addNewTask(
			@DefaultValue("") @FormParam("jobName") String jobName,
			@DefaultValue("") @FormParam("jobGroup") String jobGroup,
			@DefaultValue("") @FormParam("poolName") String poolName,
			@DefaultValue("") @FormParam("scheduleTime") String scheduleTime,
			@DefaultValue("") @FormParam("jobDesc") String jobDesc) {
		Boolean result=taskDao.addNewTask(jobName,jobGroup,poolName,scheduleTime,jobDesc);
		return result.toString();
	}
}
