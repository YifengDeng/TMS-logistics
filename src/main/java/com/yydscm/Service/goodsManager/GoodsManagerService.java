package com.yydscm.Service.goodsManager;

import com.google.common.collect.Maps;
import com.yydscm.Dao.goodsManager.GoodsManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsManagerService {

    @Autowired
    private GoodsManagerDao goodsManagerDao;

    //添加货物
    public int addGoods(Map<String, Object> map, Map<String, Object> user) {

//		//获取登录用户id
//		Long userId = 847L;
//		
//		//查询用户所属网点id
//		Map<String, Object> mapTow = Maps.newHashMap();
//		mapTow.put("uuid", userId);
//		Map<String, Object> resultMap = goodsManagerDao.selectPointId(mapTow);

        //拼装参数
        map.put("point_uuid", user.get("company_uuid"));
        map.put("create_time", new Date());
//		map.put("goods_status", 1);

        return goodsManagerDao.addGoods(map);
    }

    //删除货物
    public int deleteGoods(Map<String, Object> map) {
        return goodsManagerDao.deleteGoods(map);
    }

    //查询所有货物
    public List<Map<String, Object>> selectGoodsList(Map<String, Object> user) {
//		//获取登录用户id
//		Long userId = 847L;
//		
//		//查询用户所属网点id
//		Map<String, Object> mapTow = Maps.newHashMap();
//		mapTow.put("uuid", userId);
//		Map<String, Object> resultMap = goodsManagerDao.selectPointId(mapTow);

        Map<String, Object> map = Maps.newHashMap();
        map.put("point_uuid", user.get("company_uuid"));

        return goodsManagerDao.selectGoodsList(map);
    }


}
