package com.yydscm.Controller;

import com.yydscm.Util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by chenzhaopeng on 2017/6/1.
 */
@RestController
public class LoginController {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @RequestMapping("/")
    public ModelAndView Index(HttpSession httpSession) {
        String Sql = "SELECT * FROM yyd_t_billing WHERE uuid IN (:uuid) AND line_uuid =:line_uuid";
        Map<String, Object> param = new HashMap<>();
        List uuids = Arrays.asList(376, 377, 378, 379);
        param.put("uuid", uuids);
        param.put("line_uuid", 5443);
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, param);
        Map<String, Object> data = new HashMap<>();
        data.put("data", list);
        return new ModelAndView("index", data);
    }


    @RequestMapping("user")
    public List<Map<String, Object>> User(HttpSession session) {
        String Sql = "SELECT * FROM yyd_t_billing WHERE uuid IN (:uuid) AND line_uuid =:line_uuid";
        Map<String, Object> param = new HashMap<>();
        List uuids = Arrays.asList(376, 377, 378, 379);
        param.put("uuid", uuids);
        param.put("line_uuid", 5443);
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, param);
        return list;
    }

    @RequestMapping("testupdate")
    @ResponseBody
    @Transactional
    public String testupdate() {
        Map<String, Object> param = new HashMap<>();
        param.put("id", 65);
        param.put("test_col", "test_update");
        param.put("test_datetime", new Date());
        try {
            return DBUtil.excuteUpdate("test_table", param, new String[]{"id"}, namedParameterJdbcTemplate) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "失败";
    }

    @PostMapping("test")
    public Object test() {
        try {
            Map<String, Object> param = new HashMap<>();
            String Sql = "select * from t_user";
            List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(Sql, param);
            if(list.size() > 0){
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "失败";
    }


}
