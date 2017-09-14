package com.yydscm.Controller;

import com.google.common.collect.Maps;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公用controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("common")
public class CommonController {


    /**
     * 获取枚举属性
     *
     * @param enumName 枚举类类名
     * @return
     * @throws Exception
     */
    @PostMapping("getEnum")
    public Result getEnum(@RequestParam String enumName) {
        Map<String, Object> map = null;
        List<Map<String, Object>> list = new ArrayList<>();

        String str = "com.yydscm.Enum.";
        str = str + enumName;

        Class<?> classType = null;
        Method toKey = null;

        try {
            classType = Class.forName(str);
            toKey = classType.getMethod("getValue");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取枚举
        Object[] objs = classType.getEnumConstants();

        for (Object object : objs) {
            map = Maps.newHashMap();

            Object obj = null;
            try {
                obj = toKey.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }

            map.put("key", obj);
            map.put("value", object);

            list.add(map);
        }

        return ResultUtil.success(list);
    }

}
