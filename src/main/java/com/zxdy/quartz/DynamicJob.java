package com.zxdy.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Service("DynamicJob")
public class DynamicJob implements Job {
	
	@Autowired
	private TaskDao taskDao;

	private static Scheduler scheduler;
	public DynamicJob() throws SchedulerException{
		if(scheduler==null){
			scheduler = new StdSchedulerFactory().getScheduler();
			System.out.print("schedulerschedulerschedulerscheduler");
		}
	}
	public  Map<String, ScheduledJob> initTasks() {
		Map<String, ScheduledJob> jobMap = new HashMap<String, ScheduledJob>();
		JSONArray taskList = new JSONArray();
		taskList = taskDao.getScheduledTask();
		if (taskList != null && taskList.size() > 0) {
			for (int i = 0; i < taskList.size(); i++) {
				ScheduledJob job = new ScheduledJob();
				String jobId = taskList.getJSONObject(i).getString("JOBID");
				String jobName = taskList.getJSONObject(i).getString("JOBNAME");
				String jobGroup = taskList.getJSONObject(i).getString(
						"JOBGROUP");
				String jobStatus = taskList.getJSONObject(i).getString(
						"JOBSTATUS");
				String cronExpression = taskList.getJSONObject(i).getString(
						"CRON");
				String jobDesc = taskList.getJSONObject(i).getString("JOBDEC");
				job.setJobId(jobId);
				job.setJobName(jobName);
				job.setJobGroup(jobGroup);
				job.setJobStatus(jobStatus);
				job.setCronExpression(cronExpression);
				job.setDesc(jobDesc);
				jobMap.put(job.getJobGroup() + "_" + job.getJobName(),
						job);
			}
		}
		return jobMap;
	}


	public static List<ScheduledJob> getAllJobFromDb(Map<String, ScheduledJob> jobMap) {
		List<ScheduledJob> jobList = new ArrayList(jobMap.size());
		for (Map.Entry entry : jobMap.entrySet()) {
			jobList.add((ScheduledJob) entry.getValue());
		}
		return jobList;
	}

	public void execute(JobExecutionContext context) {

		try {
			ApplicationContext  ctx=null;
			
			try {
				ctx = (ApplicationContext) context.getScheduler().getContext()
						.get("applicationContext");
			} catch (SchedulerException e) {
			}
			// get job info
			List<ScheduledJob> jobList = getAllJobFromDb(initTasks());

			for (ScheduledJob job : jobList) {

				TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
						job.getJobGroup());
				// get trigger
				CronTrigger trigger = (CronTrigger) scheduler
						.getTrigger(triggerKey);

				// create one if not exists
				if (null == trigger) {
					JobDetail jobDetail = JobBuilder
							.newJob(QuartzJobFactory.class)
							.withIdentity(job.getJobName(), job.getJobGroup())
							.build();
					jobDetail.getJobDataMap().put("scheduleJob", job);

					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
							.cronSchedule(job.getCronExpression());

					trigger = TriggerBuilder.newTrigger()
							.withIdentity(job.getJobName(), job.getJobGroup())
							.withSchedule(scheduleBuilder).build();
					scheduler.start();
					scheduler.scheduleJob(jobDetail, trigger);
				} else {
//					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
//							.cronSchedule(job.getCronExpression());
//
//					trigger = trigger.getTriggerBuilder()
//							.withIdentity(triggerKey)
//							.withSchedule(scheduleBuilder).build();
//
//					scheduler.start();
//					scheduler.rescheduleJob(triggerKey, trigger);
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void deleteJob(String jobName,String jobGroup) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		scheduler.deleteJob(jobKey);
	}
	
	
	
	public JSONArray getAllJobFromMem() throws SchedulerException {
		JSONArray jobList=new JSONArray();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				JSONObject jobObj=new JSONObject();
				String jobName = jobKey.getName();
				String jobGroup = jobKey.getGroup();
				List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
				Date nextFireTime = triggers.get(0).getNextFireTime();
				System.out.println("[jobName] : " + jobName + " [groupName] : "
						+ jobGroup + " - " + df.format(nextFireTime));
				try {
					jobObj.put("JOBNAME",jobName);
					jobObj.put("JOBGROUP",jobGroup);
					jobObj.put("NEXTFIRETIME",df.format(nextFireTime));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				jobList.add(jobObj);
			}
		}
		return jobList;
	}
}
