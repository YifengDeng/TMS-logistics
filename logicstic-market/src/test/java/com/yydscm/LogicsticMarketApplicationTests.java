package com.yydscm;

import com.google.common.collect.Maps;
import com.yydscm.Util.DBUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class LogicsticMarketApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("user", "system");
        Object object = redisTemplate.opsForValue().get("user");
        System.out.println(object);
    }

    @Test
    public void DataBaseTest() {
        String Sql = "SELECT * FROM yyd_t_billing WHERE uuid IN (:uuid)";
        Map<String, Object> param = new HashMap<>();
        List uuids = Arrays.asList(376, 377, 378, 379);
        param.put("uuid", uuids);
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, param);
        System.out.println();

    }

    @Test
    public void DataBaseTest2() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map map1 = new HashMap();
        map1.put("test_col", "测试1");//需要赋值的字段
        simpleJdbcInsert.withTableName("test_table");//设置表名
        simpleJdbcInsert.setGeneratedKeyName("id");//设置主键名，添加成功后返回主键的值
//        KeyHolder keyHoler = simpleJdbcInsert.executeAndReturnKeyHolder(map1);
//        System.out.println(keyHoler.getKey().intValue());
        System.out.println(simpleJdbcInsert.executeAndReturnKey(map1).intValue());
    }

    /**
     * 批量添加
     */
    @Test
    public void BatchInsert() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map[] maps = new Map[10];
        for (int i = 0; i < 10; i++) {
            Map map = new HashMap();
            map.put("test_col", "测试" + i);
            maps[i] = map;
        }
        simpleJdbcInsert.withTableName("test_table");
        int[] i = simpleJdbcInsert.executeBatch(maps);
        System.out.println(i);

    }


    @Test
    public void UpdateTest() {
        Map map = new HashMap();
        map.put("id", 64);
        map.put("test_col", "update_test");
        String tablename = "test_table";
        String sql = null;
        try {
            DBUtil.excuteUpdate(tablename, map, new String[]{"id"}, namedParameterJdbcTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Objects.nonNull(sql)) {
            namedParameterJdbcTemplate.update(sql, map);
        }
    }

    @Test
    public void testNull() {
        String sql = "SELECT * FROM test_table";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(sql, Maps.newHashMap());
        Assert.assertEquals(list.size(), 2);
    }


}
