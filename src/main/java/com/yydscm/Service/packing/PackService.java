package com.yydscm.Service.packing;

import com.yydscm.Dao.packing.PackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PackService {

    @Autowired
    private PackDao packDao;

    //查询所有的包装
    public List<Map<String, Object>> selectPackAll() {
        return packDao.selectPackAll();
    }


}
