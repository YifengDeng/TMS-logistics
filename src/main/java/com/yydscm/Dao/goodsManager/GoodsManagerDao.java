package com.yydscm.Dao.goodsManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GoodsManagerDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    //查询用户所属网点id
    public Map<String, Object> selectPointId(Map<String, Object> mapTow) {
        String Sql = "select company_uuid from t_user where uuid = :uuid";
        return namedParameterJdbcTemplate.queryForMap(Sql, mapTow);
    }

    //添加货物
    public int addGoods(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_comm_goods");//设置表名
        return simpleJdbcInsert.execute(map);
    }

    //删除货物
    public int deleteGoods(Map<String, Object> map) {
        String sql = "delete from t_comm_goods WHERE uuid = :uuid";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    //查询所有货物
    public List<Map<String, Object>> selectGoodsList(Map<String, Object> map) {
        String Sql = "select uuid,goods_name from t_comm_goods where point_uuid = :point_uuid";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

}
