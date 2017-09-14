package com.yydscm.Service;

import com.google.common.collect.Maps;
import com.yydscm.Dao.IndexDao;
import com.yydscm.Util.ResultUtil;
import com.yydscm.Utils.RedisUtil;
import com.yydscm.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by chenzhaopeng on 2017/7/20.
 */
@Service
public class IndexService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    IndexDao indexDao;

    public Result getGoodsReceipt(HttpServletRequest request) {
        Map<String, Object> user = redisUtil.getUser((String) request.getAttribute("userid"));
        Long company_uuid = (Long) user.get("company_uuid");
        Map<String, Object> params = Maps.newHashMap();
        if (Objects.nonNull(company_uuid)) {
            LocalDate now = LocalDate.now();
            LocalDate first = now.minusDays(6);
            params.put("startdate", first);
            params.put("enddate", now.plusDays(1));
            if ("1".equals(user.get("role_uuid").toString())) {
                List<Map<String, Object>> childlist = indexDao.getChild(company_uuid);
                List child = childlist.stream().map(stringObjectMap -> stringObjectMap.get("uuid")).collect(toList());
                params.put("company_uuid", child);
            } else {
                params.put("company_uuid", company_uuid);
            }
            List<Map<String, Object>> list = indexDao.getGoodsReceipt(params);
            /**
             * 按照开票日期分组
             */
            Map map = list.stream().collect(groupingBy(o -> o.get("invoice_date")));
            /**
             * 按照开票日期排序
             */
            Map<String, Object> data = Maps.newTreeMap((o1, o2) -> {
                LocalDate d1 = LocalDate.parse(o1);
                LocalDate d2 = LocalDate.parse(o2);
                if (d1.isAfter(d2)) {
                    return 1;
                } else {
                    return d1.isEqual(d2) ? 0 : -1;
                }
            });
            /**
             * 遍历统计
             */
            map.forEach((o, o2) -> {
                List<Map<String, Object>> list1 = (List<Map<String, Object>>) o2;
                Map<String, Object> total = Maps.newHashMap();//新建total存放统计后的结果
                total.put("freight_total", Double.valueOf(0));//初始化
                total.put("collection_fee_total", Double.valueOf(0));
                /**
                 * 遍历list,统计后放进total中
                 */
                list1.forEach(stringObjectMap -> {
                    Double freight = (Double) stringObjectMap.get("freight");
                    Double collect = (Double) stringObjectMap.get("collection_fee");
                    Double freight_total = (Double) total.get("freight_total");
                    Double collection_fee_total = (Double) total.get("collection_fee_total");
                    freight_total += freight;
                    collection_fee_total += collect;
                    total.put("freight_total", freight_total);
                    total.put("collection_fee_total", collection_fee_total);
                    String invoice_date = (String) stringObjectMap.get("invoice_date");
                    invoice_date = invoice_date.substring(invoice_date.indexOf("-") + 1);
                    total.put("invoice_date", invoice_date);
                });
                /**
                 * 根据日期存入每个total
                 */
                data.put(o.toString(), total);
                /**
                 * 因为要查询一周的,所以如果某天没有开票就没有日期,导致返回的数据补全,因此这里补全日期
                 */
                for (int i = 0; i < 7; i++) {
                    String key = first.plusDays(i).toString();
                    if (!data.containsKey(key)) {
                        Map<String, Object> objectMap = Maps.newHashMap();
                        objectMap.put("freight_total", Double.valueOf(0));
                        objectMap.put("collection_fee_total", Double.valueOf(0));
                        objectMap.put("invoice_date", key.substring(key.indexOf("-") + 1));
                        data.put(key, objectMap);
                    }
                }

            });
            return ResultUtil.success(data);
        }
        return ResultUtil.success(null);
    }
}
