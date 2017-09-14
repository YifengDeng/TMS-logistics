package com.yydscm.Service.pointManager;

import com.google.common.collect.Maps;
import com.yydscm.Dao.declaraction.DeclaractionDao;
import com.yydscm.Dao.pointManager.PointManagerDao;
import com.yydscm.Util.MD5;
import com.yydscm.Util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PointManagerService {

    @Autowired
    private PointManagerDao pointManagerDao;
    @Autowired
    private DeclaractionDao declaractionDao;


    //获取登录用户公司id
    public Map<String, Object> getCompanyId() {
        //获取登录用户id
        Long userId = 847L;
        //查询用户所属公司id
        Map<String, Object> mapTow = Maps.newHashMap();
        mapTow.put("uuid", userId);
        Map<String, Object> resultMap = declaractionDao.selectCompanyId(mapTow);

        return resultMap;
    }

    //添加网点
    public int addPoint(Map<String, Object> map, Map<String, Object> user) {

//		Map<String, Object> resultMap = this.getCompanyId();

        Map<String, Object> sign = Maps.newHashMap();
        sign.put("logistics_uuid", user.get("logistics_uuid"));

        //查询公司未被占用的符号
        sign = pointManagerDao.selectPointSign(sign);

        //拼装网点参数
        map.put("logistics_uuid", user.get("logistics_uuid"));
        map.put("point_status", 1);
        map.put("create_time", new Date());
        map.put("point_sign", sign.get("sign"));
        //添加网点
        int uuid = pointManagerDao.addPoint(map);

        Map<String, Object> userMap = Maps.newHashMap();
        userMap.put("company_uuid", uuid);
        userMap.put("company_type", 0);
        userMap.put("role_uuid", 2);
        userMap.put("user_name", map.get("point_owner"));
        userMap.put("phone_one", map.get("point_owner_phone_o"));
        userMap.put("pwd", MD5.md5("123456"));
        userMap.put("createtime", new Date());
        userMap.put("login_sum", 0);
        userMap.put("user_status", 1);
        userMap.put("recieve_msg", 1);

        //添加用户
        int row = pointManagerDao.addUser(userMap);

        return row;
    }

    //修改网点
    public int updatePoint(Map<String, Object> map) {
        return pointManagerDao.updatePoint(map);
    }

    //查询网点详情
    public Map<String, Object> selectPoint(Map<String, Object> map) {
        return pointManagerDao.selectPoint(map);
    }

    //添加网点人员
    public int addPointUser(Map<String, Object> map) {

        map.put("company_type", 0);
        map.put("role_uuid", 2);
        map.put("pwd", MD5.md5("123456"));
        map.put("createtime", new Date());
        map.put("login_sum", 0);
        map.put("user_status", 1);
        map.put("recieve_msg", 1);

        return pointManagerDao.addPointUser(map);
    }

    //修改网点人员
    public int UpdatePointUser(Map<String, Object> map) {
        return pointManagerDao.UpdatePointUser(map);
    }

    //查询网点所有人员
    public List<Map<String, Object>> selectPointUser(Map<String, Object> map) {
        return pointManagerDao.selectPointUser(map);
    }

    //查询网点和网点人员
    public Map<String, Object> selectPointAndPointUser(Map<String, Object> map, Map<String, Object> user) {

//		Map<String, Object> resultMap = this.getCompanyId();  
        map.put("logistics_uuid", user.get("logistics_uuid"));

        List<Map<String, Object>> clist = pointManagerDao.selectPointAndPointUser(map);

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, clist);
    }

    //删除网点人员
    public int deletePointUser(Map<String, Object> map) {
        return pointManagerDao.deletePointUser(map);
    }

    //检测手机号是否重复
    public int checkPhone(Map<String, Object> map) {
        return pointManagerDao.checkPhone(map);
    }

}
