package com.yydscm.Service.sign;

import com.yydscm.Dao.Billing.BillingDao;
import com.yydscm.Dao.BillingLog.BillingLogDao;
import com.yydscm.Dao.sign.SignDao;
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
public class SignService {

    @Autowired
    SignDao signDao;
    @Autowired
    BillingDao billingDao;
    @Autowired
    BillingLogDao billingLogDao;


    /**
     * 出库签收
     * invoice_status 状态
     * uuids 运单uuid集合，以“,”隔开
     *
     * @param map
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Transactional
    public Result<?> updateBillingOFInvoiceStatus(Map<String, Object> map) {
        map.put("invoice_status", 5);
        int flag = billingDao.updateBillingOFInvoiceStatus("invoice_status", map);
        if (flag > 0) {
            String uuids = HsUtil.noAttribute("uuid", map);
            String[] uuid = uuids.split(",");
            //保存签收信息
            Map[] signMaps = new Map[uuid.length];
            for (int i = 0; i < uuid.length; i++) {
                Map<String, Object> signMap = new HashMap<>();
                signMap.put("billing_uuid", uuid[i]);
                signMap.put("receipts_delivery_fee", HsUtil.noAttribute("receipts_delivery_fee", map));    //实收送货费
                signMap.put("receipts_total_freight", HsUtil.noAttribute("receipts_total_freight", map));    //实收运费
                signMap.put("receipts_collection_fee", HsUtil.noAttribute("receipts_collection_fee", map));    //实收代收货款
                signMap.put("sign_man", HsUtil.noAttribute("sign_man", map));    //签收人
                signMap.put("sign_man_idno", HsUtil.noAttribute("sign_man_idno", map));    //签收人证件号码
                signMap.put("point_uuid", HsUtil.noAttribute("point_uuid", map));
                signMap.put("point_operator", HsUtil.noAttribute("point_operator", map));
                signMap.put("sign_date", new Date());
                signMap.put("remark", HsUtil.noAttribute("remark", map));
                signMaps[i] = signMap;
            }
            signDao.batchInsert(signMaps);

            //签收信息待加入日志
            Map[] billingLogMaps = new Map[uuid.length];
            for (int i = 0; i < uuid.length; i++) {
                Map<String, Object> billingLogMap = new HashMap<>();
                billingLogMap.put("billing_uuid", uuid[i]);
                billingLogMap.put("point_uuid", HsUtil.noAttribute("point_uuid", map));
                billingLogMap.put("operator_date", new Date());
                billingLogMap.put("operator_uuid", HsUtil.noAttribute("point_operator", map));
                billingLogMap.put("billing_status", 5);
                billingLogMap.put("billing_remark", "已签收");
                billingLogMaps[i] = billingLogMap;
            }
            billingLogDao.batchInsert(billingLogMaps);
            return ResultUtil.success(flag);
        }
        return ResultUtil.success();
    }


}
