package com.yydscm.Dao.partners;

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
public class PartnersDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    //搜索
    public List<Map<String, Object>> selectCompanyList(Map<String, Object> map) {
        String Sql = "select uuid,company_name,company_addr from t_logistics "
                + "where company_status = 1 and uuid != :uuid ";
        if (Objects.nonNull(HsUtil.noAttribute("company_name", map)) && !"".equals(HsUtil.noAttribute("company_name", map))) {
            Sql += "and company_name like '%" + HsUtil.noAttribute("company_name", map) + "%' ";
        }
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //搜索子网点
    public List<Map<String, Object>> selectPointList(Map<String, Object> map) {
        String Sql = "select uuid,logistics_uuid,point_name,point_addr from t_logistics_point "
                + "where point_status = 1 and logistics_uuid = :uuid "
                + "and uuid not in (select partner_point_id from t_partner where partner_company_id = :uuid and company_uuid = :company_uuid) ";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //添加合作伙伴
    public int[] addPartners(Map<String, Object>[] maps) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_partner");//设置表名
        return simpleJdbcInsert.executeBatch(maps);
    }

    //查询合作伙伴列表
    public List<Map<String, Object>> selectPartnersList(Map<String, Object> map) {
        String Sql = "select p.*,l.company_name,t.point_name from t_partner p,t_logistics l,t_logistics_point t "
                + "where p.company_uuid = :company_uuid and p.partner_company_id = l.uuid and p.partner_point_id = t.uuid ";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //禁用或者启用合作伙伴
    public int updatePartnersStatus(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_partner", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

}
