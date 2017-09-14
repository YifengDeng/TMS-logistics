package com.yydscm.Service.WxInterface;

import java.util.List;
import java.util.Map;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yydscm.Dao.WxInterface.WxDao;
import com.yydscm.Util.PageUtil;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WxService {

    @Autowired
    private WxDao wxDao;

    //查询诚信会员列表
    public Map<String, Object> selectCreditUserList(Map<String, Object> map) {

        List<Map<String, Object>> clist = wxDao.selectCreditUserList(map);

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, clist);
    }

    //获取用户基本信息
    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public Result CreditUserInfo(Map<String, Object> map) {
        Map<String,Object> userInfo = wxDao.selectCreditUserInfo(map);//获取用户基本信息
        if(userInfo != null){
            userInfo.put("company_addr",userInfo.get("point_addr"));
            userInfo.put("company_owner",userInfo.get("user_name"));
            userInfo.put("company_owner_phone_o",userInfo.get("phone_one"));
            userInfo.put("company_longitude",userInfo.get("point_longitude"));
            userInfo.put("company_latitude",userInfo.get("point_latitude"));
            userInfo.put("company_status",1);
            int logistics_uuid = wxDao.insertCreditCompany(userInfo);//添加公司信息

            userInfo.put("logistics_uuid",logistics_uuid);
            userInfo.put("point_status",1);
            int company_uuid = wxDao.insertCreditPoint(userInfo);//添加网点信息

            userInfo.put("company_uuid",company_uuid);
            userInfo.put("role_uuid",1);
            userInfo.put("user_status",1);
            userInfo.put("recieve_msg",1);
            userInfo.put("credit_statu",2);
            int row = wxDao.insertCreditUserInfo(userInfo);//添加用户信息

            if(row == 1){
                int count = wxDao.updateCreditStatu(map);
                if(count == 1){
                    return ResultUtil.success();
                }else{
                    //回滚
                    throw new IllegalArgumentException("数据回滚");
                }
            }else{
                return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
            }
        }else{
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }

    }

    //修改状态
    public Result<?> updateCreditStatu(Map<String, Object> map) {
        int row = wxDao.updateCreditStatu(map);
        if(row == 1){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }
    }
}
