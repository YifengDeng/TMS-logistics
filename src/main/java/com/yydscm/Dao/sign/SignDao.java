package com.yydscm.Dao.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class SignDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     * 批量插入签收信息
     *
     * @param maps
     * @return
     */
    public int batchInsert(Map<String, Object>[] maps) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_billing_sign");
        int[] i = simpleJdbcInsert.executeBatch(maps);
        return i.length;
    }


}
