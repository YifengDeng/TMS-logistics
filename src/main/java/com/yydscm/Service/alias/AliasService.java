package com.yydscm.Service.alias;

import com.google.common.collect.Maps;
import com.yydscm.Dao.alias.AliasDao;
import com.yydscm.Dao.declaraction.DeclaractionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AliasService {

    @Autowired
    private AliasDao aliasDao;
    @Autowired
    private DeclaractionDao declaractionDao;


    //添加别名
    public int addAlias(Map<String, Object> map, Map<String, Object> user) {
//		//获取登录用户id
//		Long userId = 847L;
//		//查询用户所属公司id
//		Map<String, Object> mapTow = Maps.newHashMap();
//		mapTow.put("uuid", userId);
//		Map<String, Object> resultMap = declaractionDao.selectCompanyId(mapTow);

        //拼装参数
        map.put("logistics_uuid", user.get("logistics_uuid"));
        map.put("create_time", new Date());

        return aliasDao.addAlias(map);
    }

    //修改别名
    public int updateAlias(Map<String, Object> map) {
        map.put("update_time", new Date());
        return aliasDao.updateAlias(map);
    }

    //删除别名
    public int deleteAlias(Map<String, Object> map) {
        return aliasDao.deleteAlias(map);
    }

    //查询别名列表
    public List<Map<String, Object>> selectAliasList(Map<String, Object> user) {
//		//获取登录用户id
//		Long userId = 847L;
//		//查询用户所属公司id
//		Map<String, Object> mapTow = Maps.newHashMap();
//		mapTow.put("uuid", userId);
//		Map<String, Object> resultMap = declaractionDao.selectCompanyId(mapTow);

        Map<String, Object> map = Maps.newHashMap();
        map.put("logistics_uuid", user.get("logistics_uuid"));

        return aliasDao.selectAliasList(map);
    }

}
