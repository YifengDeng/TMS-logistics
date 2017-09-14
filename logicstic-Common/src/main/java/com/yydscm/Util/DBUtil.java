package com.yydscm.Util;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yydscm.Exception.UpdateException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by chenzhaopeng on 2017/6/1.
 */
public class DBUtil {

    /**
     * 动态生成更新sql
     *
     * @param tablename       表名
     * @param params          需要更新的字段
     * @param conditionColumn 需要添加条件的字段
     * @return
     */
    public static int excuteUpdate(String tablename, Map<String, Object> params, String[] conditionColumn, NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws UpdateException {
        if (Strings.isNullOrEmpty(tablename)) {
            throw new UpdateException("表名不能为空");
        }
        if (Objects.isNull(params)) {
            throw new UpdateException("参数不能为空");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update ");
        stringBuilder.append(tablename);
        stringBuilder.append(" set ");
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            stringBuilder.append(key);
            stringBuilder.append("=:");
            stringBuilder.append(key);
            if (iterator.hasNext()) {
                stringBuilder.append(",");
            }
        }
        if (Objects.nonNull(conditionColumn) && conditionColumn.length > 0) {
            stringBuilder.append(" where 1=1 ");
            for (String col : conditionColumn) {
                if (Objects.isNull(params.get(col))) {
                    throw new UpdateException("传入的参数中没有" + col + "这个字段");
                }
                stringBuilder.append("and ");
                stringBuilder.append(col);
                stringBuilder.append("=:");
                stringBuilder.append(col);
            }
        }
        return namedParameterJdbcTemplate.update(stringBuilder.toString(), params);
    }

    public static int excuteUpdate(String tablename, Object object, String[] conditionColumn, NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws UpdateException {
        if (Strings.isNullOrEmpty(tablename)) {
            throw new UpdateException("表名不能为空");
        }
        if (Objects.isNull(object)) {
            throw new UpdateException("参数不能为空");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update ");
        stringBuilder.append(tablename);
        stringBuilder.append(" set ");
        Class cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            try {
                fields[i].setAccessible(true);
                if (Objects.nonNull(fields[i].get(object))) {
                    String FieldName = fields[i].getName();
                    stringBuilder.append(FieldName);
                    stringBuilder.append("=:");
                    stringBuilder.append(FieldName);
                    if (i != len - 1) {
                        stringBuilder.append(",");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (Objects.nonNull(conditionColumn) && conditionColumn.length > 0) {
            stringBuilder.append(" where 1=1 ");
            for (String col : conditionColumn) {
                stringBuilder.append("and ");
                stringBuilder.append(col);
                stringBuilder.append("=:");
                stringBuilder.append(col);
            }
        }
        return namedParameterJdbcTemplate.update(stringBuilder.toString(), new BeanPropertySqlParameterSource(object));
    }

    /**
     * 过滤参数
     *
     * @param cls 表对应的实体类的class
     * @param map 传入的map
     * @return
     */
    public static Map<String, Object> getUpdateData(Class cls, Map<String, Object> map) {
        Map<String, Object> update = Maps.newHashMap();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String FieldName = fields[i].getName();
            Object object = map.get(FieldName);
            if (Objects.nonNull(object)) {
                update.put(FieldName, object);
            }
        }
        return update;
    }

    public static String[] getUsingColumn(Map<String,Object> param){
        List<String> list = Lists.newArrayList();
        param.forEach((s, o) -> {
            list.add(s);
        });
        String[] columns = list.toArray(new String[list.size()]);
        return columns;
    }


}
