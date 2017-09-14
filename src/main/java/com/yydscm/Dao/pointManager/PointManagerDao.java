package com.yydscm.Dao.pointManager;

import com.yydscm.Util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PointManagerDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    //添加网点
    public int addPoint(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_logistics_point");//设置表名
        simpleJdbcInsert.setGeneratedKeyName("uuid");//设置主键名，添加成功后返回主键的值
        return simpleJdbcInsert.executeAndReturnKey(map).intValue();
    }


    //添加用户
    public int addUser(Map<String, Object> userMap) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_user");//设置表名
        return simpleJdbcInsert.execute(userMap);
    }

    //修改网点
    public int updatePoint(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_logistics_point", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    //查询网点详情
    public Map<String, Object> selectPoint(Map<String, Object> map) {
        String Sql = "select * from t_logistics_point where uuid = :uuid";
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }

    //添加网点人员
    public int addPointUser(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_user");//设置表名
        return simpleJdbcInsert.execute(map);
    }

    //修改网点人员
    public int UpdatePointUser(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_user", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    //查询网点所有人员
    public List<Map<String, Object>> selectPointUser(Map<String, Object> map) {
        String Sql = "select uuid,user_name,phone_one from t_user where company_uuid = :company_uuid";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //查询网点和网点人员
    public List<Map<String, Object>> selectPointAndPointUser(Map<String, Object> map) {
        String Sql = "select p.uuid,p.point_owner,p.point_owner_phone_o,p.point_name,"
                + "(select count(1) from t_user where company_uuid = p.uuid) num "
                + "from t_logistics_point p where p.logistics_uuid = :logistics_uuid";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //删除网点人员
    public int deletePointUser(Map<String, Object> map) {
        String sql = "delete from t_user where uuid = :uuid";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    //查询公司未被占用的符号
    public Map<String, Object> selectPointSign(Map<String, Object> sign) {
        String Sql = "select sign from t_logistics_sign "
                + "where sign not in (select point_sign from t_logistics_point where logistics_uuid = :logistics_uuid) limit 1";
        return namedParameterJdbcTemplate.queryForMap(Sql, sign);
    }

    //检测手机号是否重复
    public int checkPhone(Map<String, Object> map) {
        String Sql = "select count(1) from t_user where phone_one = :phone_one";
        return namedParameterJdbcTemplate.queryForObject(Sql, map, Integer.class);
    }


}
