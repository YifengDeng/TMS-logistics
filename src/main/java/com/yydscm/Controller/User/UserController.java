package com.yydscm.Controller.User;

import com.google.common.base.Strings;
import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.User.UserService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * Created by chenzhaopeng on 2017/7/13.
 */
@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 修改密码接口
     * /User/UpdateUser
     * 只需传入原密码(pwd),新密码(newpwd),新旧密码传输前需用md5加密
     *
     * @param request
     * @param map
     * @return
     */

    @PostMapping("/UpdateUser")
    public Result UpdateUser(HttpServletRequest request, @RequestParam Map<String, Object> map) {
        if (Objects.isNull(map) || map.entrySet().isEmpty() || Strings.isNullOrEmpty((String) map.get("pwd"))) {
            return ResultUtil.error(ResultEnum.ORDERPASSWORD.getCode(), ResultEnum.ORDERPASSWORD.getMsg());
        }
        if (Strings.isNullOrEmpty((String) map.get("newpwd"))) {
            return ResultUtil.error(ResultEnum.NEWPWDERR.getCode(), ResultEnum.NEWPWDERR.getMsg());
        }
        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));
        map.put("uuid", request.getAttribute("userid"));
        return userService.UpdateUser(map);
    }
}
