package com.yydscm.Controller.partners;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.partners.PartnersService;
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

import java.util.Map;
import java.util.Objects;

/**
 * 合作伙伴
 *
 * @author Administrator
 */
@RestController
@RequestMapping("partners")
public class PartnersController {

    @Autowired
    private PartnersService partnersService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 搜索合作伙伴公司
     *
     * @return
     */
    @PostMapping("selectCompanyList")
    public Result<?> selectCompanyList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //搜索
        Map<String, Object> mapResult = partnersService.selectCompanyList(map, user);

        return ResultUtil.success(mapResult);
    }


    /**
     * 搜索未合作的子网点
     *
     * @param map
     * @return
     */
    @PostMapping("selectPointList")
    public Result<?> selectPointList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //搜索
        Map<String, Object> mapResult = partnersService.selectPointList(map, user);

        return ResultUtil.success(mapResult);
    }


    /**
     * 添加合作伙伴
     *
     * @param map
     * @return
     */
    @PostMapping("addPartners")
    public Result<?> addPartners(@RequestParam String map, HttpServletRequest request) {

        if (Objects.isNull(map)) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        //添加合作伙伴
        int[] row = partnersService.addPartners(map, user);

        if (row.length > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
        }

    }


    /**
     * 查询合作伙伴列表
     *
     * @return
     */
    @PostMapping("selectPartnersList")
    public Result<?> selectPartnersList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        Map<String, Object> list = partnersService.selectPartnersList(map, user);

        return ResultUtil.success(list);

    }


    /**
     * 禁用或者启用合作伙伴
     *
     * @param map
     * @return
     */
    @PostMapping("updatePartnersStatus")
    public Result<?> updatePartnersStatus(@RequestParam Map<String, Object> map) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = partnersService.updatePartnersStatus(map);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }

}
