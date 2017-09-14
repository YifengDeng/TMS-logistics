package com.yydscm.Controller.Billing;


import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.Billing.BillManagerService;
import com.yydscm.Util.Excel.WriteExcel;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 运单列表
 *
 * @author Administrator
 */
@RestController
@RequestMapping("billManager")
public class BillManagerController {

    @Autowired
    private BillManagerService billManagerService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 我的运单
     * @return
     */
    @PostMapping("selectBillList")
    public Result<?> selectBillList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //根据登录id查询运单列表
        Map<String, Object> mapResult = billManagerService.selectBillList(map, user);

        return ResultUtil.success(mapResult);
    }


    /**
     * 运单作废
     *
     * @param uuid 运单id
     * @return
     */
    @PostMapping("billCancel")
    public Result<?> billCancel(Long uuid) {

        int row = billManagerService.updateBillStatus(uuid);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }
    }


    /**
     * 运单超级查询
     *
     * @param map
     * @return
     */
    @PostMapping("selectBillSuperList")
    public Result<?> selectBillSuperList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //根据登录id查询相关运单列表
        Map<String, Object> mapResult = billManagerService.selectBillSuperList(map, user);

        return ResultUtil.success(mapResult);
    }


    /**
     * 查询发站网点
     *
     * @return
     */
    @PostMapping("selectInitialStation")
    public Result<?> selectInitialStation(HttpServletRequest request) {

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        List<Map<String, Object>> list = billManagerService.selectInitialStation(user);

        return ResultUtil.success(list);

    }


    /**
     * 运单历史费用计算
     *
     * @param map
     * @return
     */
    @PostMapping("selectCost")
    public Result<?> selectCost(@RequestParam Map<String, Object> map, HttpServletRequest request) {

//		if (Objects.isNull(map) || map.entrySet().isEmpty()) {
//			return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
//		}

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        if (!Objects.isNull(map) && !map.entrySet().isEmpty()) {
            map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        }

        //运单费用计算
        Map<String, Object> list = billManagerService.selectCost(map, user);

        return ResultUtil.success(list);

    }


    /**
     * 运单超级查询费用计算
     *
     * @param map
     * @return
     */
    @PostMapping("selectSuperCost")
    public Result<?> selectSuperCost(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (!Objects.isNull(map) && !map.entrySet().isEmpty()) {
            map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        //运单超级查询费用计算
        Map<String, Object> list = billManagerService.selectSuperCost(map, user);

        return ResultUtil.success(list);

    }


    /**
     * 导出运单历史报表
     *
     * @param map
     * @return
     */
    @GetMapping("exportBillExcel")
    public void exportBillExcel(@RequestParam Map<String, Object> map, HttpServletResponse response, HttpServletRequest request) {

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        String timeStr = "";

        if (!Objects.isNull(map) && !map.entrySet().isEmpty()) {
            map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
            //拼装文件名
            String startTime = null;
            String endTime = null;
            if (Objects.nonNull(HsUtil.noAttribute("startTime", map)) && !"".equals(HsUtil.noAttribute("startTime", map))
                    && !"null".equals(HsUtil.noAttribute("startTime", map))) {
                startTime = (String) map.get("startTime");
            }
            if (Objects.nonNull(HsUtil.noAttribute("endTime", map)) && !"".equals(HsUtil.noAttribute("endTime", map))
                    && !"null".equals(HsUtil.noAttribute("endTime", map))) {
                endTime = (String) map.get("endTime");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

            if (StringUtils.isNotBlank(startTime)) {
                if (StringUtils.isNotBlank(endTime)) {
                    timeStr = startTime + "到" + endTime;
                } else {
                    timeStr = startTime + "到" + sdf.format(new Date());
                }
            } else {
                if (StringUtils.isNotBlank(endTime)) {
                    timeStr = endTime + "之前";
                } else {
                    timeStr = "所有记录";
                }
            }

        }

        //根据登录id查询运单列表
        List<Map<String, Object>> listResult = billManagerService.importBillExcel(map, user);

        WriteExcel.deriveExcel("运单历史_" + timeStr, "运单历史", new String[]{"invoicedate", "article_number", "startStation", "endStation", "transit_destination", "consignee",
                "consignee_phone", "goods_name", "quantity", "packaging", "total_freight", "payment_method", "collect_payment", "cash_payment", "back_payment", "monthly_payment",
                "payment_deduction", "default_of_payment", "declared_value", "valuation_fee", "delivery_fee", "advance", "receiving_fee", "handling_fee", "packing_fee", "upstair_fee",
                "forklift_fee", "return_fee", "under_charge_fee", "warehousing_fee", "collection_fee", "freight", "waybill_number", "batch_number", "delivery_method", "consigner",
                "consigner_phone", "consigner_addr", "consignee_addr", "weight", "volume", "number_of_copies", "user_name", "remarks"
        }, new String[]{"开票日期", "货号", "发站网点", "到站网点", "中转目的地", "收货人名称", "收货人电话", "货物名称", "货物数量", "包装", "合计费用", "付款方式", "提付", "现付", "回付", "月结", "货款扣", "欠付",
                "申明价值", "保价费", "送货费", "垫付款", "接货费", "装卸费", "包装费", "上楼费", "叉吊费", "现返费", "欠返费", "仓储费", "代收货款", "运费", "运单号", "装车批次", "提货方式", "发货人名称", "发货人电话",
                "发货人地址", "收货人地址", "重量", "体积", "回单份数", "开票人", "备注"
        }, listResult, response, "yyyy-MM-dd");
    }


    /**
     * 查询当天的费用
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("selectCostForDay")
    public Result<?> selectCostForDay(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (!Objects.isNull(map) && !map.entrySet().isEmpty()) {
            map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        //运单超级查询费用计算
        Map<String, Object> list = billManagerService.selectCostForDay(map, user);

        return ResultUtil.success(list);

    }


    /**
     * 查询到站网点
     *
     * @return
     */
    @PostMapping("selectTerminalStation")
    public Result<?> selectTerminalStation(HttpServletRequest request) {

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        List<Map<String, Object>> list = billManagerService.selectTerminalStation(user);

        return ResultUtil.success(list);

    }


    /**
     * 运单查询中的到站和发站网点查询
     *
     * @param request
     * @return
     */
    @PostMapping("selectAllTerminalStation")
    public Result<?> selectAllTerminalStation(HttpServletRequest request) {

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        List<Map<String, Object>> list = billManagerService.selectAllTerminalStation(user);

        return ResultUtil.success(list);

    }


    /**
     * 导出相关运单报表
     *
     * @param map
     * @param response
     * @param request
     */
    @GetMapping("exportSuperBillExcel")
    public void exportSuperBillExcel(@RequestParam Map<String, Object> map, HttpServletResponse response, HttpServletRequest request) {

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        String timeStr = "";

        if (!Objects.isNull(map) && !map.entrySet().isEmpty()) {
            map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
            //拼装文件名
            String startTime = null;
            String endTime = null;
            if (Objects.nonNull(HsUtil.noAttribute("startTime", map)) && !"".equals(HsUtil.noAttribute("startTime", map))
                    && !"null".equals(HsUtil.noAttribute("startTime", map))) {
                startTime = (String) map.get("startTime");
            }
            if (Objects.nonNull(HsUtil.noAttribute("endTime", map)) && !"".equals(HsUtil.noAttribute("endTime", map))
                    && !"null".equals(HsUtil.noAttribute("endTime", map))) {
                endTime = (String) map.get("endTime");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

            if (StringUtils.isNotBlank(startTime)) {
                if (StringUtils.isNotBlank(endTime)) {
                    timeStr = startTime + "到" + endTime;
                } else {
                    timeStr = startTime + "到" + sdf.format(new Date());
                }
            } else {
                if (StringUtils.isNotBlank(endTime)) {
                    timeStr = endTime + "之前";
                } else {
                    timeStr = "所有记录";
                }
            }

        }

        //根据登录id查询相关运单列表
        List<Map<String, Object>> listResult = billManagerService.exportSuperBillExcel(map, user);

        WriteExcel.deriveExcel("运单明细_" + timeStr, "运单明细", new String[]{"invoicedate", "article_number", "startStation", "endStation", "transit_destination", "consignee",
                "consignee_phone", "goods_name", "quantity", "packaging", "total_freight", "payment_method", "collect_payment", "cash_payment", "back_payment", "monthly_payment",
                "payment_deduction", "default_of_payment", "declared_value", "valuation_fee", "delivery_fee", "advance", "receiving_fee", "handling_fee", "packing_fee", "upstair_fee",
                "forklift_fee", "return_fee", "under_charge_fee", "warehousing_fee", "collection_fee", "freight", "waybill_number", "batch_number", "delivery_method", "consigner",
                "consigner_phone", "consigner_addr", "consignee_addr", "weight", "volume", "number_of_copies", "user_name", "remarks"
        }, new String[]{"开票日期", "货号", "发站网点", "到站网点", "中转目的地", "收货人名称", "收货人电话", "货物名称", "货物数量", "包装", "合计费用", "付款方式", "提付", "现付", "回付", "月结", "货款扣", "欠付",
                "申明价值", "保价费", "送货费", "垫付款", "接货费", "装卸费", "包装费", "上楼费", "叉吊费", "现返费", "欠返费", "仓储费", "代收货款", "运费", "运单号", "装车批次", "提货方式", "发货人名称", "发货人电话",
                "发货人地址", "收货人地址", "重量", "体积", "回单份数", "开票人", "备注"
        }, listResult, response, "yyyy-MM-dd");
    }

}
