package com.yydscm.Service.declaraction;

import com.google.common.collect.Maps;
import com.yydscm.Dao.declaraction.DeclaractionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DeclaractionService {

    @Autowired
    private DeclaractionDao declaractionDao;


    //添加声明
    public int addDeclaraction(Map<String, Object> map, Map<String, Object> user) {

//		//获取登录用户id
//		Long userId = 847L;
//		//查询用户所属公司id
//		Map<String, Object> mapTow = Maps.newHashMap();
//		mapTow.put("uuid", userId);
//		Map<String, Object> resultMap = declaractionDao.selectCompanyId(mapTow);

        //拼装参数
        map.put("logistics_uuid", user.get("logistics_uuid"));
        map.put("create_time", new Date());

        return declaractionDao.addDeclaraction(map);
    }

    //修改声明
    public int updateDeclaraction(Map<String, Object> map) {
        map.put("update_time", new Date());
        return declaractionDao.updateDeclaraction(map);
    }


    //删除声明
    public int deleteDeclaraction(Map<String, Object> map) {
        return declaractionDao.deleteDeclaraction(map);
    }

    //查询声明列表
    public List<Map<String, Object>> selectDeclaractionList(Map<String, Object> user) {
//		//获取登录用户id
//		Long userId = 847L;
//		//查询用户所属公司id
//		Map<String, Object> mapTow = Maps.newHashMap();
//		mapTow.put("uuid", userId);
//		Map<String, Object> resultMap = declaractionDao.selectCompanyId(mapTow);

        Map<String, Object> map = Maps.newHashMap();
        map.put("logistics_uuid", user.get("logistics_uuid"));

        return declaractionDao.selectDeclaractionList(map);
    }


}
