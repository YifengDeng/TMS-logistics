package com.yydscm.Dao.consignor;

import com.yydscm.Util.DBUtil;
import com.yydscm.Util.HsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ConsignorDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //模糊查询sql处理
    public static String str(String Sql, Map<String, Object> map) {

        if (Objects.nonNull(HsUtil.noAttribute("company_name", map)) && !"".equals(HsUtil.noAttribute("company_name", map))) {
            Sql += "and company_name like '%" + HsUtil.noAttribute("company_name", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("market_store_no", map)) && !"".equals(HsUtil.noAttribute("market_store_no", map))) {
            Sql += "and market_store_no like '%" + HsUtil.noAttribute("market_store_no", map) + "%' ";
        }

        return Sql+" and status = 1 ";
    }


    //查询所有货主
    public List<Map<String,Object>> selectConsignorList(Map<String, Object> map){

        String Sql = "select "
                + " uuid,company_name,send_out_addr,fixedline,send_out_longitude,send_out_latitude,company_principal,head_phone_one,head_phone_two,business_scope,market_uuid,market_store_no,salespeople,registration_date,createtime,remark,point_introduction,status "
                + " from "
                + " t_consignor "
                + " where 1=1 ";

        //等待添加园区关联
        Sql +="";

        Sql=ConsignorDao.str(Sql,map);

        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }


    //添加货主
    public int addConsignor(Map<String,Object> map){

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //设置表名
        simpleJdbcInsert.withTableName("t_consignor");

        return simpleJdbcInsert.execute(map);
    }


    //修改货主
    public int updateConsignor(Map<String,Object> map){

        return DBUtil.excuteUpdate("t_consignor", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

}
