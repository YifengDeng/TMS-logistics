package com.yydscm.Dao.alias;

import com.yydscm.Util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AliasDao {


    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    //添加别名
    public int addAlias(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_alias");//设置表名
        return simpleJdbcInsert.execute(map);
    }

    //修改别名
    public int updateAlias(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_alias", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    //删除别名
    public int deleteAlias(Map<String, Object> map) {
        String sql = "delete from t_alias WHERE uuid = :uuid";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    //查询别名列表
    public List<Map<String, Object>> selectAliasList(Map<String, Object> map) {
        String Sql = "select uuid,name from t_alias where logistics_uuid = :logistics_uuid order by create_time desc";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }


}
