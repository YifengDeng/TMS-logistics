package com.yydscm.Service.unusual;

import com.google.common.base.Strings;
import com.yydscm.Dao.Billing.BillingDao;
import com.yydscm.Dao.BillingLog.BillingLogDao;
import com.yydscm.Dao.unusual.UnusualDao;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.PageUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UnusualService {

    @Autowired
    UnusualDao unusualDao;
    @Autowired
    BillingDao billingDao;
    @Autowired
    BillingLogDao billingLogDao;


    /**
     * 保存异常信息
     *
     * @param map
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Transactional
    public Result<?> saveUnusual(Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("uuid", map);
        String billing_uuids = HsUtil.noAttribute("billing_uuid", map);
        int flag = 0;
//        String[] uuid = Objects.nonNull(uuids) ? uuids.split(",") : null;
        String[] billing_uuid = Objects.nonNull(billing_uuids) ? billing_uuids.split(",") : null;
        Map<String, Object> unusualMap = new HashMap<>();
        if (Objects.isNull(uuids)) {
            map.put("billing_uuid", billing_uuids);
            Map[] unusualMaps = new Map[billing_uuid.length];
            for (int i = 0; i < billing_uuid.length; i++) {
                unusualMap.put("billing_uuid", billing_uuid[i]);
                unusualMap.put("record_date", new Date());
                unusualMap.put("record_point_uuid", HsUtil.noAttribute("record_point_uuid", map));    //登记网点id
                unusualMap.put("recorder_uuid", HsUtil.noAttribute("recorder_uuid", map));    //登记人id
                unusualMap.put("unusual_type", HsUtil.noAttribute("unusual_type", map));    //异常类型
                unusualMap.put("unusual_des", HsUtil.noAttribute("unusual_des", map));    //异常描述
                unusualMap.put("status", Objects.isNull(HsUtil.noAttribute("status", map)) ? "0" : HsUtil.noAttribute("status", map));
                unusualMaps[i] = unusualMap;
            }
            flag = unusualDao.batchInsert(unusualMaps);
            if (flag > 0) {
                Map[] billingLogMaps = new Map[billing_uuid.length];
                for (int i = 0; i < billing_uuid.length; i++) {
                    Map<String, Object> billingLogMap = new HashMap<>();
                    billingLogMap.put("billing_uuid", billing_uuid[i]);
                    billingLogMap.put("point_uuid", HsUtil.noAttribute("record_point_uuid", map));
                    billingLogMap.put("operator_date", new Date());
                    billingLogMap.put("operator_uuid", HsUtil.noAttribute("recorder_uuid", map));
                    billingLogMap.put("billing_status", 1);
                    billingLogMap.put("billing_remark", "运单异常");
                    billingLogMaps[i] = billingLogMap;
                }
                billingLogDao.batchInsert(billingLogMaps);
            }
        } else {
            unusualMap.put("uuid", HsUtil.noAttribute("uuid", map));
            unusualMap.put("processed_date", new Date());    //处理时间
            unusualMap.put("processor", HsUtil.noAttribute("recorder_uuid", map));    //处理人id
            unusualMap.put("processed_results", HsUtil.noAttribute("processed_results", map));    //处理结果
            unusualMap.put("status", Objects.isNull(HsUtil.noAttribute("status", map)) ? "1" : HsUtil.noAttribute("status", map));
            flag = unusualDao.updateUnusual(unusualMap);
            if (flag > 0) {
                Map<String, Object> mapNew = unusualDao.selectUnusualByUuid(unusualMap);
                billing_uuids = Objects.isNull(mapNew) ? null : mapNew.get("billing_uuid").toString();
            }
//            return ResultUtil.success(flag);
        }
        if (flag > 0) {
            int status = 1;
            if (Objects.equals(HsUtil.noAttribute("status", unusualMap), 1)) {
                status = 0;
            }
            map.put("is_unusual", status);
            map.put("uuid", billing_uuids);
            flag = billingDao.updateBillingOFInvoiceStatus("is_unusual", map);
            return ResultUtil.success(flag);
        }

        return ResultUtil.success();
    }

    /**
     * 获取异常信息列表
     *
     * @param map
     * @return
     */
    public Result<?> selectUnusual(Map<String, Object> map) {
        String pageSizeStr = HsUtil.noAttribute("pageSize", map);
        String pageStr = HsUtil.noAttribute("page", map);
        int pagesize = Integer.parseInt((Objects.nonNull(pageSizeStr) && (Objects.equals(pageSizeStr, ""))) ? pageSizeStr : "10");
        int page = Integer.parseInt((Objects.nonNull(pageStr) && (Objects.equals(pageStr, ""))) ? pageStr : "1");
        List<Map<String, Object>> list = unusualDao.selectUnusual(map);
        Map<String, Object> resultMap = PageUtil.pageMethod(pagesize, page, list);
        return ResultUtil.success(resultMap);
    }

}
