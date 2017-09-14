package com.yydscm.Service.User;

import com.yydscm.Dao.User.MemberDao;
import com.yydscm.Util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberService {

    @Autowired
    private MemberDao MemberDao;



    //查询会员基本信息
    public Map<String,Object> selectMemberList(Map<String, Object> map) {

        List<Map<String, Object>> clist = MemberDao.selectMemberList(map);

        int pagesize = Integer.parseInt((String) map.get("pageSize"));
        int page = Integer.parseInt((String) map.get("page"));

        return PageUtil.pageMethod(pagesize, page, clist);
    }
}
