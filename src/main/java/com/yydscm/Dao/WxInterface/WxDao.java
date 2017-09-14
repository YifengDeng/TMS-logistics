package com.yydscm.Dao.WxInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.yydscm.Util.DBUtil;
import com.yydscm.Util.HsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

@Repository
public class WxDao {
    @Autowired
    @Qualifier("WxJdbcTemplate")
    NamedParameterJdbcTemplate wxnamejdbc;
    @Autowired
    JdbcTemplate jdbcTemplate;


    //查询诚信会员列表
    public List<Map<String, Object>> selectCreditUserList(Map<String, Object> map) {
        String Sql = "select u.uuid,u.company_uuid,u.company_name,u.user_name,u.phone_one,u.credit_statu,date_format(u.apply_time,'%Y-%m-%d %H:%i') apply_time,p.point_addr "
                + "from yyd_t_user u "
                + "left join yyd_t_logistics_point p on u.company_uuid = p.uuid "
                + "where u.credit_statu != 0 ";
        if (Objects.nonNull(HsUtil.noAttribute("startTime", map)) && !"".equals(HsUtil.noAttribute("startTime", map)) && !"null".equals(HsUtil.noAttribute("startTime", map))) {
            Sql += "and DATE_FORMAT(u.apply_time,'%Y-%m-%d') >= :startTime ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("endTime", map)) && !"".equals(HsUtil.noAttribute("endTime", map)) && !"null".equals(HsUtil.noAttribute("endTime", map))) {
            Sql += "and DATE_FORMAT(u.apply_time,'%Y-%m-%d') <= :endTime ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("phone_one", map)) && !"".equals(HsUtil.noAttribute("phone_one", map))) {
            Sql += "and u.phone_one like '%" + HsUtil.noAttribute("phone_one", map) + "%' ";
        }
        Sql = Sql + "order by u.apply_time desc ";
        return wxnamejdbc.queryForList(Sql, map);
    }


    //获取用户基本信息
    public Map<String,Object> selectCreditUserInfo(Map<String, Object> map) {
        String Sql = "select u.*,p.* from yyd_t_user u " +
                "left join yyd_t_logistics_point p on u.company_uuid = p.uuid " +
                "where u.uuid = :uuid";
        return wxnamejdbc.queryForMap(Sql, map);
    }

    //添加公司信息
    public int insertCreditCompany(Map<String, Object> userInfo) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_logistics");//设置表名
        simpleJdbcInsert.setGeneratedKeyName("uuid");//设置主键名，添加成功后返回主键的值

        return simpleJdbcInsert.executeAndReturnKey(userInfo).intValue();
    }

    //添加网点信息
    public int insertCreditPoint(Map<String, Object> userInfo) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_logistics_point");//设置表名
        simpleJdbcInsert.setGeneratedKeyName("uuid");//设置主键名，添加成功后返回主键的值

        return simpleJdbcInsert.executeAndReturnKey(userInfo).intValue();
    }

    //添加用户信息
    public int insertCreditUserInfo(Map<String, Object> userInfo) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_user");//设置表名

        return simpleJdbcInsert.execute(userInfo);
    }

    //修改状态
    public int updateCreditStatu(Map<String, Object> map) {
        return DBUtil.excuteUpdate("yyd_t_user", map, new String[]{"uuid"}, wxnamejdbc);
    }
}
