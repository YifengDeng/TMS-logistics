package com.yydscm.Dao.Billing;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yydscm.Util.DBUtil;
import com.yydscm.Util.HsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BillingDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     * 保存运单
     *
     * @param map
     * @return
     */
    public Long addBilling(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_billing");//设置表名
        simpleJdbcInsert.setGeneratedKeyName("uuid");//设置主键名，添加成功后返回主键的值
        return simpleJdbcInsert.executeAndReturnKey(map).longValue();
    }

    /**
     * 获取票据详情
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> selectBillingByUuid(Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("uuid", map);
        String Sql = "select b.uuid,b.waybill_number,b.initial_station_id,b.input_article_number,l.company_name,l.point_name startStation,l.point_phone startPoint_phone,"
                + "l.logistics_notice,l.point_addr startPoint_addr,o.user_name operation_user_name,s.receipts_delivery_fee,s.receipts_total_freight,s.receipts_collection_fee,"
                + "b.terminal_station_id,p.point_name endStation,p.point_phone point_owner_phone_o,p.point_addr,concat(l.point_name,'--',p.point_name) line,b.owner,lp.point_name owner_name,"
                + "DATE_FORMAT(b.invoice_date,'%Y-%m-%d %H:%i') invoice_date,b.consigner_uuid,b.consigner,b.consigner_phone,b.consigner_addr,b.consignee,"
                + "b.consignee_phone,b.consignee_addr,b.goods_name,b.quantity,b.packaging,b.weight,b.volume,"
                + "b.delivery_method,b.number_of_copies,b.drawer_uuid,u.user_name,b.invoice_status,b.article_number,if(b.update_time is null,null,DATE_FORMAT(b.update_time,'%Y-%m-%d %H:%i')) update_time,b.operation_user_uuid,"
                + "b.transit_destination,b.loading_batches_id,lb.batch_number,DATE_FORMAT(lb.loading_time,'%Y-%m-%d %H:%i') loading_time,lb.plate_number,lb.driver_name,"
                + "lb.driver_phone,b.source,b.is_unusual,b.remarks,f.uuid as billing_fee_uuid,"
                + "f.declared_value,f.valuation_fee,f.delivery_fee,f.advance,f.receiving_fee,f.handling_fee,"
                + "f.packing_fee,f.upstair_fee,f.forklift_fee,f.return_fee,f.under_charge_fee,f.warehousing_fee,"
                + "f.collection_fee,f.freight,f.total_freight,f.total_freight_receipts,f.payment_method,"
                + "f.cash_payment,f.collect_payment,f.default_of_payment,f.back_payment,f.monthly_payment,f.payment_deduction "
                + "from t_billing b "
                + "left join t_billing_fee f on f.billing_uuid=b.uuid "
                + "left join (select l1.uuid,l1.point_name,l1.logistics_uuid,l1.point_addr,l1.point_owner_phone_o,l1.point_phone,c.company_name,ln.logistics_notice from t_logistics_point l1 "
                + "left join (select group_concat(concat(logistics_sort,'、',logistics_notice)) logistics_notice,logistics_uuid from t_logistics_notice group by logistics_uuid) ln "
                + "on ln.logistics_uuid=l1.logistics_uuid "
                + "left join t_logistics c on c.uuid=l1.logistics_uuid) l on l.uuid=b.initial_station_id "
                + "left join t_logistics_point p on p.uuid=b.terminal_station_id "
                + "left join t_logistics_point lp on lp.uuid=b.owner "
                + "left join t_loading_batches lb on lb.uuid = b.loading_batches_id "
                + "left join t_user u on u.uuid=b.drawer_uuid "
                + " left join t_user o on o.uuid=b.operation_user_uuid "
                + " left join t_billing_sign s on s.billing_uuid=b.uuid "
                + "where b.uuid in (" + uuids + ")";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    /**
     * 我也有超级查询
     *
     * @param map
     * @return
     */
    @SuppressWarnings("static-access")
    public List<Map<String, Object>> selectBilling(Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("uuid", map);
        String Sql = "select b.uuid,b.waybill_number,b.initial_station_id,b.input_article_number,l.company_name,l.point_name startStation,l.point_phone startPoint_phone," +
                "l.logistics_notice,l.point_addr startPoint_addr,o.user_name operation_user_name,s.receipts_delivery_fee,s.receipts_total_freight,s.receipts_collection_fee,"
                + "b.terminal_station_id,p.point_name endStation,p.point_phone point_owner_phone_o,p.point_addr,concat(l.point_name,'--',p.point_name) line,b.owner,lp.point_name owner_name,"
                + "DATE_FORMAT(b.invoice_date,'%Y-%m-%d %H:%i') invoice_date,b.consigner_uuid,b.consigner,b.consigner_phone,b.consigner_addr,b.consignee,"
                + "b.consignee_phone,b.consignee_addr,b.goods_name,b.quantity,b.packaging,b.weight,b.volume,"
                + "b.delivery_method,b.number_of_copies,b.drawer_uuid,u.user_name,b.invoice_status,b.article_number,if(b.update_time is null,null,DATE_FORMAT(b.update_time,'%Y-%m-%d %H:%i')) update_time,b.operation_user_uuid,"
                + "b.transit_destination,b.loading_batches_id,lb.batch_number,DATE_FORMAT(lb.loading_time,'%Y-%m-%d %H:%i') loading_time,lb.plate_number,lb.driver_name,"
                + "lb.driver_phone,lb.pay_the_freight,b.source,b.is_unusual,b.remarks,f.billing_uuid,"
                + "f.declared_value,f.valuation_fee,f.delivery_fee,f.advance,f.receiving_fee,f.handling_fee,"
                + "f.packing_fee,f.upstair_fee,f.forklift_fee,f.return_fee,f.under_charge_fee,f.warehousing_fee,"
                + "f.collection_fee,f.freight,f.total_freight,f.total_freight_receipts,f.payment_method,"
                + "f.cash_payment,f.collect_payment,f.default_of_payment,f.back_payment,f.monthly_payment,f.payment_deduction "
                + "from t_billing b "
                + "left join t_billing_fee f on f.billing_uuid=b.uuid "
                + "left join (select l1.uuid,l1.point_name,l1.logistics_uuid,l1.point_addr,l1.point_owner_phone_o,l1.point_phone,c.company_name,ln.logistics_notice from t_logistics_point l1 "
                + "left join (select group_concat(concat(logistics_sort,'、',logistics_notice)) logistics_notice,logistics_uuid from t_logistics_notice group by logistics_uuid) ln "
                + "on ln.logistics_uuid=l1.logistics_uuid "
                + "left join t_logistics c on c.uuid=l1.logistics_uuid) l on l.uuid=b.initial_station_id "
                + "left join t_logistics_point p on p.uuid=b.terminal_station_id "
                + "left join t_logistics_point lp on lp.uuid=b.owner "
                + "left join t_loading_batches lb on lb.uuid = b.loading_batches_id "
                + "left join t_user u on u.uuid=b.drawer_uuid "
                + " left join t_user o on o.uuid=b.operation_user_uuid "
                + " left join t_billing_sign s on s.billing_uuid=b.uuid "
                + "where 1=1 and b.invoice_status != 9 ";
        if (Objects.nonNull(uuids)) {
            Sql += " and b.uuid in (" + uuids + ")";
        } else {
            Sql = this.str(Sql, map);
        }

        //select_type:1、装车加载；2、发车加载；3、装车历史
        if (HsUtil.noAttribute("select_type", map) == "1") {
            Sql += " order by b.invoice_date asc";
        } else if (HsUtil.noAttribute("select_type", map) == "2") {
            Sql += " order by lb.batch_number asc";
        } else if (HsUtil.noAttribute("select_type", map) == "3") {
            Sql += " order by lb.batch_number desc";
        } else {
            Sql += " order by b.invoice_date desc";
        }

        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }


    /**
     * 修改运单
     *
     * @param map
     * @return
     */
    public int updateBilling(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_billing", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    /**
     * 修改运单状态
     * 到货、签收、中转等可用
     *
     * @param map
     * @return
     */
    public int updateBillingOFInvoiceStatus(String property, Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("uuid", map);
        String sql = "update t_billing set " + property + "=:" + property;
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("transit_destination", map))) {
            sql += ",transit_destination=:transit_destination";
        }
        sql += " where uuid in (" + uuids + ")";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    /**
     * APP端装车前获取数据
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> selectBillingForLoading(Map<String, Object> map) {
        String Sql = "select b.uuid,b.initial_station_id,b.input_article_number,l.point_name initial_station_name,b.terminal_station_id,"
                + "p.point_name terminal_station_name,if(b.transit_destination is null,p.point_name,b.transit_destination) transit_destination,o.user_name operation_user_name,"
                + "b.goods_name,b.packaging,b.quantity,b.waybill_number,if(b.update_time is null,null,DATE_FORMAT(b.update_time,'%Y-%m-%d %H:%i')) update_time,b.operation_user_uuid,"
                + "DATE_FORMAT(b.invoice_date,'%Y-%m-%d %H:%i:%s') invoice_date from t_billing b"
                + " left join t_logistics_point p on p.uuid=b.terminal_station_id"
                + " left join t_logistics_point l on l.uuid=b.initial_station_id"
                + " left join t_user o on o.uuid=b.operation_user_uuid "
                + " where b.invoice_status = 1 and b.terminal_station_id is not null and b.initial_station_id = :initial_station_id"
                + " order by b.terminal_station_id,b.uuid";
        System.err.println(Sql);
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    /**
     * 装车流程，修改运单装车批次号
     *
     *  'uuid' 批量时，逗号隔开
     * @param map
     * @return
     */
    public int updateBillingOFLoadingBatchsIdByUuid(Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("uuid", map);
        String sql = "update t_billing set loading_batches_id=:loading_batches_id,invoice_status=:invoice_status where uuid in (" + uuids + ")";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    /**
     * 发车流程 只需修改状态
     *
     * @param map
     * @return
     */
    public int updateBillingOFLoadingBatchsIdForGo(Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("uuid", map);
        String sql = "update t_billing set invoice_status=:invoice_status where uuid in (" + uuids + ")";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    /**
     * 保存运单费用
     *
     * @param map
     * @return
     */
    public Long addBillingFee(Map<String, Object> map) {
        List<String> list = Lists.newArrayList();
        map.forEach((s, o) -> list.add(s));
        String[] cols = list.toArray(new String[map.entrySet().size()]);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_billing_fee");
        simpleJdbcInsert.setGeneratedKeyName("uuid");
        simpleJdbcInsert.usingColumns(cols);
        return simpleJdbcInsert.executeAndReturnKey(map).longValue();
    }

    /**
     * 修改运单费用
     *
     * @param map
     * @return
     */
    public int updateBillingFee(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_billing_fee", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    /**
     * 获取网点最大运单号
     * initial_station_id 开票网点id
     *
     * @param map
     * @return
     */
    public String getMaxWaybillNumber(Map<String, Object> map) {
        String Sql = "SELECT max(waybill_number) waybill_number FROM `t_billing` where initial_station_id=:initial_station_id " +
                "and DATE_FORMAT(invoice_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') and waybill_number is not null";
        return namedParameterJdbcTemplate.queryForObject(Sql, map, String.class);
    }

    /**
     * 获取网点最后一个货号
     *
     * @param map
     * @return
     */
    public String getMaxArticle_number(Map<String, Object> map) {
        String Sql = "SELECT article_number FROM t_billing WHERE uuid =(SELECT max(uuid) FROM t_billing WHERE initial_station_id = :initial_station_id)";
        return namedParameterJdbcTemplate.queryForObject(Sql, map, String.class);
    }

    /**
     * 获取到站网点名称
     *
     * @param map
     * @return
     */
    public String getPoint_name(Map<String, Object> map) {
        String Sql = "select point_name from t_logistics_point where uuid=:terminal_station_id";
        return namedParameterJdbcTemplate.queryForObject(Sql, map, String.class);
    }


    /**
     * 获取网点标识（字母）
     *
     * @param map
     * @return
     */
    public String getPoint_sign(Map<String, Object> map) {
        String Sql = "select point_sign from t_logistics_point where uuid = :initial_station_id";
        return namedParameterJdbcTemplate.queryForObject(Sql, map, String.class);
    }


    /**
     * 获取到站信息
     */
    public List<Map<String, Object>> searchTerminalStation(Map<String, Object> map) {
        String Sql = "select uuid,point_name,logistics_uuid from t_logistics_point "
                + "where uuid != :uuid and (logistics_uuid = :company_uuid "
                + "or uuid in (select partner_point_id from t_partner where company_uuid = :company_uuid and partner_status = 1))";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, map);
        return list;
    }

    /**
     * 获取网点开票历史目的地
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchDestination(Map<String, Object> map) {
        String Sql = "select DISTINCT transit_destination value from t_billing "
                + "where initial_station_id = :initial_station_id and transit_destination is not null";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, map);
        return list;
    }

    /**
     * 获取货主信息
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchConsigner(Map<String, Object> map) {
        String consigner = Objects.nonNull(HsUtil.noAttribute("consigner", map)) ? HsUtil.noAttribute("consigner", map) : "";
        String Sql = "select consigner value,consigner_phone phone,consigner_addr address from t_billing "
                + "where consigner is not null and consigner != '' and initial_station_id = :point_uuid "
                + "and consigner like '" + consigner + "%' group by consigner,consigner_phone,consigner_addr order by count(uuid) desc";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, map);
        return list;
    }

    /**
     * 获取收货人信息
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchConsignee(Map<String, Object> map) {
        String consigner = Objects.nonNull(HsUtil.noAttribute("consigner", map)) ? HsUtil.noAttribute("consigner", map) : "";
        String Sql = "select consignee value,consignee_phone phone,consignee_addr address "
                + "from t_billing where consignee is not null and consignee != '' and initial_station_id = :point_uuid ";
        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            Sql += "and consigner like '" + consigner + "%'";
        }
        String consignee = Objects.nonNull(HsUtil.noAttribute("consignee", map)) ? HsUtil.noAttribute("consignee", map) : "";
        Sql += "and consignee like '" + consignee + "%' "
                + "group by consignee,consignee_phone,consignee_addr order by count(uuid) desc";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, map);
        return list;
    }

    /**
     * 获取货物信息
     * 存在问题
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchGoods_name(Map<String, Object> map) {
        String Sql = "select replace(b.goods_name,' ','') value,b.packaging,b.quantity,bf.total_freight from t_billing b "
                + "left join t_billing_fee bf on b.uuid=bf.billing_uuid "
                + "where 1=1 and b.initial_station_id = :point_uuid and b.goods_name is not null and b.goods_name !='' ";
        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            Sql += "and b.consigner like '" + HsUtil.noAttribute("consigner", map).toString() + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee", map)) && !"".equals(HsUtil.noAttribute("consignee", map))) {
            Sql += "and b.consignee like '" + HsUtil.noAttribute("consignee", map).toString() + "%' ";
        }
        String goods_name = Objects.nonNull(HsUtil.noAttribute("goods_name", map)) ? HsUtil.noAttribute("goods_name", map) : "";
        Sql += "and b.goods_name like '%" + goods_name + "%' "
                + "group by b.goods_name,b.packaging,b.quantity,bf.total_freight order by b.quantity desc";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, map);
        return list;
    }

    /**
     * 运单查询SQL 公共条件拼接
     *
     * @param Sql
     * @param map
     * @return
     */
    public static String str(String Sql, Map<String, Object> map) {
        if (Objects.nonNull(HsUtil.noAttribute("payment_method", map)) && !"".equals(HsUtil.noAttribute("payment_method", map))) {
            Sql += "and f.payment_method = :payment_method ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("invoice_status", map)) && !"".equals(HsUtil.noAttribute("invoice_status", map))) {
            Sql += "and b.invoice_status in (" + HsUtil.noAttribute("invoice_status", map) + ") ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("feeType", map)) && !"".equals(HsUtil.noAttribute("feeType", map))) {
            String feeType = (String) map.get("feeType");
            Sql += "and f." + feeType + "> 0 ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("article_number", map)) && !"".equals(HsUtil.noAttribute("article_number", map))) {
            Sql += "and b.article_number like '%" + HsUtil.noAttribute("article_number", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("goods_name", map)) && !"".equals(HsUtil.noAttribute("goods_name", map))) {
            Sql += "and b.goods_name like '%" + HsUtil.noAttribute("goods_name", map) + "%' ";
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
            Sql += "and b.consignee_phone  like '%" + HsUtil.noAttribute("consignee_phone", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("startTime", map)) && !"".equals(HsUtil.noAttribute("startTime", map))) {
            Sql += "and DATE_FORMAT(b.invoice_date,'%Y-%m-%d') >= :startTime ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("endTime", map)) && !"".equals(HsUtil.noAttribute("endTime", map))) {
            Sql += "and DATE_FORMAT(b.invoice_date,'%Y-%m-%d') <= :endTime ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("initial_station_id", map)) && !"".equals(HsUtil.noAttribute("initial_station_id", map))) {
            Sql += "and b.initial_station_id = :initial_station_id ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("terminal_station_id", map)) && !"".equals(HsUtil.noAttribute("terminal_station_id", map))) {
            Sql += "and b.terminal_station_id = :terminal_station_id ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("batch_number", map)) && !"".equals(HsUtil.noAttribute("batch_number", map))) {
            Sql += "and lb.batch_number like '%" + HsUtil.noAttribute("batch_number", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("plate_number", map)) && !"".equals(HsUtil.noAttribute("plate_number", map))) {
            Sql += "and lb.plate_number like '%" + HsUtil.noAttribute("plate_number", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("driver_name", map)) && !"".equals(HsUtil.noAttribute("driver_name", map))) {
            Sql += "and lb.driver_name like '%" + HsUtil.noAttribute("driver_name", map) + "%' ";
        }
        return Sql;
    }

    public Double getInsureRate(Long logistics_uuid) {
        String sql = "SELECT Insured_rate FROM t_system_parameter WHERE logistics_uuid=:logistics_uuid";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("logistics_uuid", logistics_uuid);
        return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, Double.class);
    }

    public List<Map<String, Object>> searchRemarks(Map<String, Object> map) {
        String sql = "SELECT remarks FROM t_billing WHERE initial_station_id=:point_uuid";
        return namedParameterJdbcTemplate.queryForList(sql, map);
    }

    public Long SaveInsureRate(Map<String, Object> map) {
        List<String> list = Lists.newArrayList();
        map.forEach((s, o) -> {
            list.add(s);
        });
        String[] columns = list.toArray(new String[list.size()]);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_system_parameter");//设置表名
        simpleJdbcInsert.usingColumns(columns);
        simpleJdbcInsert.setGeneratedKeyName("uuid");//设置主键名，添加成功后返回主键的值
        return simpleJdbcInsert.executeAndReturnKey(map).longValue();
    }

    public int updateSystemParameter(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_system_parameter", map, new String[]{"logistics_uuid"}, namedParameterJdbcTemplate);
    }

    /**
     * 选中运单的合计
     *
     * @param map
     * @return
     */
    public Map<String, Object> billingsSumByUuid(Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("uuid", map);
        String Sql = "select lp.company_name,lp.point_name,count(1) totalBill,date_format(now(),'%Y-%m-%d') today,sum(n.quantity) quantity,round(sum(n.weight),2) weight,round(sum(n.volume),2) volume,round(sum(n.cash_payment),2) cash_payment,"
                + "round(sum(n.collect_payment),2) collect_payment,sum(n.default_of_payment) default_of_payment,round(sum(n.back_payment),2) back_payment, "
                + "round(sum(n.monthly_payment),2) monthly_payment,sum(n.payment_deduction) payment_deduction,"
                + "sum(n.declared_value) declared_value,round(sum(n.valuation_fee),2) valuation_fee,round(sum(n.delivery_fee),2) delivery_fee,"
                + "round(sum(n.advance),2) advance,round(sum(n.receiving_fee),2) receiving_fee,round(sum(n.handling_fee),2) handling_fee,round(sum(n.packing_fee),2) packing_fee,"
                + "round(sum(n.upstair_fee),2) upstair_fee,round(sum(n.forklift_fee),2) forklift_fee,round(sum(n.return_fee),2) return_fee,round(sum(n.under_charge_fee),2) under_charge_fee,"
                + "round(sum(n.warehousing_fee),2) warehousing_fee,round(sum(n.collection_fee),2) collection_fee,round(sum(n.freight),2) freight,round(sum(n.total_freight),2) total_freight "
                + "from (select b.*,f.declared_value,f.valuation_fee,f.delivery_fee,f.advance,f.receiving_fee,f.handling_fee,f.packing_fee,f.upstair_fee,"
                + "f.forklift_fee,f.return_fee,f.under_charge_fee,f.warehousing_fee,f.collection_fee,f.freight,f.total_freight,f.total_freight_receipts,"
                + "f.payment_method,f.cash_payment,f.collect_payment,f.default_of_payment,f.back_payment,f.monthly_payment,f.payment_deduction "
                + " from t_billing b left join t_billing_fee f on b.uuid=f.billing_uuid where b.uuid in (" + uuids + ")) n," +
                "(select p.point_name,l.company_name from t_logistics_point p left join t_logistics l on l.uuid=p.logistics_uuid where p.uuid=:point_uuid) lp ";
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }


}
