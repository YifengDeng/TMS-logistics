package com.yydscm.Controller.Billing;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.Billing.BillingService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 开票
 *
 * @author PC20170414
 */
@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    BillingService billingService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 保存票据
     *
     * @param map
     * @return
     */
    @PostMapping("/saveBilling")
    public Result<?> saveBilling(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        //在session中取得用户所属网点信息
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        Long user_uuid = (Long) user.get("uuid");
        map.put("initial_station", point_uuid);
        map.put("drawer", user_uuid);
        return billingService.saveBilling(map);
    }

    /**
     * 获取票据详情
     *
     * @param map
     * @return
     */
    @PostMapping("/selectBillingByUuid")
    public Result<?> selectBillingByUuid(@RequestParam Map<String, Object> map) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        return billingService.selectBillingByUuid(map);
    }

    /**
     * 获取到站信息(本公司其他网点、合作伙伴网点)
     *
     * @return
     */
    @PostMapping("/searchTerminalStation")
    public Result<?> searchTerminalStation(HttpServletRequest request) {
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        Long company_uuid = (Long) user.get("logistics_uuid");
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", point_uuid);
        map.put("company_uuid", company_uuid);
        return billingService.searchTerminalStation(map);
    }

    /**
     * 获取网点开票历史目的地
     * @param request
     * @return
     */
    @PostMapping("/searchDestination")
    public Result<?> searchDestination(HttpServletRequest request) {
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Map<String, Object> map = new HashMap<>();
        map.put("initial_station_id", user.get("company_uuid"));
        return billingService.searchDestination(map);
    }

    /**
     * 获取常用收发货信息
     * 发货人信息、收货人信息、货物信息
     * consigner 货主
     * consignee 收货人
     * goods_name 货物
     * flag 搜索层级标识（1：发货人；2：收货人；3：货物）
     *
     * @param map
     * @return
     */
    @PostMapping("/searchName")
    public Result<?> searchName(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        map.put("point_uuid", point_uuid);
        return billingService.searchBillingPrompt(map);
    }

    /**
     * 获取常用备注
     *
     * @param map
     * @param request
     * @return
     */
    @GetMapping("/searchRemarks")
    public Result searchRemarks(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        map.put("point_uuid", point_uuid);
        return billingService.searchRemarks(map);
    }


    /**
     * 超级运单查询
     *
     * @return
     */
    @PostMapping("/selectBilling")
    public Result<?> selectBilling(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        map.put("point_uuid", point_uuid);
        return billingService.selectBilling(map);
    }

    /**
     * 保存保价率   url:/billing/SaveInsuredRate
     * 不传logistics_uuid为增加一条记录，传入为修改。
     * logistics_uuid 网点id
     * Insured_rate 保价率
     * commission_rate 代收货款手续费费率
     *
     * @param request
     * @param map
     * @return
     */
    @PostMapping("/SaveInsuredRate")
    public Result SaveInsuredRate(HttpServletRequest request, @RequestParam Map<String, Object> map) {
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        if (Objects.isNull(map.get("logistics_uuid"))) {
            Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
            Long logistics_uuid = (Long) user.get("logistics_uuid");
            map.put("logistics_uuid", logistics_uuid);
            return billingService.SaveInsureRate(map);
        }
        return billingService.UpdateInsureRate(map);

    }


    /**
     * 获取保价率 url:/InsuredRate/{logistics_uuid}
     * restfull风格 直接把logistics_uuid写在后面   e.g.  /InsuredRate/2287
     * @param logistics_uuid
     * @return
     */
    @GetMapping("/InsuredRate/{logistics_uuid}")
    public Result<?> InsuredRate(@PathVariable Long logistics_uuid) {
        return billingService.getInsuredRate(logistics_uuid);
    }


}
