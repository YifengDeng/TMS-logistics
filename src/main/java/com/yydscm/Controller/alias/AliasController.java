package com.yydscm.Controller.alias;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.alias.AliasService;
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
 * 别名管理
 *
 * @author Administrator
 */
@RestController
@RequestMapping("alias")
public class AliasController {

    @Autowired
    private AliasService aliasService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 添加或者修改别名
     *
     * @param map
     * @return
     */
    @PostMapping("addOrUpdateAlias")
    public Result<?> addOrUpdateAlias(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = 0;
        if (Objects.isNull(map.get("uuid"))) {
            row = aliasService.addAlias(map, user);
        } else {
            row = aliasService.updateAlias(map);
        }

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 删除别名
     *
     * @param map
     * @return
     */
    @PostMapping("deleteAlias")
    public Result<?> deleteAlias(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = aliasService.deleteAlias(map);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }


    /**
     * 查询别名列表
     *
     * @return
     */
    @PostMapping("selectAliasList")
    public Result<?> selectDeclaractionList(HttpServletRequest request) {

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        List<Map<String, Object>> list = aliasService.selectAliasList(user);

        return ResultUtil.success(list);

    }


}
