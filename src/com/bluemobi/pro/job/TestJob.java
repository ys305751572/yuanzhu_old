package com.bluemobi.pro.job;

import com.bluemobi.utils.AbstractJob;

public class TestJob extends AbstractJob{
	
	@Override
	public void execute() {
		System.out.println("==============executeJob===============");
	}

}
