package com.yydscm.Controller.unusual;

import com.google.common.base.Strings;
import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.unusual.UnusualService;
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
 * 异常信息
 *
 * @author PC20170414
 */
@RestController
@RequestMapping("/unusual")
public class UnusualController {

    @Autowired
    UnusualService unusualService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 保存异常信息
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/saveUnusual")
    public Result<?> saveUnusual(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        Long recorder_uuid = (Long) user.get("uuid");
        map.put("record_point_uuid", point_uuid);
        map.put("recorder_uuid", recorder_uuid);
        return unusualService.saveUnusual(map);
    }

    /**
     * 获取异常信息列表
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/selectUnusual")
    public Result<?> selectUnusual(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long point_uuid = (Long) user.get("company_uuid");
        if (Strings.isNullOrEmpty(HsUtil.noAttribute("record_point_uuid", map))) {
            map.put("record_point_uuid", point_uuid);
        }
        return unusualService.selectUnusual(map);
    }


}
