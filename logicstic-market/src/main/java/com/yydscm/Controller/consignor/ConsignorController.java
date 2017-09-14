package com.yydscm.Controller.consignor;


import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.consignor.ConsignorService;
import com.yydscm.Util.HsUtil;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * 货主基本信息
 */
@RestController
@RequestMapping("consignor")
public class ConsignorController {

    @Autowired
    ConsignorService consignorService;
    @Autowired
    RedisUtil redisUtil;


    /**
     *查询所有货主信息
     *
     * @param map
     * @return
     */
    @PostMapping("selectConsignorList")
    public Result<?> selectConsignorList(@RequestParam Map<String, Object> map, HttpServletRequest request) {

        //判断空值
        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
//        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        Map<String, Object> consignorList = consignorService.selectConsignorList(map,null);

        return ResultUtil.success(consignorList);

    }


    /**
     * 添加或修改货主信息
     *
     * @param map
     * @return
     */
    @PostMapping("addConsignor")
    public Result<?> addConsignor(@RequestParam Map<String,Object> map){

        //判断空值
        if(Objects.isNull(map) || map.entrySet().isEmpty()){
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(),ResultEnum.FAIL_PARAMS.getMsg());
        }

        //用户登录数据
        //Map<String,Object> user = redisUtil.getUser((String) request.getAttribute("userid"));

        int row = 0;
        //添加与修改判断
        if(Objects.isNull(map.get("uuid"))){
            row = consignorService.addConsignor(map ,null);
        }else{
            row = consignorService.updateConsignor(map);
        }

        if(row==1){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(ResultEnum.FAIL.getCode(),ResultEnum.FAIL.getMsg());
        }
    }


    /**
     * 删除货主
     *
     * @param map
     * @return
     */
    @PostMapping("deleteConsignor")
    public Result<?> deleteConsignor(@RequestParam Map<String,Object> map){

        if (Objects.isNull(map) || map.entrySet().isEmpty()) {
            return ResultUtil.error(ResultEnum.FAIL_PARAMS.getCode(), ResultEnum.FAIL_PARAMS.getMsg());
        }

        map = Objects.isNull(HsUtil.noAttribute("map", map)) ? map : HsUtil.toHashMap(map.get("map"));

        int row = consignorService.deleteConsignor(map);

        if (row == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
        }
    }

}
