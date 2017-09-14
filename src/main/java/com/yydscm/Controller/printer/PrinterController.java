package com.yydscm.Controller.printer;

import com.google.common.base.Strings;
import com.yydscm.Service.User.UserService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 打印机配置
 *
 * @author HOME_HCL
 */
@RestController
@RequestMapping("/printer")
public class PrinterController {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;

    /**
     * 获取用户绑定的打印机信息
     *
     * @param request
     * @return
     */
    @PostMapping("/selectPrinter")
    public Result<?> selectPrinter(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", (String) request.getAttribute("userid"));
        return userService.selectPrinter(map);
    }

    /**
     * 绑定用户常用打印机信息
     *
     * @param map
     * @return
     */
    @PostMapping("/savePrinter")
    public Result<?> savePrinter(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(HsUtil.noAttribute("TP", map))) {
            map.put("TP", null);
        }
        if (Strings.isNullOrEmpty(HsUtil.noAttribute("stylus", map))) {
            map.put("stylus", null);
        }
        if (Strings.isNullOrEmpty(HsUtil.noAttribute("A4", map))) {
            map.put("A4", null);
        }
        map.put("uuid", (String) request.getAttribute("userid"));
        return userService.updateUserForPrinter(map);
    }


}
