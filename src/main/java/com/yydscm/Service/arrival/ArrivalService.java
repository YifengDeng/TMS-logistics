package com.yydscm.Service.arrival;

import com.yydscm.Dao.Billing.BillingDao;
import com.yydscm.Dao.BillingLog.BillingLogDao;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class ArrivalService {

    @Autowired
    BillingDao billingDao;
    @Autowired
    BillingLogDao billingLogDao;


    /**
     * 到货确认
     * invoice_status 状态
     * uuids 运单uuid集合，以“,”隔开
     *
     * @param map
     * @return
     */
    @Transactional
    public Result<?> updateBillingOFInvoiceStatus(Map<String, Object> map) {
        map.put("invoice_status", 4);
        int flag = billingDao.updateBillingOFInvoiceStatus("invoice_status", map);
        if (flag > 0) {
            //到货信息待加入日志
            String uuids = HsUtil.noAttribute("uuid", map);
            String[] uuid = uuids.split(",");
            Map[] billingLogMaps = new Map[uuid.length];
            //组装日志信息
            for (int i = 0; i < uuid.length; i++) {
                Map<String, Object> billingLogMap = new HashMap<>();
                billingLogMap.put("billing_uuid", uuid[i]);
                billingLogMap.put("point_uuid", HsUtil.noAttribute("point_uuid", map));
                billingLogMap.put("operator_date", new Date());
                billingLogMap.put("operator_uuid", HsUtil.noAttribute("operator_uuid", map));
                billingLogMap.put("billing_status", HsUtil.noAttribute("invoice_status", map));
                billingLogMap.put("billing_remark", "已到货");
                billingLogMaps[i] = billingLogMap;
            }
            billingLogDao.batchInsert(billingLogMaps);
            return ResultUtil.success(flag);
        }
        return ResultUtil.success();
    }


}
