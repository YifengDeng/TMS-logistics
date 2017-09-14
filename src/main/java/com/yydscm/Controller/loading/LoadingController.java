package com.yydscm.Controller.loading;

import com.google.common.base.Strings;
import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.Billing.BillingService;
import com.yydscm.Service.loadingService.LoadingService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 装车
 *
 * @author HOME_HCL
 */
@RestController
@RequestMapping("loading")
public class LoadingController {

    @Autowired
    LoadingService loadingService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    BillingService billingService;


    /**
     * 保存装车、发车信息
     *
     * @param map uuid : 运单uuid，多个用逗号隔开	--必传参数
     *            发车参数：装车批次号（loading_batches_id）,多个用逗号隔开 -- 发车必传参数，装车no传
     * @return
     */
    @PostMapping("/saveLoadingBatch")
    public Result<?> saveLoadingBatch(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        //在session中取得用户所属网点信息
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        Long operator_uuid = (Long) user.get("uuid");
        map.put("loading_point", point_uuid);
        map.put("operator_uuid", operator_uuid);
        return loadingService.saveLoadingBatch(map);
    }

    /**
     * 移除装车
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/removeLoadingBatch")
    public Result<?> removeLoadingBatch(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        //在session中取得用户所属网点信息
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        Long operator_uuid = (Long) user.get("uuid");
        map.put("loading_point", point_uuid);
        map.put("operator_uuid", operator_uuid);
        return loadingService.removeLoadingBatch(map);
    }

    /**
     * APP端装车前获取数据
     *
     * @param request
     * @return
     */
    @PostMapping("/selectBillingForLoading")
    public Result<?> selectBillingForLoading(HttpServletRequest request) {
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        Map<String, Object> map = new HashMap<>();
        map.put("initial_station_id", point_uuid);
        return loadingService.selectBillingForLoading(map);
    }

    /**
     * 装车（或装车历史）获取运单列表
     * page 页码
     * pageSize 每页显示条数
     * 装车：invoice_status 状态为开票状态，即：1
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/selectBillingForLoadingByAll")
    public Result<?> selectBillingForLoadingByAll(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        //在session中取得用户所属网点信息
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        if (Objects.nonNull(HsUtil.noAttribute("initial_station_id", map))) {
            point_uuid = Long.valueOf(HsUtil.noAttribute("initial_station_id", map));
            map.put("initial_station_id", point_uuid);
        } else {
            map.put("initial_station_id", point_uuid);
        }
        String invoice_status = HsUtil.noAttribute("invoice_status", map);
        if (Strings.isNullOrEmpty(invoice_status)) {
            map.put("invoice_status", "2,3,4,5");
        } else {
            map.put("invoice_status", invoice_status);
        }
        return billingService.selectBilling(map);
    }


}
