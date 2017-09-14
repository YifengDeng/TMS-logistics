package com.yydscm.Controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yydscm.Enum.Const;
import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.User.UserService;
import com.yydscm.Util.ImageUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by chenzhaopeng on 2017/6/1
 */
@RestController
public class LoginController<K, V> {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录请求地址
     *
     * @return
     */
    @PostMapping(value = "/Applogin")
    public Result AppLogin(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        if (Objects.isNull(param)) {
            return ResultUtil.error(ResultEnum.USERPAWDISNULL.getCode(), ResultEnum.USERPAWDISNULL.getMsg());
        }
        Result result = userService.AuthLogin(param, request);
        return result;
    }

    @PostMapping(value = "/login")
    public Result Login(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        if (Objects.isNull(param)) {
            return ResultUtil.error(ResultEnum.USERPAWDISNULL.getCode(), ResultEnum.USERPAWDISNULL.getMsg());
        }
        Result result = userService.AuthLogin(param, request);
        return result;
    }

    /**
     * 生成验证码图片
     *
     * @param request
     * @return
     */
    @GetMapping("/randCode")
    public void vailcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = ImageUtil.createImage();
        String code = (String) map.get("randCode");
        request.getSession().setAttribute("randCode", code);
        BufferedImage image = (BufferedImage) map.get("image");
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
    }

    @GetMapping("/menu")
    public Result Menu(HttpServletRequest request) {
        String userid = (String) request.getAttribute("userid");
        return userService.getMenu(userid);
    }

    /**
     * 获取用户信息的方式,登录后有效
     *
     * @param request
     * @return
     */
    @PostMapping("/testuser")
    public Result TestUser(HttpServletRequest request) {
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        return ResultUtil.success(user);
    }


    @PostMapping("/logout")
    public Result Logout(HttpServletRequest request) {
        String uuid = (String) request.getAttribute("uuid");
        if (!Strings.isNullOrEmpty(uuid)) {
            redisUtil.remove(uuid);
        }
        return ResultUtil.success();
    }


    @GetMapping("setSession")
    public Result SetSession(HttpSession session) {
        Map map = new HashMap<>();
        map.put("TSession", "sessionTest");
        session.setAttribute(Const.USER.name(), map);
        return ResultUtil.success(map);
    }

    @GetMapping("getSession")
    public Result GetSession(HttpSession httpSession) {
//        return ResultUtil.success(httpSession.getAttribute(Const.USER.name()));
        List list = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            list.add("hello world");
        }
        return ResultUtil.success(list);
    }


}
