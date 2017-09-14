package com.yydscm;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogisticsTmsApplicationTests {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 放入map到redis中
     */
    @Test
    public void RedisMap1() {
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList("select * from yyd_t_user", Maps.newHashMap());
        Map<String, Object> data = Maps.newHashMap();
        list.forEach(map -> {
            data.put(map.get("uuid") + "", map);
        });
        redisTemplate.opsForHash().putAll("map", data);
    }

    @Test
    public void RedisMap3() {
        Object object = redisTemplate.opsForHash().get("846", "token");
        System.out.println(object);
    }


    /**
     * 取出map
     * h:存入时redis时的key
     * o:map对象中的key
     */
    @Test
    public void RedisMap2() {
        Object object = redisTemplate.opsForHash().get("map", "5");
        System.out.println(object);
    }

    /**
     * 存取list
     */
    @Test
    public void RedisList() {
        List list = namedParameterJdbcTemplate.queryForList("select * from yyd_t_billing", new HashMap<>());
        redisTemplate.opsForList().leftPush("list", list);
        Object object = redisTemplate.opsForList().rightPop("list");
    }


}
