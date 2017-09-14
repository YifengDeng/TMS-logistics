package com.yydscm.Controller.packing;

import com.yydscm.Service.packing.PackService;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 包装
 *
 * @author Administrator
 */
@RestController
@RequestMapping("pack")
public class PackController {

    @Autowired
    private PackService packService;


    /**
     * 查询所有的包装
     *
     * @return
     */
    @PostMapping("selectPackAll")
    public Result selectPackAll() {

        List<Map<String, Object>> map = packService.selectPackAll();

        return ResultUtil.success(map);
    }

}
