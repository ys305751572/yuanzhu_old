package com.bluemobi.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * <p>Title: AbstractJob.java</p> 
 * <p>Description 抽象时间任务JOB:</p>
 * @author Administrator 
 * @date 2015年5月15日 下午2:14:23
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public abstract class AbstractJob implements Job{

	public abstract void execute() ;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		execute();
	}
}
