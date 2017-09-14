package com.yydscm.Dao.User;

import com.yydscm.Util.HsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class MemberDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //查询会员基本信息
    public List<Map<String,Object>> selectMemberList(Map<String, Object> map) {

        String sql = "select uuid,company_name,company_addr,company_phone,company_owner,company_owner_phone_o,date_format(create_time,'%Y-%m-%d %H:%i') create_time " +
                "from t_logistics  where company_status = 1 ";
        if (Objects.nonNull(HsUtil.noAttribute("startTime", map)) && !"".equals(HsUtil.noAttribute("startTime", map)) && !"null".equals(HsUtil.noAttribute("startTime", map))) {
            sql += "and DATE_FORMAT(create_time,'%Y-%m-%d') >= :startTime ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("endTime", map)) && !"".equals(HsUtil.noAttribute("endTime", map)) && !"null".equals(HsUtil.noAttribute("endTime", map))) {
            sql += "and DATE_FORMAT(create_time,'%Y-%m-%d') <= :endTime ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("company_name", map)) && !"".equals(HsUtil.noAttribute("company_name", map))) {
            sql += "and company_name like '%" + HsUtil.noAttribute("company_name", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("company_owner", map)) && !"".equals(HsUtil.noAttribute("company_owner", map))) {
            sql += "and company_owner like '%" + HsUtil.noAttribute("company_owner", map) + "%' ";
        }
        if (Objects.nonNull(HsUtil.noAttribute("company_owner_phone_o", map)) && !"".equals(HsUtil.noAttribute("company_owner_phone_o", map))) {
            sql += "and company_owner_phone_o like '%" + HsUtil.noAttribute("company_owner_phone_o", map) + "%' ";
        }
        sql += "order by create_time desc";

        return namedParameterJdbcTemplate.queryForList(sql, map);
    }
}
