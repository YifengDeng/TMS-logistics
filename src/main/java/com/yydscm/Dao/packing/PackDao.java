package com.yydscm.Dao.packing;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PackDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    //查询所有的包装
    public List<Map<String, Object>> selectPackAll() {
        String Sql = "select packing_name from t_public_packing where packing_status = 1";
        Map<String, Object> param = Maps.newHashMap();
        return namedParameterJdbcTemplate.queryForList(Sql, param);
    }


}
