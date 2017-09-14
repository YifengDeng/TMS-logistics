package com.yydscm.Service.driverManager;

import com.google.common.collect.Maps;
import com.yydscm.Dao.declaraction.DeclaractionDao;
import com.yydscm.Dao.driverManager.DriverManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DriverManagerService {

    @Autowired
    private DriverManagerDao driverManagerDao;
    @Autowired
    private DeclaractionDao declaractionDao;


    //添加车辆信息
    public int addDriver(Map<String, Object> map, Map<String, Object> user) {
        //获取登录用户id
//		Long userId = 847L;
        //查询用户所属公司id和名称
        Map<String, Object> mapTow = Maps.newHashMap();
        mapTow.put("uuid", user.get("uuid"));
        Map<String, Object> resultMap = driverManagerDao.selectCompanyIdAndName(mapTow);

        //拼装参数
        map.put("company_uuid", resultMap.get("uuid"));
        map.put("company_name", resultMap.get("company_name"));
        map.put("create_time", new Date());

        return driverManagerDao.addDriver(map);
    }

    //修改车辆信息
    public int updateDriver(Map<String, Object> map) {
        map.put("update_time", new Date());
        return driverManagerDao.updateDriver(map);
    }

    //删除车辆信息
    public int deleteDriver(Map<String, Object> map) {
        return driverManagerDao.deleteDriver(map);
    }

    //查询车辆信息
    public List<Map<String, Object>> selectDriverList(Map<String, Object> user) {
//		//获取登录用户id
//		Long userId = 847L;
//		//查询用户所属公司id
//		Map<String, Object> mapTow = Maps.newHashMap();
//		mapTow.put("uuid", userId);
//		Map<String, Object> resultMap = declaractionDao.selectCompanyId(mapTow);

        //拼装参数
        Map<String, Object> map = Maps.newHashMap();
        map.put("company_uuid", user.get("logistics_uuid"));

        return driverManagerDao.selectDriverList(map);
    }

}
