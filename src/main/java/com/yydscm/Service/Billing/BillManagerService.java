package com.yydscm.Service.Billing;

import com.google.common.collect.Maps;
import com.yydscm.Dao.Billing.BillManagerDao;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.PageUtil;
import com.yydscm.Util.PhoneFormatCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BillManagerService {

    @Autowired
    private BillManagerDao billManagerDao;


    //获取登录用户公司id
    public Map<String, Object> getCompanyId() {
        //获取用户登录id
        Long userId = 847L;

        //查询登录人员角色和我网点id
        Map<String, Object> mapTow = Maps.newHashMap();
        mapTow.put("uuid", userId);
        Map<String, Object> roleMap = billManagerDao.selectUserRole(mapTow);
        return roleMap;
    }


    //根据登录id查询运单列表
    public Map<String, Object> selectBillList(Map<String, Object> map, Map<String, Object> user) {

        //如果用户是老板，则查询公司所有，如果是网点人员，则查询当前网点.   角色：1：老板，2：站点人员

        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consigner"))) {
                map.put("consigner_phone", map.get("consigner"));
                map.put("consigner", "");
            }
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee", map)) && !"".equals(HsUtil.noAttribute("consignee", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consignee"))) {
                map.put("consignee_phone", map.get("consignee"));
                map.put("consignee", "");
            }
        }


        List<Map<String, Object>> clist = null;
        int roleUuid = ((Integer) user.get("role_uuid")).intValue();

        map.put("company_uuid", user.get("company_uuid"));
        if (roleUuid == 1) {
            clist = billManagerDao.selectAllPointBillList(map);
        } else {
            clist = billManagerDao.selectPointBillList(map);
        }

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, clist);
    }


    //运单作废
    public int updateBillStatus(Long uuid) {

        Map<String, Object> map = Maps.newHashMap();
        map.put("uuid", uuid);
        map.put("invoice_status", 9);

        return billManagerDao.updateBillStatus(map);
    }


    //根据登录id查询相关运单列表
    public Map<String, Object> selectBillSuperList(Map<String, Object> map, Map<String, Object> user) {

        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consigner"))) {
                map.put("consigner_phone", map.get("consigner"));
                map.put("consigner", "");
            }
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee", map)) && !"".equals(HsUtil.noAttribute("consignee", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consignee"))) {
                map.put("consignee_phone", map.get("consignee"));
                map.put("consignee", "");
            }
        }

        //如果用户是老板，则查询公司所有，如果是网点人员，则查询当前网点.   角色：1：老板，2：站点人员
        List<Map<String, Object>> clist = null;
        int roleUuid = ((Integer) user.get("role_uuid")).intValue();
        map.put("company_uuid", user.get("company_uuid"));

        if (roleUuid == 1) {
            clist = billManagerDao.selectAllBillSuperList(map);
        } else {
            clist = billManagerDao.selectBillSuperList(map);
        }

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, clist);
    }

    //查询发站网点
    public List<Map<String, Object>> selectInitialStation(Map<String, Object> user) {

//		Map<String, Object> roleMap = this.getCompanyId();

        //如果用户是老板，则查询公司所有，如果是网点人员，则查询当前网点.   角色：1：老板，2：站点人员
        List<Map<String, Object>> clist = null;
        int roleUuid = ((Integer) user.get("role_uuid")).intValue();

        Map<String, Object> map = Maps.newHashMap();
        map.put("company_uuid", user.get("company_uuid"));

        if (roleUuid == 1) {
            clist = billManagerDao.selectAllInitialStation(map);
        } else {
            clist = billManagerDao.selectInitialStation(map);
        }

        return clist;
    }


    //运单费用计算
    public Map<String, Object> selectCost(Map<String, Object> map, Map<String, Object> user) {

        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consigner"))) {
                map.put("consigner_phone", map.get("consigner"));
                map.put("consigner", "");
            }
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee", map)) && !"".equals(HsUtil.noAttribute("consignee", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consignee"))) {
                map.put("consignee_phone", map.get("consignee"));
                map.put("consignee", "");
            }
        }

        //如果用户是老板，则查询公司所有，如果是网点人员，则查询当前网点.   角色：1：老板，2：站点人员
        Map<String, Object> clist = null;
        int roleUuid = ((Integer) user.get("role_uuid")).intValue();

        map.put("company_uuid", user.get("company_uuid"));
        if (roleUuid == 1) {
            clist = billManagerDao.selectAllCost(map);
        } else {
            clist = billManagerDao.selectCost(map);
        }

        return clist;
    }


    //运单超级查询费用计算
    public Map<String, Object> selectSuperCost(Map<String, Object> map, Map<String, Object> user) {

        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consigner"))) {
                map.put("consigner_phone", map.get("consigner"));
                map.put("consigner", "");
            }
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee", map)) && !"".equals(HsUtil.noAttribute("consignee", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consignee"))) {
                map.put("consignee_phone", map.get("consignee"));
                map.put("consignee", "");
            }
        }

        //如果用户是老板，则查询公司所有，如果是网点人员，则查询当前网点.   角色：1：老板，2：站点人员
        Map<String, Object> clist = null;
        int roleUuid = ((Integer) user.get("role_uuid")).intValue();
        map.put("company_uuid", user.get("company_uuid"));

        if (roleUuid == 1) {
            clist = billManagerDao.selectAllSuperCost(map);
        } else {
            clist = billManagerDao.selectSuperCost(map);
        }

        return clist;
    }


    //导出运单历史报表
    public List<Map<String, Object>> importBillExcel(Map<String, Object> map, Map<String, Object> user) {
//		Map<String, Object> roleMap = this.getCompanyId();

        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consigner"))) {
                map.put("consigner_phone", map.get("consigner"));
                map.put("consigner", "");
            }
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee", map)) && !"".equals(HsUtil.noAttribute("consignee", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consignee"))) {
                map.put("consignee_phone", map.get("consignee"));
                map.put("consignee", "");
            }
        }
        //如果用户是老板，则查询公司所有，如果是网点人员，则查询当前网点.   角色：1：老板，2：站点人员
        List<Map<String, Object>> clist = null;
        int roleUuid = ((Integer) user.get("role_uuid")).intValue();

        map.put("company_uuid", user.get("company_uuid"));
        if (roleUuid == 1) {
            clist = billManagerDao.selectAllPointBillList(map);
        } else {
            clist = billManagerDao.selectPointBillList(map);
        }

        return clist;
    }


    //查询当天的费用
    public Map<String, Object> selectCostForDay(Map<String, Object> map, Map<String, Object> user) {
        map.put("company_uuid", user.get("company_uuid"));
        return billManagerDao.selectCostForDay(map);
    }


    //查询到站网点
    public List<Map<String, Object>> selectTerminalStation(Map<String, Object> user) {
        //如果用户是老板，则查询公司所有，如果是网点人员，则查询当前网点.   角色：1：老板，2：站点人员
        List<Map<String, Object>> clist = null;
        int roleUuid = ((Integer) user.get("role_uuid")).intValue();

        Map<String, Object> map = Maps.newHashMap();
        map.put("uuid", user.get("company_uuid"));
        map.put("company_uuid", user.get("logistics_uuid"));

        if (roleUuid == 1) {
            clist = billManagerDao.selectAllTerminalStation(map);
        } else {
            clist = billManagerDao.selectTerminalStation(map);
        }

        return clist;
    }


    //运单查询中的到站和发站网点查询
    public List<Map<String, Object>> selectAllTerminalStation(Map<String, Object> user) {

        Map<String, Object> map = Maps.newHashMap();
        map.put("uuid", user.get("company_uuid"));
        map.put("company_uuid", user.get("logistics_uuid"));

        return billManagerDao.selectAllTerminalStation(map);
    }


    //根据登录id查询相关运单列表
    public List<Map<String, Object>> exportSuperBillExcel(Map<String, Object> map, Map<String, Object> user) {
        if (Objects.nonNull(HsUtil.noAttribute("consigner", map)) && !"".equals(HsUtil.noAttribute("consigner", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consigner"))) {
                map.put("consigner_phone", map.get("consigner"));
                map.put("consigner", "");
            }
        }
        if (Objects.nonNull(HsUtil.noAttribute("consignee", map)) && !"".equals(HsUtil.noAttribute("consignee", map))) {
            if (PhoneFormatCheckUtils.isPhoneLegal((String) map.get("consignee"))) {
                map.put("consignee_phone", map.get("consignee"));
                map.put("consignee", "");
            }
        }
        //如果用户是老板，则查询公司所有，如果是网点人员，则查询当前网点.   角色：1：老板，2：站点人员
        List<Map<String, Object>> clist = null;
        int roleUuid = ((Integer) user.get("role_uuid")).intValue();
        map.put("company_uuid", user.get("company_uuid"));

        if (roleUuid == 1) {
            clist = billManagerDao.selectAllBillSuperList(map);
        } else {
            clist = billManagerDao.selectBillSuperList(map);
        }

        return clist;
    }


}
