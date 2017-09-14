package com.yydscm.Dao.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class ReportDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String str = " f.declared_value,f.valuation_fee,f.delivery_fee,f.advance,f.receiving_fee,f.handling_fee,f.packing_fee,f.upstair_fee,"
            + "f.forklift_fee,f.return_fee,f.under_charge_fee,f.warehousing_fee,f.collection_fee,f.freight,f.total_freight,f.total_freight_receipts,"
            + "f.payment_method,f.cash_payment,f.collect_payment,f.default_of_payment,f.back_payment,f.monthly_payment,f.payment_deduction ";

    //查询某一天的开票记录
    public List<Map<String, Object>> selectReportListByDay(Map<String, Object> map) {
        String Sql = "select b.uuid,b.quantity," + str + " from t_billing b ,t_billing_fee f "
                + "where b.owner = :company_uuid and b.uuid = f.billing_uuid and b.invoice_status != 9 "
                + "and DATE_FORMAT(b.invoice_date,'%Y-%m-%d') = :day  ";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }


}
