package com.yydscm.Dao.Billing;

import com.yydscm.Util.DBUtil;
import com.yydscm.Util.HsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BillManagerDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    private final String str = " f.uuid billing_fee_uuid,f.declared_value,f.valuation_fee,f.delivery_fee,f.advance,f.receiving_fee,f.handling_fee,f.packing_fee,f.upstair_fee,"
            + "f.forklift_fee,f.return_fee,f.under_charge_fee,f.warehousing_fee,f.collection_fee,f.freight,f.total_freight,f.total_freight_receipts,"
            + "f.payment_method,f.cash_payment,f.collect_payment,f.default_of_payment,f.back_payment,f.monthly_payment,f.payment_deduction ";

    public static String str(String Sql) {
        Sql = "select count(1) totalBill,sum(n.quantity) quantity,round(sum(n.weight),2) weight,round(sum(n.volume),2) volume,round(sum(n.cash_payment),2) cash_payment,"
                + "round(sum(n.collect_payment),2) collect_payment,sum(n.default_of_payment) default_of_payment,round(sum(n.back_payment),2) back_payment, "
                + "round(sum(n.monthly_payment),2) monthly_payment,sum(n.payment_deduction) payment_deduction,"
                + "sum(n.declared_value) declared_value,round(sum(n.valuation_fee),2) valuation_fee,round(sum(n.delivery_fee),2) delivery_fee,"
                + "round(sum(n.advance),2) advance,round(sum(n.receiving_fee),2) receiving_fee,round(sum(n.handling_fee),2) handling_fee,round(sum(n.packing_fee),2) packing_fee,"
                + "round(sum(n.upstair_fee),2) upstair_fee,round(sum(n.forklift_fee),2) forklift_fee,round(sum(n.return_fee),2) return_fee,round(sum(n.under_charge_fee),2) under_charge_fee,"
                + "round(sum(n.warehousing_fee),2) warehousing_fee,round(sum(n.collection_fee),2) collection_fee,round(sum(n.freight),2) freight,round(sum(n.total_freight),2) total_freight "
                + "from (" + Sql + ") n ";
        return Sql;
    }

    public static String report(String Sql) {
        Sql = "select DATE_FORMAT(invoice_date,'%Y-%m-%d') days,count(1) totalBill,sum(n.quantity) quantity,round(sum(n.weight),2) weight,round(sum(n.volume),2) volume,round(sum(n.cash_payment),2) cash_payment,"
                + "round(sum(n.collect_payment),2) collect_payment,sum(n.default_of_payment) default_of_payment,round(sum(n.back_payment),2) back_payment, "
                + "round(sum(n.monthly_payment),2) monthly_payment,sum(n.payment_deduction) payment_deduction,"
                + "sum(n.declared_value) declared_value,round(sum(n.valuation_fee),2) valuation_fee,round(sum(n.delivery_fee),2) delivery_fee,"
                + "round(sum(n.advance),2) advance,round(sum(n.receiving_fee),2) receiving_fee,round(sum(n.handling_fee),2) handling_fee,round(sum(n.packing_fee),2) packing_fee,"
                + "round(sum(n.upstair_fee),2) upstair_fee,round(sum(n.forklift_fee),2) forklift_fee,round(sum(n.return_fee),2) return_fee,round(sum(n.under_charge_fee),2) under_charge_fee,"
                + "round(sum(n.warehousing_fee),2) warehousing_fee,round(sum(n.collection_fee),2) collection_fee,round(sum(n.freight),2) freight,round(sum(n.total_freight),2) total_freight "
                + "from (" + Sql + ") n ";
        return Sql;
    }


    public static String str(String Sql, Map<String, Object> map) {

        if (Objects.nonNull(HsUtil.noAttribute("payment_method", map)) && !"".equals(HsUtil.noAttribute("payment_method", map))) {
            Sql += "and f.payment_method = :payment_method ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("invoice_status", map)) && !"".equals(HsUtil.noAttribute("invoice_status", map))) {
            Sql += "and b.invoice_status = :invoice_status ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("feeType", map)) && !"".equals(HsUtil.noAttribute("feeType", map))) {
            String feeType = (String) map.get("feeType");
            Sql += "and f." + feeType + "> 0 ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("article_number", map)) && !"".equals(HsUtil.noAttribute("article_number", map))) {
            Sql += "and b.article_number like '%" + HsUtil.noAttribute("article_number", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("waybill_number", map)) && !"".equals(HsUtil.noAttribute("waybill_number", map))) {
            Sql += "and b.waybill_number like '%" + HsUtil.noAttribute("waybill_number", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            Sql += "and b.consigner like '%" + HsUtil.noAttribute("consigner", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("consigner_phone", map)) && !"".equals(HsUtil.noAttribute("consigner_phone", map))) {
            Sql += "and b.consigner_phone like '%" + HsUtil.noAttribute("consigner_phone", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee", map)) && !"".equals(HsUtil.noAttribute("consignee", map))) {
            Sql += "and b.consignee like '%" + HsUtil.noAttribute("consignee", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee_phone", map)) && !"".equals(HsUtil.noAttribute("consignee_phone", map))) {
            Sql += "and b.consignee_phone like '%" + HsUtil.noAttribute("consignee_phone", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("startTime", map)) && !"".equals(HsUtil.noAttribute("startTime", map)) && !"null".equals(HsUtil.noAttribute("startTime", map))) {
            Sql += "and DATE_FORMAT(b.invoice_date,'%Y-%m-%d') >= :startTime ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("endTime", map)) && !"".equals(HsUtil.noAttribute("endTime", map)) && !"null".equals(HsUtil.noAttribute("endTime", map))) {
            Sql += "and DATE_FORMAT(b.invoice_date,'%Y-%m-%d') <= :endTime ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("initial_station_id", map)) && !"".equals(HsUtil.noAttribute("initial_station_id", map))) {
            Sql += "and b.initial_station_id = :initial_station_id ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("terminal_station_id", map)) && !"".equals(HsUtil.noAttribute("terminal_station_id", map))) {
            Sql += "and b.terminal_station_id = :terminal_station_id ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("delivery_method", map)) && !"".equals(HsUtil.noAttribute("delivery_method", map))) {
            Sql += "and b.delivery_method = :delivery_method ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("batch_number", map)) && !"".equals(HsUtil.noAttribute("batch_number", map))) {
            Sql += "and b.loading_batches_id in (select uuid from t_loading_batches where batch_number like '%" + HsUtil.noAttribute("batch_number", map) + "%') ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("day", map)) && !"".equals(HsUtil.noAttribute("day", map))) {
            Sql += "and DATE_FORMAT(b.invoice_date,'%Y-%m-%d') = :day ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("month", map)) && !"".equals(HsUtil.noAttribute("month", map))) {
            Sql += "and DATE_FORMAT(b.invoice_date,'%Y-%m') = :month ";
        }

        return Sql;
    }

    //查询登录人员角色
    public Map<String, Object> selectUserRole(Map<String, Object> mapTow) {
        String Sql = "select company_uuid,role_uuid from t_user where uuid = :uuid";
        return namedParameterJdbcTemplate.queryForMap(Sql, mapTow);
    }

    //查询公司所有
    public List<Map<String, Object>> selectAllPointBillList(Map<String, Object> map) {
        String Sql = "select b.uuid,b.waybill_number,b.initial_station_id,b.terminal_station_id,b.owner,DATE_FORMAT(b.invoice_date,'%Y-%m-%d %H:%i') invoice_date,b.consigner_uuid,b.consigner,b.consigner_phone," +
                "b.consigner_addr,b.consignee,b.consignee_phone,b.consignee_addr,b.goods_name,b.quantity,b.packaging,b.weight,b.volume,b.delivery_method,b.input_article_number," +
                "b.number_of_copies,b.drawer_uuid,b.invoice_status,b.article_number,b.transit_destination,b.loading_batches_id,b.source,b.is_unusual," +
                "if(b.update_time is null,null,DATE_FORMAT(b.update_time,'%Y-%m-%d %H:%i')) update_time,b.operation_user_uuid,s.receipts_delivery_fee,s.receipts_total_freight,s.receipts_collection_fee," +
                "b.remarks,date_format(b.invoice_date,'%Y-%m-%d %H:%i') invoicedate," + str + ",p.point_name startStation,p.point_phone startPoint_phone,p.point_addr startPoint_addr,"
                + "t.point_phone point_owner_phone_o,t.point_name endStation,t.point_addr,u.user_name,c.batch_number,l.company_name,ln.logistics_notice,o.user_name operation_user_name "
                + "from t_billing b "
                + "left join t_billing_fee f on b.uuid = f.billing_uuid "
                + "left join t_logistics_point p on p.uuid = b.initial_station_id "
                + "left join t_logistics_point t on t.uuid = b.terminal_station_id "
                + "left join t_user u on b.drawer_uuid = u.uuid "
                + "left join t_loading_batches c on c.uuid = b.loading_batches_id "
                + "LEFT JOIN t_logistics l ON l.uuid = p.logistics_uuid "
                + " left join t_user o on o.uuid=b.operation_user_uuid "
                + " left join t_billing_sign s on s.billing_uuid=b.uuid "
                + "LEFT JOIN (select group_concat(concat(logistics_sort,'、',logistics_notice)) logistics_notice,logistics_uuid from t_logistics_notice group by logistics_uuid) ln ON ln.logistics_uuid = l.uuid "
                + "where b.owner in "
                + "  (select uuid from"
                + "	    t_logistics_point "
                + "  where logistics_uuid = "
                + "	    (select "
                + "      logistics_uuid "
                + "	    from"
                + "      t_logistics_point "
                + "    where uuid = :company_uuid) "
                + "    and point_status = 1) and b.invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = Sql + " order by b.invoice_date desc";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //查询当前网点
    public List<Map<String, Object>> selectPointBillList(Map<String, Object> map) {
        String Sql = "select b.uuid,b.waybill_number,b.initial_station_id,b.terminal_station_id,b.owner,DATE_FORMAT(b.invoice_date,'%Y-%m-%d %H:%i') invoice_date,b.consigner_uuid,b.consigner,b.consigner_phone," +
                "b.consigner_addr,b.consignee,b.consignee_phone,b.consignee_addr,b.goods_name,b.quantity,b.packaging,b.weight,b.volume,b.delivery_method," +
                "if(b.update_time is null,null,DATE_FORMAT(b.update_time,'%Y-%m-%d %H:%i')) update_time,b.operation_user_uuid,s.receipts_delivery_fee,s.receipts_total_freight,s.receipts_collection_fee," +
                "b.number_of_copies,b.drawer_uuid,b.invoice_status,b.article_number,b.transit_destination,b.loading_batches_id,b.source,b.is_unusual,b.input_article_number," +
                "b.remarks,date_format(b.invoice_date,'%Y-%m-%d %H:%i') invoicedate," + str + ",p.point_name startStation,p.point_phone startPoint_phone,p.point_addr startPoint_addr,"
                + "t.point_phone point_owner_phone_o,t.point_name endStation,u.user_name,t.point_addr,c.batch_number,l.company_name,ln.logistics_notice,o.user_name operation_user_name "
                + "from t_billing b "
                + "left join t_billing_fee f on b.uuid = f.billing_uuid "
                + "left join t_logistics_point p on p.uuid = b.initial_station_id "
                + "left join t_logistics_point t on t.uuid = b.terminal_station_id "
                + "left join t_user u on b.drawer_uuid = u.uuid "
                + "left join t_loading_batches c on c.uuid = b.loading_batches_id "
                + "LEFT JOIN t_logistics l ON l.uuid = p.logistics_uuid "
                + " left join t_user o on o.uuid=b.operation_user_uuid "
                + " left join t_billing_sign s on s.billing_uuid=b.uuid "
                + "LEFT JOIN (select group_concat(concat(logistics_sort,'、',logistics_notice)) logistics_notice,logistics_uuid from t_logistics_notice group by logistics_uuid) ln ON ln.logistics_uuid = l.uuid "
                + "where b.owner = :company_uuid and b.invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = Sql + " order by b.invoice_date desc";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //运单作废
    public int updateBillStatus(Map<String, Object> map) {
        String tablename = "t_billing";

        return DBUtil.excuteUpdate(tablename, map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    //查询公司所有相关运单
    public List<Map<String, Object>> selectAllBillSuperList(Map<String, Object> map) {
        String Sql = "select b.uuid,b.waybill_number,b.initial_station_id,b.terminal_station_id,b.owner,DATE_FORMAT(b.invoice_date,'%Y-%m-%d %H:%i') invoice_date,b.consigner_uuid,b.consigner,b.consigner_phone," +
                "b.consigner_addr,b.consignee,b.consignee_phone,b.consignee_addr,b.goods_name,b.quantity,b.packaging,b.weight,b.volume,b.delivery_method," +
                "if(b.update_time is null,null,DATE_FORMAT(b.update_time,'%Y-%m-%d %H:%i')) update_time,b.operation_user_uuid,s.receipts_delivery_fee,s.receipts_total_freight,s.receipts_collection_fee," +
                "b.number_of_copies,b.drawer_uuid,b.invoice_status,b.article_number,b.transit_destination,b.loading_batches_id,b.source,b.is_unusual,b.input_article_number," +
                "b.remarks,date_format(b.invoice_date,'%Y-%m-%d %H:%i') invoicedate," + str + ",p.point_name startStation,p.point_phone startPoint_phone,p.point_addr startPoint_addr,"
                + "t.point_phone point_owner_phone_o,t.point_name endStation,t.point_addr,u.user_name,c.batch_number,l.company_name,ln.logistics_notice,o.user_name operation_user_name "
                + "from t_billing b "
                + "left join t_billing_fee f on b.uuid = f.billing_uuid "
                + "left join t_logistics_point p on p.uuid = b.initial_station_id "
                + "left join t_logistics_point t on t.uuid = b.terminal_station_id "
                + "left join t_user u on b.drawer_uuid = u.uuid "
                + "left join t_loading_batches c on c.uuid = b.loading_batches_id "
                + "LEFT JOIN t_logistics l ON l.uuid = p.logistics_uuid "
                + " left join t_user o on o.uuid=b.operation_user_uuid "
                + " left join t_billing_sign s on s.billing_uuid=b.uuid "
                + "LEFT JOIN (select group_concat(concat(logistics_sort,'、',logistics_notice)) logistics_notice,logistics_uuid from t_logistics_notice group by logistics_uuid) ln ON ln.logistics_uuid = l.uuid "
                + "where (b.owner in "
                + "  (select uuid from t_logistics_point where logistics_uuid = "
                + "	    (select logistics_uuid from t_logistics_point where uuid = :company_uuid) and point_status = 1) "
                + "or b.terminal_station_id in "
                + "	 (select uuid from t_logistics_point where logistics_uuid = "
                + "		(select logistics_uuid from t_logistics_point where uuid = :company_uuid) and point_status = 1)) "
                + "and b.invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = Sql + " order by b.invoice_date desc";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //查询当前网点相关运单
    public List<Map<String, Object>> selectBillSuperList(Map<String, Object> map) {
        String Sql = "select b.uuid,b.waybill_number,b.initial_station_id,b.terminal_station_id,b.owner,DATE_FORMAT(b.invoice_date,'%Y-%m-%d %H:%i') invoice_date,b.consigner_uuid,b.consigner,b.consigner_phone," +
                "b.consigner_addr,b.consignee,b.consignee_phone,b.consignee_addr,b.goods_name,b.quantity,b.packaging,b.weight,b.volume,b.delivery_method," +
                "if(b.update_time is null,null,DATE_FORMAT(b.update_time,'%Y-%m-%d %H:%i')) update_time,b.operation_user_uuid,s.receipts_delivery_fee,s.receipts_total_freight,s.receipts_collection_fee," +
                "b.number_of_copies,b.drawer_uuid,b.invoice_status,b.article_number,b.transit_destination,b.loading_batches_id,b.source,b.is_unusual,b.input_article_number," +
                "b.remarks,date_format(b.invoice_date,'%Y-%m-%d %H:%i') invoicedate," + str + ",p.point_name startStation,p.point_phone startPoint_phone,p.point_addr startPoint_addr,"
                + "t.point_phone point_owner_phone_o,t.point_name endStation,t.point_addr,u.user_name,c.batch_number,l.company_name,ln.logistics_notice,o.user_name operation_user_name "
                + "from t_billing b "
                + "left join t_billing_fee f on b.uuid = f.billing_uuid "
                + "left join t_logistics_point p on p.uuid = b.initial_station_id "
                + "left join t_logistics_point t on t.uuid = b.terminal_station_id "
                + "left join t_user u on b.drawer_uuid = u.uuid "
                + "left join t_loading_batches c on c.uuid = b.loading_batches_id "
                + "LEFT JOIN t_logistics l ON l.uuid = p.logistics_uuid "
                + " left join t_user o on o.uuid=b.operation_user_uuid "
                + " left join t_billing_sign s on s.billing_uuid=b.uuid "
                + "LEFT JOIN (select group_concat(concat(logistics_sort,'、',logistics_notice)) logistics_notice,logistics_uuid from t_logistics_notice group by logistics_uuid) ln ON ln.logistics_uuid = l.uuid "
                + "where (b.owner = :company_uuid or b.terminal_station_id = :company_uuid) "
                + "and b.invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = Sql + " order by b.invoice_date desc";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //查询所有发站网点
    public List<Map<String, Object>> selectAllInitialStation(Map<String, Object> map) {
        String Sql = "select uuid,point_name from t_logistics_point "
                + "where logistics_uuid =(select logistics_uuid from t_logistics_point where uuid = :company_uuid) "
                + "and point_status = 1";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //查询当前网点发站网点
    public List<Map<String, Object>> selectInitialStation(Map<String, Object> map) {
        String Sql = "select uuid,point_name from t_logistics_point where uuid = :company_uuid";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    //查询子网点费用
    public Map<String, Object> selectAllCost(Map<String, Object> map) {
        String Sql = "select b.*," + str + " from"
                + "  t_billing b ,t_billing_fee f "
                + "where b.owner in "
                + "  (select uuid from"
                + "	    t_logistics_point "
                + "  where logistics_uuid = "
                + "	    (select "
                + "      logistics_uuid "
                + "	    from"
                + "      t_logistics_point "
                + "    where uuid = :company_uuid) "
                + "    and point_status = 1) and b.uuid = f.billing_uuid and b.invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = BillManagerDao.str(Sql);
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }

    //查询当前登录网点费用
    public Map<String, Object> selectCost(Map<String, Object> map) {
        String Sql = "select b.*," + str + " from t_billing b ,t_billing_fee f "
                + "where b.owner = :company_uuid and b.uuid = f.billing_uuid and b.invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = BillManagerDao.str(Sql);

        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }

    //查询子网点相关运单费用
    public Map<String, Object> selectAllSuperCost(Map<String, Object> map) {
        String Sql = "select b.*," + str + " from t_billing b ,t_billing_fee f "
                + "where (b.owner in "
                + "  (select uuid from t_logistics_point where logistics_uuid = "
                + "	    (select logistics_uuid from t_logistics_point where uuid = :company_uuid) and point_status = 1) "
                + "or b.terminal_station_id in "
                + "	 (select uuid from t_logistics_point where logistics_uuid = "
                + "		(select logistics_uuid from t_logistics_point where uuid = :company_uuid) and point_status = 1)) "
                + "and b.uuid = f.billing_uuid and invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = BillManagerDao.str(Sql);
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }

    //查询与当前登录网点相关运单费用
    public Map<String, Object> selectSuperCost(Map<String, Object> map) {
        String Sql = "select b.*," + str + " from t_billing b ,t_billing_fee f "
                + "where (b.owner = :company_uuid or b.terminal_station_id = :company_uuid) and b.uuid = f.billing_uuid and b.invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = BillManagerDao.str(Sql);
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }

    //查询当天的费用
    public Map<String, Object> selectCostForDay(Map<String, Object> map) {
        String Sql = "select b.*," + str + " from t_billing b ,t_billing_fee f "
                + "where b.owner = :company_uuid and b.uuid = f.billing_uuid and b.invoice_status != 9 and to_days(b.invoice_date) = to_days(now()) ";
        Sql = BillManagerDao.str(Sql);
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }

    //查询所有到站网点
    public List<Map<String, Object>> selectAllTerminalStation(Map<String, Object> map) {
        String Sql = "select uuid,point_name from t_logistics_point "
                + "where logistics_uuid = :company_uuid "
                + "or uuid in (select partner_point_id from t_partner where company_uuid = :company_uuid and partner_status = 1)";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, map);
        return list;
    }

    //查询子网点到站网点
    public List<Map<String, Object>> selectTerminalStation(Map<String, Object> map) {
        String Sql = "select uuid,point_name from t_logistics_point "
                + "where uuid != :uuid and (logistics_uuid = :company_uuid "
                + "or uuid in (select partner_point_id from t_partner where company_uuid = :company_uuid and partner_status = 1))";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, map);
        return list;
    }

    //营业报表，按按月查询或查询当天记录
    public List<Map<String, Object>> selectReportList(Map<String, Object> map) {
        String Sql = "select b.*," + str + " from t_billing b ,t_billing_fee f "
                + "where b.owner = :company_uuid and b.uuid = f.billing_uuid and b.invoice_status != 9 ";
        Sql = BillManagerDao.str(Sql, map);
        Sql = BillManagerDao.report(Sql);
        Sql = Sql + " group by days";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }


}
