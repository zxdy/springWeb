package com.zxdy.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Service("QuartzJob")
public class QuartzJob implements Job {
	
	public void execute(JobExecutionContext context) {
		Date dateNow=new Date();
		System.out.print("runing" + dateNow);
	}
}
