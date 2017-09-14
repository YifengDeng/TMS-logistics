package com.yydscm.Service.partners;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.yydscm.Dao.declaraction.DeclaractionDao;
import com.yydscm.Dao.partners.PartnersDao;
import com.yydscm.Util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PartnersService {


    @Autowired
    private PartnersDao partnersDao;
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


    //搜索
    public Map<String, Object> selectCompanyList(Map<String, Object> map, Map<String, Object> user) {

//		Map<String, Object> resultMap = this.getCompanyId();

        //查询不包含自己的公司
        map.put("uuid", user.get("logistics_uuid"));

        List<Map<String, Object>> clist = partnersDao.selectCompanyList(map);

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, clist);
    }


    //搜索子网点
    public Map<String, Object> selectPointList(Map<String, Object> map, Map<String, Object> user) {

//		Map<String, Object> resultMap = this.getCompanyId();

        map.put("company_uuid", user.get("logistics_uuid"));

        List<Map<String, Object>> clist = partnersDao.selectPointList(map);

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, clist);
    }


    //添加合作伙伴
    public int[] addPartners(String map, Map<String, Object> user) {

//		Map<String, Object> resultMap = this.getCompanyId();

        List<Map<String, Object>> listObjectFir = (List<Map<String, Object>>) JSONArray.parse(map);
        Map[] maps = new Map[listObjectFir.size()];

        for (int i = 0; i < listObjectFir.size(); i++) {
            Map<String, Object> map2 = listObjectFir.get(i);
            map2.put("company_uuid", user.get("logistics_uuid"));
            map2.put("partner_status", 1);
            maps[i] = map2;
        }
        return partnersDao.addPartners(maps);
    }


    //查询合作伙伴列表
    public Map<String, Object> selectPartnersList(Map<String, Object> map, Map<String, Object> user) {

//		Map<String, Object> resultMap = this.getCompanyId();
        map.put("company_uuid", user.get("logistics_uuid"));

        List<Map<String, Object>> clist = partnersDao.selectPartnersList(map);

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, clist);
    }


    //禁用或者启用合作伙伴
    public int updatePartnersStatus(Map<String, Object> map) {
        return partnersDao.updatePartnersStatus(map);
    }

}
