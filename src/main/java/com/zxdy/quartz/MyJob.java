package com.zxdy.quartz;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service("MyJob")
public class MyJob {

	public void runMyJob() {
		Date dateNow=new Date();
		System.out.print("runing" + dateNow);	
	}
}
