package com.yydscm.Controller.driverManager;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.driverManager.DriverManagerService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 车辆管理
 *
 * @author Administrator
 */
@RestController
@RequestMapping("driverManager")
public class DriverManagerController {

    @Autowired
    private DriverManagerService driverManagerService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 添加或者修改车辆信息
     *
     * @param map
     * @return
     */
    @PostMapping("addOrUpdateDriver")
    public Result<?> addDriver(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = 0;
        if (Objects.isNull(map.get("uuid"))) {
            row = driverManagerService.addDriver(map, user);
        } else {
            row = driverManagerService.updateDriver(map);
        }

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 删除车辆信息
     *
     * @param map
     * @return
     */
    @PostMapping("deleteDriver")
    public Result<?> deleteDriver(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = driverManagerService.deleteDriver(map);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 查询车辆信息
     *
     * @return
     */
    @PostMapping("selectDriverList")
    public Result<?> selectDriverList(HttpServletRequest request) {

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        List<Map<String, Object>> list = driverManagerService.selectDriverList(user);

        return ResultUtil.success(list);

    }


}
