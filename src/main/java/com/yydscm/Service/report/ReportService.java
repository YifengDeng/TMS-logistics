package com.yydscm.Service.report;

import com.yydscm.Dao.Billing.BillManagerDao;
import com.yydscm.Dao.report.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportDao reportDao;
    @Autowired
    private BillManagerDao billManagerDao;


    //营业报表，按按月查询或查询当天记录
    public List<Map<String, Object>> selectReportList(Map<String, Object> map, Map<String, Object> user) {

        map.put("company_uuid", user.get("company_uuid"));

        return billManagerDao.selectReportList(map);
    }

    //查询某一天的开票记录
    public List<Map<String, Object>> selectReportListByDay(Map<String, Object> map, Map<String, Object> user) {
        map.put("company_uuid", user.get("company_uuid"));

        return reportDao.selectReportListByDay(map);
    }

}
