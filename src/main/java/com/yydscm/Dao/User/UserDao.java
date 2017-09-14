package com.yydscm.Dao.User;

import com.google.common.base.Strings;
import com.yydscm.Util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by chenzhaopeng on 2017/6/5.
 */
@Repository
public class UserDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public Map<String, Object> selectUserByUserName(Map<String, Object> param) throws EmptyResultDataAccessException {
        String sql = "SELECT u.*,l.logistics_uuid,l.point_name,lo.company_name FROM t_user u " +
                "LEFT JOIN t_logistics_point l on u.company_uuid=l.uuid " +
                "LEFT JOIN t_logistics lo ON lo.uuid=l.logistics_uuid";
        String username = (String) param.get("username");
        if (Objects.nonNull(username) && !Strings.isNullOrEmpty(username)) {
            sql += " WHERE phone_one=:phone_one";
        }
        param.put("phone_one", username);
        Map<String, Object> user = namedParameterJdbcTemplate.queryForMap(sql, param);
        return user;
    }

    public List<Map<String, Object>> selectMenuByMenuId(Map<String, Object> param) {
        String sql = "SELECT * FROM t_menu WHERE 1=1";
        if (Objects.nonNull(param.get("menuid"))) {
            sql += " AND uuid IN (:menuid)";
        }
        sql += " ORDER BY seq_no";
        logger.info(sql);
        return namedParameterJdbcTemplate.queryForList(sql, param);
    }

    /**
     * 绑定用户常用打印机信息
     *
     * @param map
     * @return
     */
    public int updateUserForPrinter(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_user", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }

    /**
     * 获取用户绑定的打印机信息
     *
     * @param map
     * @return
     */
    public Map<String, Object> selectPrinterById(Map<String, Object> map) {
        String Sql = "select TP,stylus,A4 from t_user where uuid = :uuid";
        return namedParameterJdbcTemplate.queryForMap(Sql, map);
    }

    public Map<String, Object> selectUserById(String userId) {
        String sql = "SELECT * FROM t_user WHERE uuid=:uuid";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("uuid", userId);
        return namedParameterJdbcTemplate.queryForMap(sql, sqlParameterSource);
    }

    public int updateUser(Map<String, Object> map) {
        return DBUtil.excuteUpdate("t_user", map, new String[]{"uuid"}, namedParameterJdbcTemplate);
    }
}
