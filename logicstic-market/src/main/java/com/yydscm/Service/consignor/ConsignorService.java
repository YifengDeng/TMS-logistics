package com.yydscm.Service.consignor;

import com.yydscm.Dao.consignor.ConsignorDao;
import com.yydscm.Util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ConsignorService {

    @Autowired
    private ConsignorDao consignorDao;


    //查询所有货主
    public Map<String, Object> selectConsignorList(Map<String, Object> map,Map<String, Object> user){

        //放入用户id
        //map.put("user_uuid", user.get("user_uuid"));

        List<Map<String,Object>> consignorList=null;
        consignorList = consignorDao.selectConsignorList(map);

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, consignorList);
    }


    //添加货主
    @Transactional
    public int addConsignor(Map<String, Object> map, Map<String, Object> user){

        //拼装参数
        map.put("createtime", new Date());
        map.put("status", 1);

     return consignorDao.addConsignor(map);
    }


    //删除货主
    @Transactional
    public int deleteConsignor(Map<String,Object> map){

        //拼装参数
        map.put("status",0);

        return consignorDao.updateConsignor(map);
    }


    //修改货主
    @Transactional
    public int updateConsignor(Map<String,Object> map){
        return consignorDao.updateConsignor(map);
    }

}
