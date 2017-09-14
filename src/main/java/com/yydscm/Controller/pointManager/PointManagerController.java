package com.yydscm.Controller.pointManager;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.pointManager.PointManagerService;
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
 * 网点和人员管理
 *
 * @author Administrator
 */
@RestController
@RequestMapping("pointManager")
public class PointManagerController {

    @Autowired
    private PointManagerService pointManagerService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 添加或者修改网点
     *
     * @param map
     * @return
     */
    @PostMapping("addOrUpdatePoint")
    public Result<?> addOrUpdatePoint(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        int row = 0;
        if (Objects.isNull(map.get("uuid"))) {
            //检测手机号是否重复
            map.put("phone_one", map.get("point_owner_phone_o"));
            int count = pointManagerService.checkPhone(map);
            if (count > 0) {
                return ResultUtil.error(ResultEnum.PHONEEXIST.getCode(), ResultEnum.PHONEEXIST.getMsg());
            } else {
                row = pointManagerService.addPoint(map, user);
            }
        } else {
            row = pointManagerService.updatePoint(map);
        }

        if (row == 1) {
            return ResultUtil.success(row);
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 查询网点详情
     *
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("selectPoint")
    public Result<?> selectPoint(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        Map<String, Object> list = pointManagerService.selectPoint(map);

        return ResultUtil.success(list);

    }


    /**
     * 添加或者修改网点人员
     *
     * @param map
     * @return
     */
    @PostMapping("addOrUpdatePointUser")
    public Result<?> addOrUpdatePointUser(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = 0;
        //检测手机号是否重复
        int count = pointManagerService.checkPhone(map);
        if (count > 0) {
            return ResultUtil.error(ResultEnum.PHONEEXIST.getCode(), ResultEnum.PHONEEXIST.getMsg());
        } else {

            if (Objects.isNull(map.get("uuid"))) {
                row = pointManagerService.addPointUser(map);
            } else {
                row = pointManagerService.UpdatePointUser(map);
            }
        }
        if (row == 1) {
            return ResultUtil.success(row);
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }
    }


    /**
     * 查询网点所有人员
     *
     * @param map
     * @return
     */
    @PostMapping("selectPointUser")
    public Result<?> selectPointUser(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        List<Map<String, Object>> list = pointManagerService.selectPointUser(map);

        return ResultUtil.success(list);

    }


    /**
     * 查询网点和网点人员
     *
     * @param map
     * @return
     */
    @PostMapping("selectPointAndPointUser")
    public Result<?> selectPointAndPointUser(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        Map<String, Object> list = pointManagerService.selectPointAndPointUser(map, user);

        return ResultUtil.success(list);

    }


    /**
     * 删除网点人员
     *
     * @param map
     * @return
     */
    @PostMapping("deletePointUser")
    public Result<?> deletePointUser(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = pointManagerService.deletePointUser(map);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }

}
