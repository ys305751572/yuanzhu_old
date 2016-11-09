package com.bluemobi.utils;

import java.text.ParseException;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.bluemobi.pro.job.TestJob;

/**
 * 
 * <p>Title: QuartzManager.java</p> 
 * <p>Description: 任务调度器</p> 
 * @author yesong 
 * @date 2015年5月13日 下午4:18:27
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class QuartzManager {
	
	/**
	 * 根据cron表达式添加定时任务
	 * @param jobName
	 * @param groupName
	 * @param triggerName
	 * @param cron
	 * @param job
	 */
	public static void addJob(String jobName,String groupName,String triggerName,String cron,Job job) {
		if(StringUtils.isBlank(jobName) || StringUtils.isBlank(triggerName) || StringUtils.isBlank(groupName) || StringUtils.isBlank(cron) || job== null) 
				throw new IllegalArgumentException("参数异常");
		
		// 用Scheduler Factory 创建调度器Scheduler 
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = sf.getScheduler();
			JobDetail jd = new JobDetail(jobName,groupName,job.getClass());
			CronTrigger trigger = new CronTrigger(triggerName,groupName,cron);
			
//			JobDetail jd = JobBuilder.newJob(job.getClass()).withIdentity(jobName, groupName).build();
//			CronTrigger trigger = TriggerBuilder.newTrigger() // 创建triggerBuiler
//								.withIdentity(triggerName, groupName)
//								.withSchedule(CronScheduleBuilder.cronSchedule(cron))
//								.build();
			scheduler.scheduleJob(jd, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		QuartzManager.addJob("jobName", "groupName", "triggerName","*/5 * * * * ?", new TestJob());
	}
}
