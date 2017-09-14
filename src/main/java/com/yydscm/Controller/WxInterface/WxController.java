package com.yydscm.Controller.WxInterface;

import java.util.Map;
import java.util.Objects;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.WxInterface.WxService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;

/**
 * Created by chenzhaopeng on 2017/7/11.
 */
@RestController
@RequestMapping("credit")
public class WxController {

    @Autowired
    private WxService wxService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询诚信会员列表
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("selectCreditUserList")
    public Result<?> selectCreditUserList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        int role_uuid = (int) user.get("role_uuid");
        if (role_uuid == 3) {

            //查询诚信会员列表
            Map<String, Object> mapResult = wxService.selectCreditUserList(map);

            return ResultUtil.success(mapResult);
        } else {
            return ResultUtil.error(ResultEnum.FAILROLE.getCode(), ResultEnum.FAILROLE.getMsg());
        }

    }


    /**
     * 审核
     * @param map
     * @param request
     * @return
     */
    @PostMapping("reviewInfo")
    public Result<?> reviewInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        int role_uuid = (int) user.get("role_uuid");
        if (role_uuid == 3) {
            String credit_statu = (String) map.get("credit_statu");
            if(credit_statu.equals("2")){//审核通过
                //获取用户基本信息并添加
                return wxService.CreditUserInfo(map);
            }
            if(credit_statu.equals("3")){//审核拒绝
                return wxService.updateCreditStatu(map);
            }
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        } else {
            return ResultUtil.error(ResultEnum.FAILROLE.getCode(), ResultEnum.FAILROLE.getMsg());
        }

    }


}
