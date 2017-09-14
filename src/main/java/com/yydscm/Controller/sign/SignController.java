package com.yydscm.Controller.sign;

import com.google.common.base.Strings;
import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.Billing.BillingService;
import com.yydscm.Service.sign.SignService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;


/**
 * 签收
 *
 * @author HOME_HCL
 */
@RestController
@RequestMapping("/sign")
public class SignController {

    @Autowired
    SignService signService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    BillingService billingService;


    /**
     * 批量签收
     *
     * @param map
     * @return
     */
    @RequestMapping("/updateBillingForSign")
    public Result<?> updateBillingOFInvoiceStatus(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        Long point_operator = (Long) user.get("uuid");
        map.put("point_uuid", point_uuid);
        map.put("point_operator", point_operator);
        return signService.updateBillingOFInvoiceStatus(map);
    }

    /**
     * 签收列表
     *
     * @param map
     * @return
     */
    @PostMapping("/selectSignList")
    public Result<?> selectSignList(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        map.put("terminal_station_id", point_uuid);
        String invoice_status = (Objects.nonNull(HsUtil.noAttribute("invoice_status", map)) &&
                !Strings.isNullOrEmpty(HsUtil.noAttribute("invoice_status", map)))
                ? HsUtil.noAttribute("invoice_status", map) : "4,5";
        map.put("invoice_status", invoice_status);
        return billingService.selectBilling(map);
    }

    /**
     * 打印运单，选中运单的合计
     *
     * @param map
     * @return
     */
    @PostMapping("/billingsSumByUuid")
    public Result<?> billingsSumByUuid(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        map.put("point_uuid", user.get("company_uuid"));
        return billingService.billingsSumByUuid(map);
    }


}
