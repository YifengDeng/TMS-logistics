package com.yydscm.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzhaopeng on 2017/7/20.
 */
@Repository
public class IndexDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Map<String, Object>> getGoodsReceipt(Map params) {
        String sql = "SELECT f.freight,f.collection_fee,DATE_FORMAT(b.invoice_date,'%Y-%m-%d') as invoice_date FROM " +
                "t_billing b LEFT JOIN t_billing_fee f ON b.uuid=f.billing_uuid " +
                "WHERE b.initial_station_id IN (:company_uuid) " +
                "AND " +
                "b.invoice_date BETWEEN :startdate AND :enddate " +
                "AND b.invoice_status<>9 " +
                "ORDER BY b.invoice_date ASC ";
        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> getChild(Long company_uuid) {
        String sql = "SELECT uuid FROM t_logistics_point WHERE logistics_uuid=(SELECT logistics_uuid FROM t_logistics_point WHERE uuid=:uuid)";
        return namedParameterJdbcTemplate.queryForList(sql, new MapSqlParameterSource().addValue("uuid", company_uuid));
    }
}
