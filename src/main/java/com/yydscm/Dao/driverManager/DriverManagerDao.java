package com.yydscm.Dao.driverManager;

import com.yydscm.Util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DriverManagerDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //查询用户所属公司id和名称
    public Map<String, Object> selectCompanyIdAndName(Map<String, Object> mapTow) {
        String Sql = "select uuid,company_name from t_logistics "
                + "where uuid = (select logistics_uuid from t_logistics_point "
                + "where uuid = (select company_uuid from t_user where uuid = :uuid))";
        return namedParameterJdbcTemplate.queryForMap(Sql, mapTow);
    }

    //添加车辆信息
    public int addDriver(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_driver");//设置表名
        return simpleJdbcInsert.execute(map);
    }

    //修改车辆信息
    public int updateDriver(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_driver", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    //删除车辆信息
    public int deleteDriver(Map<String, Object> map) {
        String sql = "delete from t_driver WHERE uuid = :uuid";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    //查询车辆信息
    public List<Map<String, Object>> selectDriverList(Map<String, Object> map) {
        String Sql = "select company_name,plate_number,driver_name,driver_phone "
                + "from t_driver "
                + "where company_uuid = :company_uuid order by create_time desc";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }
}
