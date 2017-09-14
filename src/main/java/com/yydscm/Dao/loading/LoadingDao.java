package com.yydscm.Dao.loading;

import com.yydscm.Util.HsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class LoadingDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    //保存装车批次信息
    public Long addLoadingBatch(Map<String, Object> map) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("t_loading_batches");
        simpleJdbcInsert.setGeneratedKeyName("uuid");
        return simpleJdbcInsert.executeAndReturnKey(map).longValue();
    }

    //修改装车信息
    public int updateLoadingBatch(Map<String, Object> map) {
        String uuids = HsUtil.noAttribute("loading_batches_id", map);
        map.put("plate_number", HsUtil.noAttribute("plate_number", map));
        map.put("driver_name", HsUtil.noAttribute("driver_name", map));
        map.put("driver_phone", HsUtil.noAttribute("driver_phone", map));
        map.put("pay_the_freight", HsUtil.noAttribute("pay_the_freight", map));
        map.put("pay_the_oil_card", HsUtil.noAttribute("pay_the_oil_card", map));
        map.put("pay_back_freight", HsUtil.noAttribute("pay_back_freight", map));
        map.put("monthly_freight", HsUtil.noAttribute("monthly_freight", map));
        map.put("destination1", HsUtil.noAttribute("destination1", map));
        map.put("destination1_freight", HsUtil.noAttribute("destination1_freight", map));
        map.put("destination2", HsUtil.noAttribute("destination2", map));
        map.put("destination2_freight", HsUtil.noAttribute("destination2_freight", map));
        map.put("destination3", HsUtil.noAttribute("destination3", map));
        map.put("destination3_freight", HsUtil.noAttribute("destination3_freight", map));

        String sql = "update t_loading_batches set plate_number=:plate_number,driver_name=:driver_name,driver_phone=:driver_phone," +
                "pay_the_freight=:pay_the_freight,pay_the_oil_card=:pay_the_oil_card,pay_back_freight=:pay_back_freight,monthly_freight=:monthly_freight," +
                "destination1=:destination1,destination1_freight=:destination1_freight,destination2=:destination2,destination2_freight=:destination2_freight," +
                "destination3=:destination3,destination3_freight=:destination3_freight  where uuid in (" + uuids + ")";
        System.out.println(sql);
        return namedParameterJdbcTemplate.update(sql, map);
    }

    //获取网点当日批次最大序号
    public Map<String, Object> selectMaxLoadingBatchOfSort(Map<String, Object> map) {
        String Sql = "select MAX(sort) sort from t_loading_batches where TO_DAYS(:loading_time) = TO_DAYS(NOW()) "
                + "and loading_point = :loading_point";
        System.out.println(Sql);
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }

//	/**
//	 * 获取装车信息(到货确认需要)
//	 * @param map
//	 * @return
//	 */
//	public List<Map<String, Object>> selectLoadingInfo(Map<String, Object> map){
//		String Sql = "select l.uuid,l.batch_number,l.sort,l.loading_point,p.point_name loading_point_name,"
//				+ "l.loading_time,l.plate_number,"
//				+ "l.driver_name,l.driver_phone,l.pay_the_freight,l.pay_the_oil_card,l.pay_back_freight,"
//				+ "l.monthly_freight,l.destination1,l.destination1_freight,l.destination2,l.destination2_freight,"
//				+ "l.destination3,l.destination3_freight,l.arrive_status from t_loading_batches l "
//				+ "left join t_logistics_point p on p.uuid = l.loading_point "
//				+ "where 1=1 ";
//		return namedParameterJdbcTemplate.queryForList(Sql, map);
//	}


}
