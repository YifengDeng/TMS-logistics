package com.yydscm.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.yydscm.Service.loadingService.LoadingService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTokenManagerTest {
    @Autowired
    LoadingService loadingService;

    @Test
    public void getClaimsFromToken() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODYwMjM0MTM1OCIsInVzZXIiOnsidXVpZCI6ODQ2LCJjb21wYW55X3V1aWQiOjQsImNvbXBhbnlfdHlwZSI6MCwicm9sZV91dWlkIjpudWxsLCJoZWFkX2ltZyI6bnVsbCwidXNlcl9uYW1lIjoiMTg2MDIzNDEzNTgiLCJwaG9uZV9vbmUiOiIxMzIyMzI3NTg0NyIsInBob25lX3R3byI6IiIsInB3ZCI6IiIsImNyZWF0ZXRpbWUiOjE0ODkwNjY4MjcwMDAsImxhc3RfbG9naW4iOjE0OTMzMjI0MzAwMDAsImxvZ2luX3N1bSI6MTEwLCJ1c2VyX3N0YXR1cyI6MCwicmVjaWV2ZV9tc2ciOm51bGwsInJlbWFyayI6bnVsbCwidXNlcmluZm9fcGFyYW1ldGVyIjpudWxsfSwiaWF0IjoxNDk3ODkwMTI2fQ.iDCs2NgupC1EhGAldUeYtHTddbu_-2Nv05BsbvkzMQjW1r7dKXGxwo_2HvpANyrBtX91OvPG1nBMcA290v7oiQ";
        Claims claims = null;
        claims = Jwts.parser()
                .setSigningKey("hesheng168")
                .parseClaimsJws(token)
                .getBody();
        System.out.println();
    }

    @Test
    public void Group() {
        Map<String, Object> param = Maps.newHashMap();
        param.put("initial_station_id", 2287);
        List<Map<String, Object>> list = (List<Map<String, Object>>) loadingService.selectBillingForLoading(param).getData();
//        Map<String,List<Map<String,Object> map=list.stream().collect(Collectors.groupingBy(o -> o.get("terminal_station_id").);
        ListMultimap<String, Object> listMultimap = ArrayListMultimap.create();
        list.forEach(map -> {
            listMultimap.put(map.get("terminal_station_id") + "", map);
        });
        System.out.println(listMultimap);
    }

}