package com.yydscm.Dao.declaraction;

import com.yydscm.Util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DeclaractionDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //查询用户所属公司id
    public Map<String, Object> selectCompanyId(Map<String, Object> mapTow) {
        String Sql = "select logistics_uuid from t_logistics_point where uuid = (select company_uuid from t_user where uuid = :uuid)";
        return namedParameterJdbcTemplate.queryForMap(Sql, mapTow);
    }

    //添加声明
    public int addDeclaraction(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_logistics_notice");//设置表名
        return simpleJdbcInsert.execute(map);
    }

    //修改声明
    public int updateDeclaraction(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_logistics_notice", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    //删除声明
    public int deleteDeclaraction(Map<String, Object> map) {
        String sql = "delete from t_logistics_notice WHERE uuid = :uuid";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    //查询声明列表
    public List<Map<String, Object>> selectDeclaractionList(Map<String, Object> map) {
        String Sql = "select uuid,logistics_notice,logistics_sort from t_logistics_notice where logistics_uuid = :logistics_uuid order by logistics_sort asc";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }


}
