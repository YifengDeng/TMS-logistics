package com.yydscm.Controller.report;

import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.report.ReportService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 报表
 *
 * @author Administrator
 */
@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 营业报表，按按月查询或查询当天记录
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("selectReportList")
    public Result<?> selectReportList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //根据登录id查询运单列表
        List<Map<String, Object>> resultList = reportService.selectReportList(map, user);

        return ResultUtil.success(resultList);
    }


    /**
     * 查询某一天的开票记录
     *
     * @param map
     * @param request
     * @return
     */
    @PostMapping("selectReportListByDay")
    public Result<?> selectReportListByDay(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        //根据登录id查询运单列表
        List<Map<String, Object>> resultList = reportService.selectReportListByDay(map, user);

        return ResultUtil.success(resultList);
    }

}
