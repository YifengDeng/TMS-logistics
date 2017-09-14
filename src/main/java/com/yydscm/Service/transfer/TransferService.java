package com.yydscm.Service.transfer;

import com.google.common.base.Strings;
import com.yydscm.Dao.Billing.BillingDao;
import com.yydscm.Dao.BillingLog.BillingLogDao;
import com.yydscm.Dao.Transfer.TransferDao;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.PageUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by PC20170414 on 2017/7/18.
 */

@Service
public class TransferService {

    @Autowired
    TransferDao transferDao;
    @Autowired
    BillingDao billingDao;
    @Autowired
    BillingLogDao billingLogDao;


    /**
     * 保存中转信息
     *
     * @param map
     * @return
     */
    @Transactional
    public Result<?> saveTransfer(Map<String, Object> map) {
        if (Strings.isNullOrEmpty(HsUtil.noAttribute("to_point_uuid", map))) {
            map.remove("to_point_uuid");
        }
        Long key = transferDao.saveTransfer(map);
        Map<String, Object> transferMap = new HashMap<>();
        transferMap.put("uuid", HsUtil.noAttribute("billing_uuid", map));
        transferMap.put("invoice_status", 6);
        transferMap.put("transit_destination", HsUtil.noAttribute("transit_destination", map));
        int flag = 0;
        if (key != null) {
            flag = billingDao.updateBillingOFInvoiceStatus("invoice_status", transferMap);
//            flag = billingDao.updateBilling(transferMap);
            if (flag > 0) {
                //到货信息待加入日志
                String uuids = HsUtil.noAttribute("billing_uuid", map);
                String[] uuid = uuids.split(",");
                Map[] billingLogMaps = new Map[uuid.length];
                //组装日志信息
                for (int i = 0; i < uuid.length; i++) {
                    Map<String, Object> billingLogMap = new HashMap<>();
                    billingLogMap.put("billing_uuid", uuid[i]);
                    billingLogMap.put("point_uuid", HsUtil.noAttribute("point_uuid", map));
                    billingLogMap.put("operator_date", new Date());
                    billingLogMap.put("operator_uuid", HsUtil.noAttribute("operator_id", map));
                    billingLogMap.put("billing_status", 6);
                    billingLogMap.put("billing_remark", "中转");
                    billingLogMaps[i] = billingLogMap;
                }
                billingLogDao.batchInsert(billingLogMaps);
                return ResultUtil.success(flag);
            }
        }
        return ResultUtil.success(flag);
    }

    /**
     * 修改中转信息
     *
     * @param map
     * @return
     */
    @Transactional
    public Result<?> updateTransfer(Map<String, Object> map) {
        int flag = transferDao.updateTransfer(map);
        return ResultUtil.success(flag);
    }

    /**
     * 中转信息列表
     *
     * @param map
     * @return
     */
    public Result<?> selectTransfer(Map<String, Object> map) {
        String pageSizeStr = HsUtil.noAttribute("pageSize", map);
        String pageStr = HsUtil.noAttribute("page", map);
        int pagesize = Integer.parseInt((Objects.nonNull(pageSizeStr) && (Objects.equals(pageSizeStr, ""))) ? pageSizeStr : "10");
        int page = Integer.parseInt((Objects.nonNull(pageStr) && (Objects.equals(pageStr, ""))) ? pageStr : "1");
        List<Map<String, Object>> list = transferDao.selectTransfer(map);
        Map<String, Object> resultMap = PageUtil.pageMethod(pagesize, page, list);
        return ResultUtil.success(resultMap);
    }

    /**
     * 获取平台内所有网点，除了登录网点
     *
     * @param map
     * @return
     */
    public Result<?> getPointForNotOwn(Map<String, Object> map) {
        List<Map<String, Object>> list = transferDao.getPointForNotOwn(map);
        return ResultUtil.success(list);
    }

    public static void main(String[] args) {
        String fileName = "aa.sql";
        String path = TransferService.class.getResource("").toString();
        System.out.println(path);
        if (path != null) {
            path = path.substring(5, path.indexOf("WEB-INF") + 8);//如果是windwos将5变成6
            //System.out.println("current path :" + path);
        }
        System.out.println(path + fileName);
    }



}
