package com.yydscm.Dao.BillingLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BillingLogDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 批量插入日志信息
     *
     * @param maps
     * @return
     */
    public int batchInsert(Map<String, Object>[] maps) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_billing_log");
        int[] i = simpleJdbcInsert.executeBatch(maps);
        return i.length;
    }


}
