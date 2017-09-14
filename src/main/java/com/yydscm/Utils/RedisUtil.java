package com.yydscm.Utils;

import com.yydscm.Enum.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by chenzhaopeng on 2017/6/20.
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 写入hashmap到缓存
     */
    public boolean put(String key, Map map) {
        boolean result = false;
        redisTemplate.opsForHash().putAll(key, map);
        result = true;
        return result;
    }

    /**
     * 读取hashmap
     *
     * @param key     存入redis时的key
     * @param hashKey Map中的key
     */
    public Object get(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }


    /**
     * 获取用户信息
     */

    public Map<String, Object> getUser(String uuid) {
        return (Map<String, Object>) get(uuid, Const.USER.name());
    }
}
