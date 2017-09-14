package com.yydscm.Controller;

import com.yydscm.Service.IndexService;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by chenzhaopeng on 2017/7/20.
 */
@RestController
public class IndexController {
    @Autowired
    IndexService indexService;

    @GetMapping("/GoodsReceipt")
    public Result GoodsReceipt(HttpServletRequest request) {
        return indexService.getGoodsReceipt(request);
    }
}
