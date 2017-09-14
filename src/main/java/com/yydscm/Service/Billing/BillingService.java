package com.yydscm.Service.Billing;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.yydscm.Dao.Billing.BillingDao;
import com.yydscm.Dao.BillingLog.BillingLogDao;
import com.yydscm.Entity.T_billing;
import com.yydscm.Enum.ResultEnum;
import com.yydscm.Util.DBUtil;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.PageUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 开票
 *
 * @author PC20170414
 */
@Service
public class BillingService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(BillingService.class);

    @Autowired
    BillingDao billingDao;
    @Autowired
    BillingLogDao billingLogDao;

    /**
     * 开票保存
     *
     * @param map
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Transactional
    public Result<?> saveBilling(Map<String, Object> map) {
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object value = entry.getValue();
            value += "";
            if (Objects.isNull(value) || Strings.isNullOrEmpty((String) value)) {
                iterator.remove();
            }
        }
        Long key = null;
        int flag = 0;
        try {
            key = Long.valueOf(map.get("uuid").toString());
        } catch (Exception e) {
            System.err.println("新增操作。。。。");
        }

        Map[] billingLogMaps = new Map[1];
        if (Objects.isNull(key)) {
            //生成运单号、货号
            String initial_station_id = HsUtil.noAttribute("initial_station", map);    //开票网点id
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            String timeStr = sdf.format(new Date());
            //获取网点当天最大运单号
            Map<String, Object> newMap = new HashMap<>();
            newMap.put("initial_station_id", HsUtil.noAttribute("initial_station", map));
            //获取网点最大运单号
            String maxNumber = null;
            try {
                maxNumber = billingDao.getMaxWaybillNumber(newMap);
            } catch (Exception e) {
                logger.info("运单号第一个");
            }
            String waybill_number = null;
            if (Strings.isNullOrEmpty(maxNumber)) {
                waybill_number = initial_station_id + timeStr + "001";
            } else {
                maxNumber = maxNumber.substring(maxNumber.length() - 3, maxNumber.length());
                waybill_number = initial_station_id + timeStr + String.format("%03d", (Integer.parseInt(maxNumber) + 1));
            }
            //货号
            String article_number = generateArticle_number(newMap, map);
            System.out.println("货号：" + article_number);

            map.put("waybill_number", waybill_number);
            map.put("article_number", article_number);
            map.put("initial_station_id", HsUtil.noAttribute("initial_station", map));
            map.put("owner", HsUtil.noAttribute("initial_station", map));
            map.put("drawer_uuid", HsUtil.noAttribute("drawer", map));
            if(Strings.isNullOrEmpty(HsUtil.noAttribute("invoice_date", map))){
                map.put("invoice_date", new Date());
            }else{

            }
            map.put("is_unusual", 0);
            map.put("invoice_status", 1);
            key = billingDao.addBilling(map);
            if (Objects.nonNull(key)) {
                flag = 1;
                map.put("billing_uuid", key);
                //保存运单日志
                billingLogMaps[0] = Maps.newHashMap();
                billingLogMaps[0].put("billing_status", 1);
                billingLogMaps[0].put("billing_remark", "开票");
            }
        } else {
            Map<String, Object> billing = DBUtil.getUpdateData(T_billing.class, map);
            billing.put("update_time", new Date());
            billing.put("operation_user_uuid", HsUtil.noAttribute("drawer", map));
            flag = billingDao.updateBilling(billing);
            //组装日志信息
            billingLogMaps[0] = Maps.newHashMap();
            billingLogMaps[0].put("billing_status", 0);    //状态置为0，区分附属流程，此信息不为货主或收件人展示，仅物流公司可见
            billingLogMaps[0].put("billing_remark", "修改票据信息");
        }
        if (flag > 0) {
            //保存运单费用
            saveBillingFee(map);
        }

        if (Objects.nonNull(key)) {
            billingLogMaps[0].put("billing_uuid", key);
            billingLogMaps[0].put("point_uuid", HsUtil.noAttribute("initial_station", map));
            billingLogMaps[0].put("operator_date", new Date());
            billingLogMaps[0].put("operator_uuid", HsUtil.noAttribute("drawer_uuid", map));
            billingLogDao.batchInsert(billingLogMaps);

            Map<String, Object> billMap = new HashMap<>();
            billMap.put("uuid", String.valueOf(key));
            List<Map<String, Object>> list = billingDao.selectBillingByUuid(billMap);

            if (list.size() > 0) {
                map = list.get(0);
            }
            return ResultUtil.success(map);
        }
        return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
    }

    /**
     * 获取票据详情
     *
     * @param map
     * @return
     */
    public Result<?> selectBillingByUuid(Map<String, Object> map) {
        List<Map<String, Object>> list = billingDao.selectBillingByUuid(map);
        if (list.size() > 0) {
            return ResultUtil.success(list);
        }
        return ResultUtil.success();
    }


    /**
     * 保存运单费用
     *
     * @param map
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Transactional
    public void saveBillingFee(Map<String, Object> map) {
        int flag = 0;
        Long key = null;
        Map<String, Object> billingFeeMap = new HashMap<>();
        billingFeeMap.put("uuid", HsUtil.noAttribute("billing_fee_uuid", map));
        billingFeeMap.put("billing_uuid", HsUtil.noAttribute("billing_uuid", map));
        billingFeeMap.put("declared_value", HsUtil.noAttribute("declared_value", map));
        billingFeeMap.put("valuation_fee", HsUtil.noAttribute("valuation_fee", map));
        billingFeeMap.put("delivery_fee", HsUtil.noAttribute("delivery_fee", map));
        billingFeeMap.put("advance", HsUtil.noAttribute("advance", map));
        billingFeeMap.put("receiving_fee", HsUtil.noAttribute("receiving_fee", map));
        billingFeeMap.put("handling_fee", HsUtil.noAttribute("handling_fee", map));
        billingFeeMap.put("packing_fee", HsUtil.noAttribute("packing_fee", map));
        billingFeeMap.put("upstair_fee", HsUtil.noAttribute("upstair_fee", map));
        billingFeeMap.put("forklift_fee", HsUtil.noAttribute("forklift_fee", map));
        billingFeeMap.put("return_fee", HsUtil.noAttribute("return_fee", map));
        billingFeeMap.put("under_charge_fee", HsUtil.noAttribute("under_charge_fee", map));
        billingFeeMap.put("warehousing_fee", HsUtil.noAttribute("warehousing_fee", map));
        billingFeeMap.put("collection_fee", HsUtil.noAttribute("collection_fee", map));
        billingFeeMap.put("freight", HsUtil.noAttribute("freight", map));
        billingFeeMap.put("total_freight", HsUtil.noAttribute("total_freight", map));
        billingFeeMap.put("total_freight_receipts", HsUtil.noAttribute("total_freight_receipts", map));
        billingFeeMap.put("payment_method", HsUtil.noAttribute("payment_method", map));
        billingFeeMap.put("cash_payment", HsUtil.noAttribute("cash_payment", map));
        billingFeeMap.put("collect_payment", HsUtil.noAttribute("collect_payment", map));
        billingFeeMap.put("default_of_payment", HsUtil.noAttribute("default_of_payment", map));
        billingFeeMap.put("back_payment", HsUtil.noAttribute("back_payment", map));
        billingFeeMap.put("monthly_payment", HsUtil.noAttribute("monthly_payment", map));
        billingFeeMap.put("payment_deduction", HsUtil.noAttribute("payment_deduction", map));
        Iterator iterator = billingFeeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object value = entry.getValue();
            if (Objects.isNull(value) || Strings.isNullOrEmpty((String) value)) {
                iterator.remove();
            }
        }
        if (Objects.nonNull(billingFeeMap.get("uuid"))) {
            flag = billingDao.updateBillingFee(billingFeeMap);
            if (flag > 0) {
                try {
                    key = Long.valueOf(HsUtil.noAttribute("billing_fee_uuid", map));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } else {
            key = billingDao.addBillingFee(billingFeeMap);
        }
        if ((flag > 0) || (Objects.nonNull(key))) {
            Map[] billingLogMaps = new Map[1];
            //组装日志信息
            billingLogMaps[0] = Maps.newHashMap();
            billingLogMaps[0].put("billing_uuid", key);
            billingLogMaps[0].put("point_uuid", HsUtil.noAttribute("point_uuid", map));
            billingLogMaps[0].put("operator_date", new Date());
            billingLogMaps[0].put("operator_uuid", HsUtil.noAttribute("drawer_uuid", map));
            billingLogMaps[0].put("billing_status", 0);    //状态置为0，区分附属流程，此信息不为货主或收件人展示，仅物流公司可见
            billingLogMaps[0].put("billing_remark", "修改票据信息");
            billingLogDao.batchInsert(billingLogMaps);
        }
    }

    /**
     * 获取到站信息
     *
     * @param map
     * @return
     */
    public Result<?> searchTerminalStation(Map<String, Object> map) {
        List<Map<String, Object>> list = billingDao.searchTerminalStation(map);
        LinkedList<Map<String, Object>> newList = new LinkedList<>();
        for (Map<String, Object> newMap : list) {
            if (map.get("company_uuid").equals(newMap.get("logistics_uuid"))) {
                newList.addFirst(newMap);
            } else {
                newList.add(newMap);
            }
        }
        if (list.size() > 0) {
            return ResultUtil.success(newList);
        }
        return ResultUtil.success();
    }

    /**
     * 获取网点开票历史目的地
     * @param map
     * @return
     */
    public Result<?> searchDestination(Map<String, Object> map){
        List<Map<String, Object>> list = billingDao.searchDestination(map);
        return ResultUtil.success(list);
    }


    /**
     * 获取常用收发货信息
     * 发货人信息、收货人信息、货物信息
     * consigner 货主
     * consignee 收货人
     * goods_name 货物
     * flag 搜索层级标识（1：发货人；2：收货人；3：货物）
     *
     * @return
     */
    public Result<?> searchBillingPrompt(Map<String, Object> map) {
        String flag = HsUtil.noAttribute("flag", map);
        if ("1".equals(flag)) {
            //货主信息
            List<Map<String, Object>> list = billingDao.searchConsigner(map);
            if (list.size() > 0) {
                return ResultUtil.success(list);
            }
        } else if ("2".equals(flag)) {
            //收货人信息
            List<Map<String, Object>> list = billingDao.searchConsignee(map);
            if (list.size() > 0) {
                return ResultUtil.success(list);
            }
        } else if ("3".equals(flag)) {
            //货物信息
            List<Map<String, Object>> list = billingDao.searchGoods_name(map);
            if (list.size() > 0) {
                return ResultUtil.success(list);
            }
        }
        return ResultUtil.success();
    }

    /**
     * 获取货主信息
     *
     * @return
     */
    public List<Map<String, Object>> searchConsigner(Map<String, Object> map) {
        return billingDao.searchConsigner(map);
    }

    /**
     * 获取收货人信息
     *
     * @return
     */
    public List<Map<String, Object>> searchConsignee(Map<String, Object> map) {
        return billingDao.searchConsignee(map);
    }

    /**
     * 获取货物信息
     *
     * @return
     */
    public List<Map<String, Object>> searchGoods_name(Map<String, Object> map) {
        return billingDao.searchGoods_name(map);
    }


    /**
     * 我也有超级查询哟
     *
     * @param map
     * @return
     */
    public Result<?> selectBilling(Map<String, Object> map) {
        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        List<Map<String, Object>> list = billingDao.selectBilling(map);
        Map<String, Object> resultMap = PageUtil.pageMethod(pagesize, page, list);
        return ResultUtil.success(resultMap);
    }


    /**
     * 保存保价率
     */
    @Transactional
    public Result SaveInsureRate(Map<String, Object> map) {
        map = HsUtil.RemoveNullforMap(map);
        Long key = billingDao.SaveInsureRate(map);
        if (Objects.nonNull(key)) {
            return ResultUtil.success(key);
        }

        return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
    }

    /**
     * 修改保价率
     */
    public Result UpdateInsureRate(Map<String, Object> map) {
        if (Objects.nonNull(map.get("logistics_uuid"))) {
            int flag = billingDao.updateSystemParameter(map);
            if (flag > 0) {
                return ResultUtil.success();
            }
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
    }


    /**
     * 获取保价率
     *
     * @param logistics_uuid
     * @return
     */
    public Result<?> getInsuredRate(Long logistics_uuid) {
        if (Objects.nonNull(logistics_uuid)) {
            try {
                Double insureRate = billingDao.getInsureRate(logistics_uuid);
                if (Objects.nonNull(insureRate)) {
                    return ResultUtil.success(insureRate);
                }
            } catch (Exception e) {
                return ResultUtil.success(0);
            }
        }
        return ResultUtil.success(0);
    }

    public Result searchRemarks(Map<String, Object> map) {
        List<Map<String, Object>> list = billingDao.searchRemarks(map);
        list = list.stream()
                .distinct()
                .filter(map1 -> !Strings.isNullOrEmpty((String) map1.get("remarks")))
                .collect(Collectors.toList());
        return ResultUtil.success(list);
    }


    //生成货号
    public String generateArticle_number(Map<String, Object> newMap, Map<String, Object> map) {
        String article_number_old = null; //老货号
        try {
            article_number_old = billingDao.getMaxArticle_number(newMap);
        } catch (EmptyResultDataAccessException e) {
            logger.info("第一个货号");
        }
        if (Strings.isNullOrEmpty(article_number_old)) {
            article_number_old = null;
        } else {
            if (HsUtil.judgeContainsStr(article_number_old)) {
                String num = article_number_old.split("-")[0];
                article_number_old = article_number_old.substring(num.length() - 3, num.length());
            } else {
                article_number_old = null;
            }
        }
        String sign = billingDao.getPoint_sign(newMap); //网点标识
        String quantity = HsUtil.noAttribute("quantity", map);  //数量
        //获取到站网点名称
        String terminal_station_name = billingDao.getPoint_name(map);

        String article_number = "";

        if (Strings.isNullOrEmpty(article_number_old)) {
            //截取目的地第一个字
            article_number = (Strings.isNullOrEmpty(terminal_station_name) ? "" : terminal_station_name.substring(0, 1)) + sign + "001-" + quantity;
        } else {
            if (Integer.valueOf(article_number_old).intValue() == 999) {
                article_number = (Strings.isNullOrEmpty(terminal_station_name) ? "" : terminal_station_name.substring(0, 1)) + sign + "001-" + quantity;
            } else {
//                int num = Integer.valueOf(article_number_old).intValue() + 1;
                String no_ = String.format("%03d", (Integer.parseInt(article_number_old) + 1));
                article_number = (Strings.isNullOrEmpty(terminal_station_name) ? "" : terminal_station_name.substring(0, 1)) + sign + no_ + "-" + quantity;
            }
        }

        return article_number;

    }

    /**
     * 选中运单的合计
     *
     * @param map
     * @return
     */
    public Result<?> billingsSumByUuid(Map<String, Object> map) {
        Map<String, Object> resultMap = billingDao.billingsSumByUuid(map);
        return ResultUtil.success(resultMap);
    }

}
