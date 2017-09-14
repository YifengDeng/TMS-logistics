package com.yydscm.Service.User;

import com.google.common.collect.Maps;
import com.yydscm.Dao.User.UserDao;
import com.yydscm.Enum.Const;
import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.TokenManager;
import com.yydscm.Util.MD5;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by chenzhaopeng on 2017/6/19.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    TokenManager tokenManager;
    @Autowired
    RedisUtil redisUtil;


    public Result AuthLogin(Map<String, Object> param, HttpServletRequest request) {


        Map<String, Object> user = null;
        try {
            user = userDao.selectUserByUserName(param);
        } catch (EmptyResultDataAccessException e) {
            return ResultUtil.error(ResultEnum.USERISNULL.getCode(), ResultEnum.USERISNULL.getMsg());
        }
        if (Objects.isNull(user)) {
            return ResultUtil.error(ResultEnum.USERISNULL.getCode(), ResultEnum.USERISNULL.getMsg());
        }
//        String Url = request.getRequestURI();
//        if (Url.indexOf("/login") >= 0) {
//            String randcode = (String) request.getSession().getAttribute("randCode");
//            if (!randcode.toLowerCase().equals(param.get("randNum").toString().toLowerCase())) {
//                return ResultUtil.error(ResultEnum.RANDCODE.getCode(), ResultEnum.RANDCODE.getMsg());
//            }
//        }
        if (param.get("pwd").equals(user.get("pwd"))) {
            user.remove("pwd");
            String token = tokenManager.createToken(user);
            Map<String, Object> loginInfo = Maps.newHashMap();
            loginInfo.put(Const.USER.name(), user);
            loginInfo.put(Const.TOKEN.name(), token);
            redisUtil.put(user.get("uuid") + "", loginInfo);
            return ResultUtil.success(user, token);
        } else {
            return ResultUtil.error(ResultEnum.PASSWORDERR.getCode(), ResultEnum.PASSWORDERR.getMsg());
        }
    }

    public Result getMenu(String userid) {
        Map<String, Object> user = redisUtil.getUser(userid);
        String[] menus = {
                "158",
                "159",
                "160",
                "161",
                "162",
                "163",
                "164",
                "165",
                "166",
                "167",
                "168",
                "169",
                "170"};
        Map<String, Object> param = Maps.newHashMap();
        param.put("menuid", Arrays.asList(menus));
        List<Map<String, Object>> menuinfos = userDao.selectMenuByMenuId(param);
        Map<String, Object> menumap = Maps.newLinkedHashMap();
        menuinfos.forEach(map -> {
            menumap.put(map.get("uuid") + "", map);
        });
        for (Iterator iterator = menumap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Map<String, Object> s = (Map<String, Object>) entry.getValue();
            String key = (String) entry.getKey();
            Long parent_uuid = (Long) s.get("parent_uuid");
            if (parent_uuid != -1) {
                Map<String, Object> p = (Map<String, Object>) menumap.get(parent_uuid + "");
                p.put("sub_" + key, s);
                iterator.remove();
            }
        }

        return ResultUtil.success(menumap);
    }

    /**
     * 绑定用户常用打印机信息
     *
     * @param map
     * @return
     */
    public Result<?> updateUserForPrinter(Map<String, Object> map) {
        int flag = userDao.updateUserForPrinter(map);
        return ResultUtil.success(flag);
    }

    /**
     * 获取用户绑定的打印机信息
     *
     * @param map
     * @return
     */
    public Result<?> selectPrinter(Map<String, Object> map) {
        return ResultUtil.success(userDao.selectPrinterById(map));
    }

    public Map<String, Object> getUser(String userId) {
        return userDao.selectUserById(userId);
    }

    /**
     * 修改密码
     *
     * @param map
     * @return
     */
    @Transactional
    public Result UpdateUser(Map<String, Object> map) {
        String password = (String) map.get("pwd");
        String userid = (String) map.get("uuid");
        Map<String, Object> user = null;
        try {
            user = getUser(userid);
        } catch (EmptyResultDataAccessException e) {
            return ResultUtil.error(ResultEnum.USERISNULL.getCode(), ResultEnum.USERISNULL.getMsg());
        }
        Optional<Map<String, Object>> userOptinal = Optional.ofNullable(user);
        if (userOptinal.isPresent()) {
            String orderPwd = (String) user.get("pwd");
            if (password.equals(orderPwd)) {
                user.put("pwd", map.get("newpwd"));
                int flag = userDao.updateUser(user);
                return flag == 1 ? ResultUtil.success() : ResultUtil.error(ResultEnum.FAIL_SAVE.getCode(), ResultEnum.FAIL_SAVE.getMsg());
            } else {
                return ResultUtil.error(ResultEnum.ORDERPWDERR.getCode(), ResultEnum.ORDERPWDERR.getMsg());
            }
        }
        return ResultUtil.error(ResultEnum.USERISNULL.getCode(), ResultEnum.USERISNULL.getMsg());
    }
}
