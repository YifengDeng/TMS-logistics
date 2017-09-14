package com.yydscm.Controller.declaraction;


import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.declaraction.DeclaractionService;
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
 * 声明
 *
 * @author Administrator
 */
@RestController
@RequestMapping("declaraction")
public class DeclaractionController {


    @Autowired
    private DeclaractionService declaractionService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 添加或者修改声明
     *
     * @param request
     * @return
     */
    @PostMapping("addDeclaraction")
    public Result<?> addDeclaraction(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = 0;
        if (Objects.isNull(map.get("uuid"))) {
            row = declaractionService.addDeclaraction(map, user);
        } else {
            row = declaractionService.updateDeclaraction(map);
        }

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 删除声明
     *
     * @param map
     * @return
     */
    @PostMapping("deleteDeclaraction")
    public Result<?> deleteDeclaraction(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = declaractionService.deleteDeclaraction(map);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 查询声明列表
     *
     * @return
     */
    @PostMapping("selectDeclaractionList")
    public Result<?> selectDeclaractionList(HttpServletRequest request) {
        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        List<Map<String, Object>> list = declaractionService.selectDeclaractionList(user);

        return ResultUtil.success(list);

    }


}
