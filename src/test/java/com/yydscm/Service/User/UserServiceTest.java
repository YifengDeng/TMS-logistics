package com.yydscm.Service.User;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by chenzhaopeng on 2017/7/13.
 */
public class UserServiceTest {
    @Test
    public void updateUser() throws Exception {
//        Map<String,Object> user= null;
        Map<String, Object> user = Maps.newHashMap();
        user.put("uuid", 1);
        user.put("username", "czp");
        user.put("pwd", "123456");
        user.put("uuid", 4);
        Optional<Map<String, Object>> optional = Optional.ofNullable(user);
        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            System.out.println("传入对象为空");
        }
        optional.ifPresent(map -> {
            System.out.println(map);
        });
        System.out.println(optional.orElse(Maps.newHashMap()));
    }

}