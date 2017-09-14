package com.yydscm.Controller.User;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.User.MemberService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 会员基础信息
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    RedisUtil redisUtil;


    /**
     *查询会员基本信息
     * @param map
     * @return
     */
    @PostMapping("selectMemberList")
    public Result<?> selectMemberList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        if(!((Integer)user.get("role_uuid")).equals(3)){
            return ResultUtil.success();
        }

        //查询会员基本信息
        Map<String, Object> mapResult = memberService.selectMemberList(map);

        return ResultUtil.success(mapResult);
    }


}
