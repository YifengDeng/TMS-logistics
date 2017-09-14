package com.yydscm.Controller.goodsManager;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.goodsManager.GoodsManagerService;
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
 * 货物管理
 *
 * @author Administrator
 */
@RestController
@RequestMapping("goodsManager")
public class GoodsManagerController {

    @Autowired
    private GoodsManagerService goodsManagerService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 添加货物
     *
     * @param map
     * @return
     */
    @PostMapping("addGoods")
    public Result<?> addGoods(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = goodsManagerService.addGoods(map, user);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 删除货物
     *
     * @param map
     * @return
     */
    @PostMapping("deleteGoods")
    public Result<?> deleteGoods(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = goodsManagerService.deleteGoods(map);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 查询所有货物
     *
     * @return
     */
    @PostMapping("selectGoodsList")
    public Result<?> selectGoodsList(HttpServletRequest request) {

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        List<Map<String, Object>> list = goodsManagerService.selectGoodsList(user);

        return ResultUtil.success(list);

    }


}
