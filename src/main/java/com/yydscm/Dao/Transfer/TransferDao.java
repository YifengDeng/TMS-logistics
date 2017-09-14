package com.yydscm.Dao.Transfer;

import com.google.common.base.Strings;
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

/**
 * Created by PC20170414 on 2017/7/17.
 */

@Repository
public class TransferDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     * 保存中转信息
     *
     * @param
     * @return
     */
    public Long saveTransfer(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_transfer");//设置表名
        simpleJdbcInsert.setGeneratedKeyName("uuid");//设置主键名，添加成功后返回主键的值
        return simpleJdbcInsert.executeAndReturnKey(map).longValue();
    }

    /**
     * 修改中转信息
     *
     * @param map
     * @return
     */
    public int updateTransfer(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_transfer", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }


    /**
     * 中转信息列表
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> selectTransfer(Map<String, Object> map) {
        String Sql = "select t.to_point_uuid,if(t.to_company_name is null,if(l.company_name is null,l.point_name,concat(l.company_name,'(',p.point_name,')')),t.to_company_name) point_name," +
                "date_format(t.transfer_date,'%Y-%m-%d %H:%i') transfer_date,t.transfer_fee," +
                "t.point_uuid,p.point_name operation_point_name,t.operator_id,u.user_name,t.transfer_type,t.transit_destination,t.billing_uuid,t.remark " +
                " from t_transfer t " +
                " left join (select ll.uuid,ll.point_name,lo.company_name from t_logistics_point ll " +
                " left join t_logistics lo on lo.uuid = ll.logistics_uuid) l on l.uuid = t.to_point_uuid " +
                " left join t_logistics_point p on p.uuid = t.point_uuid " +
                " left join t_user u on u.uuid = t.operator_id " +
                " where 1=1 and (t.to_point_uuid = :point_uuid or t.point_uuid = :point_uuid) ";
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("user_name", map))) {
            Sql += " and u.user_name like '%" + HsUtil.noAttribute("user_name", map) + "%'";
        }
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("transfer_type", map))) {
            Sql += " and t.transfer_type = :transfer_type ";
        }
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("startTime", map))) {
            Sql += "and DATE_FORMAT(t.transfer_date,'%Y-%m-%d') >= :startTime ";
        }
        if (!Strings.isNullOrEmpty(HsUtil.noAttribute("endTime", map))) {
            Sql += "and DATE_FORMAT(t.transfer_date,'%Y-%m-%d') <= :endTime ";
        }
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }

    /**
     * 获取平台内所有网点，除了登录网点
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> getPointForNotOwn(Map<String, Object> map) {
        String Sql = "select p.uuid,concat(l.company_name,'(',p.point_name,')') point_name from t_logistics_point p " +
                "left join t_logistics l on l.uuid=p.logistics_uuid " +
                "where 1=1 and l.company_name is not null and p.uuid != :point_uuid and p.logistics_uuid != :point_uuid";
        return namedParameterJdbcTemplate.queryForList(Sql, map);
    }








}
