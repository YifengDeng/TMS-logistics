package com.yydscm.Controller;

import com.google.common.collect.Maps;
import com.yydscm.Entity.Test_table;
import com.yydscm.Exception.UpdateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzhaopeng on 2017/6/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {


    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    @Qualifier("WxJdbcTemplate")
    NamedParameterJdbcTemplate wxJdbcTemplate;

    @Test
    public void maptest() {
        String tablename = "test_table";
        Test_table test_table = new Test_table();
        test_table.setId(173l);
        test_table.setName("Java编程思想");
        test_table.setAddress("重庆北");
        Map map = Maps.newHashMap();
        map.put("id", 173);
        map.put("name", "java");
        map.put("address", "add");
        try {
//            int i = DBUtil.getUpdateSql(tablename, map, new String[]{"id"}, namedParameterJdbcTemplate);
//            int i = DBUtil.excuteUpdate(tablename, test_table, new String[]{"id"}, namedParameterJdbcTemplate);
//            System.out.println(i);
        } catch (UpdateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void namejdbc() {
//        String sql = "select * from  yyd_t_user";
//        String sql1 = "select * FROM t_user";
////        List list = wxJdbcTemplate.queryForList(sql, Maps.newHashMap());
//        List list1 = namedParameterJdbcTemplate.queryForList(sql1, Maps.newHashMap());
//        System.out.println();
    }

    @Test
    public void testGit() {
        System.out.println();
        System.out.println();
        System.out.println();
    }


}


