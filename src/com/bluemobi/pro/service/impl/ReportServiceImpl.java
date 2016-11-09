package com.bluemobi.pro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Report;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;

/**
 * @author TuYiHeng
 * 
 */
@Service
public class ReportServiceImpl extends BaseService {

    public static String PREFIX = Report.class.getName();

    public Page<List<Report>> list(Map<String, Object> params, int current, int pagesize) {
        return this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
    }

    public int save(Map<String, Object> params) throws Exception {
        return this.getBaseDao().save(PREFIX + ".insert", params);
    }
}