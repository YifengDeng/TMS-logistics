package com.yydscm.Service.loadingService;

import com.google.common.base.Strings;
import com.yydscm.Dao.Billing.BillingDao;
import com.yydscm.Dao.BillingLog.BillingLogDao;
import com.yydscm.Dao.loading.LoadingDao;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LoadingService {

    @Autowired
    LoadingDao loadingDao;
    @Autowired
    BillingDao billingDao;
    @Autowired
    BillingLogDao billingLogDao;


    /**
     * 保存装车信息
     *
     * @param map
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Transactional
    public Result<?> saveLoadingBatch(Map<String, Object> map) {
        int log = 0;
        Long key = null;
        int flag = 0;
        //无“批次ID”,进入装车环节
        if (Strings.isNullOrEmpty(HsUtil.noAttribute("loading_batches_id", map))) {
            //批次号规则：网点id + “yyMMdd” + 序号
            map.put("loading_time", new Date());
            Map<String, Object> loadMap = loadingDao.selectMaxLoadingBatchOfSort(map);
            int sort = 0;
            try {
                sort = (Objects.isNull(loadMap) || loadMap.entrySet().isEmpty()) ? 1 : Integer.parseInt(loadMap.get("sort").toString());
            } catch (Exception e) {
            }
            StringBuilder batch_number = new StringBuilder();
            batch_number.append(map.get("loading_point").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            batch_number.append(sdf.format(new Date()));
            batch_number.append(String.format("%03d", sort + 1));
            System.err.println("批次号：" + batch_number.toString());
            map.put("batch_number", batch_number.toString());
            map.put("sort", sort + 1);
            //装车
            key = loadingDao.addLoadingBatch(map);
            if (Objects.nonNull(key)) {
                flag = 1;
            }
        } else {
            log = 1;
            flag = loadingDao.updateLoadingBatch(map);
        }

        String uuids = HsUtil.noAttribute("uuid", map);
        if (flag > 0) {
            Map<String, Object> billingMap = new HashMap<>();
            billingMap.put("uuid", uuids);
            if (log > 0) {
                billingMap.put("invoice_status", 3);
                flag = billingDao.updateBillingOFLoadingBatchsIdForGo(billingMap);
            } else {
                billingMap.put("loading_batches_id", key);
                billingMap.put("invoice_status", 2);
                flag = billingDao.updateBillingOFLoadingBatchsIdByUuid(billingMap);
            }
            String[] uuid = uuids.split(",");
            Map[] billingLogMaps = new Map[uuid.length];
            //组装日志信息
            for (int i = 0; i < uuid.length; i++) {
                Map<String, Object> billingLogMap = new HashMap<>();
                billingLogMap.put("billing_uuid", uuid[i]);
                billingLogMap.put("point_uuid", HsUtil.noAttribute("loading_point", map));
                billingLogMap.put("operator_date", new Date());
                billingLogMap.put("operator_uuid", HsUtil.noAttribute("operator_uuid", map));
                if (log > 0) {
                    billingLogMap.put("billing_status", 3);
                    billingLogMap.put("billing_remark", "发车");
                } else {
                    billingLogMap.put("billing_status", 2);
                    billingLogMap.put("billing_remark", "装车");
                }
                billingLogMaps[i] = billingLogMap;
            }
            billingLogDao.batchInsert(billingLogMaps);
            List<Map<String, Object>> list = billingDao.selectBillingByUuid(map);
            return ResultUtil.success(list);
        }
        return ResultUtil.success();
    }


    /**
     * 移除装车
     *
     * @param map
     * @return
     */
    @Transactional
    public Result<?> removeLoadingBatch(Map<String, Object> map) {
        //改运单状态、加日志
        String uuids = HsUtil.noAttribute("uuid", map);
        Map<String, Object> billingMap = new HashMap<>();
        billingMap.put("uuid", uuids);
        billingMap.put("loading_batches_id", null);
        billingMap.put("invoice_status", 1);
        int flag = billingDao.updateBillingOFLoadingBatchsIdByUuid(billingMap);
        String[] uuid = uuids.split(",");
        Map[] billingLogMaps = new Map[uuid.length];
        //组装日志信息
        for (int i = 0; i < uuid.length; i++) {
            Map<String, Object> billingLogMap = new HashMap<>();
            billingLogMap.put("billing_uuid", uuid[i]);
            billingLogMap.put("point_uuid", HsUtil.noAttribute("loading_point", map));
            billingLogMap.put("operator_date", new Date());
            billingLogMap.put("operator_uuid", HsUtil.noAttribute("operator_uuid", map));
            billingLogMap.put("billing_status", 1);
            billingLogMap.put("billing_remark", "取消装车");
            billingLogMaps[i] = billingLogMap;
        }
        flag = billingLogDao.batchInsert(billingLogMaps);
        return ResultUtil.success(flag);
    }


    /**
     * APP端装车前获取数据
     *
     * @param map
     * @return
     */
    public Result<?> selectBillingForLoading(Map<String, Object> map) {
        List<Map<String, Object>> list = billingDao.selectBillingForLoading(map);
        if (list.size() > 0) {
//			Map<String, List<Map<String, Object>>> employeesByCity = 
//					list.stream().collect(Collectors.groupingBy(Map->Map.get("terminal_station_id").toString()));
//			ListMultimap<String, Object> listMultimap=ArrayListMultimap.create();
//			list.forEach(Map -> {
//			    listMultimap.put(Map.get("terminal_station_id")+"", Map);
//			});
            return ResultUtil.success(list);
        }
        return ResultUtil.success();
    }

//	/**
//	 * 到货确认需要的装车信息
//	 * @param map
//	 * @return
//	 */
//	@PostMapping("/selectSignInfo")
//	public Result<?> selectLoadingInfo(Map<String, Object> map){
//		List<Map<String, Object>> list = loadingDao.selectLoadingInfo(map);
//		if (list.size() > 0) {
//			return ResultUtil.success(list);
//		}
//		return  ResultUtil.success();
//	}


}
