package com.yydscm.Dao.unusual;

import com.google.common.base.Strings;
import com.yydscm.Util.HsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UnusualDao {


    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     * 批量保存异常信息
     *
     * @param maps
     * @return
     */
    public int batchInsert(Map<String, Object>[] maps) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_unusual_log");
        int[] i = simpleJdbcInsert.executeBatch(maps);
        return i.length;
    }


    /**
     * 修改异常信息
     *
     * @param map
     * @return
     */
    public int updateUnusual(Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("uuid", map);
        String SQL = "update t_unusual_log set processed_date=:processed_date,processor=:processor," +
                "processed_results=:processed_results,status=:status where uuid in (" + uuids + ")";
        return namedParameterJdbcTemplate.update(SQL, map);
    }

    /**
     * 获取异常信息
     *
     * @param map
     * @return
     */
    public Map<String, Object> selectUnusualByUuid(Map<String, Object> map) {
        String Sql = "select * from t_unusual_log where uuid = :uuid";
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }


    /**
     * 获取异常信息列表
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> selectUnusual(Map<String, Object> map) {
        System.out.println(map.toString());
        String Sql = "select u.uuid,u.billing_uuid,DATE_FORMAT(u.record_date,'%Y-%m-%d') record_date,u.record_point_uuid,u.recorder_uuid,p.point_name," +
                "t.user_name recorder_name,u.unusual_type,u.unusual_des,u.processor,t1.user_name processor_name,DATE_FORMAT(u.processed_date,'%Y-%m-%d') processed_date," +
                "u.processed_results,u.status,if(u.status = 0,'未处理','已处理') statusStr," +
                "b.waybill_number,b.consigner,b.consigner_phone,b.consignee,b.consignee_phone,b.goods_name,b.quantity " +
                "from t_unusual_log u " +
                "left join t_user t on t.uuid = u.recorder_uuid " +
                "left join t_user t1 on t1.uuid = u.processor " +
                "left join t_billing b on b.uuid = u.billing_uuid " +
                "left join t_logistics_point p on p.uuid=u.record_point_uuid " +
                "where 1=1 ";
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("record_point_uuid", map))) {
            Sql += " and u.record_point_uuid = :record_point_uuid";
        } else {
            Sql += " and u.record_point_uuid = :record_point_uuid ";
        }
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("startTime", map))) {
            Sql += "  and DATE_FORMAT(u.record_date,'%Y-%m-%d') >= DATE_FORMAT(:startTime,'%Y-%m-%d') ";
        }
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("endTime", map))) {
            Sql += "and DATE_FORMAT(u.record_date,'%Y-%m-%d') <= DATE_FORMAT(:endTime,'%Y-%m-%d') ";
        }
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("recorder_name", map))) {
            Sql += " and t.user_name like '%" + HsUtil.noAttribute("recorder_name", map) + "%' ";
        }
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("processor_name", map))) {
            Sql += " and t1.user_name like '%" + HsUtil.noAttribute("processor_name", map) + "%'";
        }
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("status", map))) {
            Sql += " and u.status = :status";
        }
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }


}
