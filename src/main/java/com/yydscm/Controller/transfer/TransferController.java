package com.yydscm.Controller.transfer;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.transfer.TransferService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by PC20170414 on 2017/7/18.
 */

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    TransferService transferService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 保存中转信息
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/saveTransfer")
    public Result<?> saveTransfer(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        map.put("point_uuid", user.get("company_uuid"));
        map.put("operator_id", user.get("uuid"));
        map.put("transfer_date", new Date());
        return transferService.saveTransfer(map);
    }


    /**
     * 获取中转信息列表
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/selectTransfer")
    public Result<?> selectTransfer(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        map.put("point_uuid", point_uuid);
        return transferService.selectTransfer(map);
    }

    /**
     * 获取平台内所有网点，除了登录网点
     *
     * @param request
     * @return
     */
    @PostMapping("/getPointForNotOwn")
    public Result<?> getPointForNotOwn(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        map.put("point_uuid", user.get("company_uuid"));
        return transferService.getPointForNotOwn(map);
    }


}
