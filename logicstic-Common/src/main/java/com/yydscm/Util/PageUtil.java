package com.yydscm.Util;

import com.google.common.collect.Maps;
import com.yydscm.view.PageBean;

import java.util.List;
import java.util.Map;

/**
 * 分页工具类
 *
 * @author Administrator
 */
public class PageUtil {


    /**
     * @param pageSize 条数
     * @param page     页数
     * @param clist    查询结果集
     * @return
     */
    public static Map<String, Object> pageMethod(int pageSize, int page, List<?> clist) {

        Map<String, Object> map = Maps.newHashMap();

        PageBean pagebean = new PageBean(clist.size(), pageSize);//初始化PageBean对象
        //设置当前页
        pagebean.setCurPage(page); //这里page是从页面上获取的一个参数，代表页数
        //获得分页大小
        int pagesize = pagebean.getPageSize();
        //获得分页数据在list集合中的索引
        int firstIndex = (page - 1) * pagesize;
        int toIndex = page * pagesize;
        if (toIndex > clist.size()) {
            toIndex = clist.size();
        }
        if (firstIndex > toIndex) {
            firstIndex = 0;
            pagebean.setCurPage(1);
        }
        //截取数据集合，获得分页数据
        List<?> pageList = clist.subList(firstIndex, toIndex);
        map.put("pageList", pageList);
        map.put("pagebean", pagebean);

        return map;
    }


}
