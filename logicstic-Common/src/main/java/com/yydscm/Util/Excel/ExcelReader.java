package com.yydscm.Util.Excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Excel读取抽象
 *
 * @author Archx
 * @date 2015/7/2 0002
 */
public abstract class ExcelReader<E> {

    /**
     * 工作簿
     */
    protected final Workbook workbook;

    /**
     * 属性数组
     */
    protected final String[] fields;

    /**
     * 工作表索引
     */
    protected final int sheetIndex;

    /**
     * 行索引
     */
    protected final int rowIndex;

    /**
     * 列索引
     */
    protected final int cellIndex;

    /**
     * 构造函数
     *
     * @param workbook   工作簿
     * @param fields     属性数组
     * @param sheetIndex 工作表索引
     * @param rowIndex   行索引
     * @param cellIndex  列索引
     */
    public ExcelReader(Workbook workbook, String[] fields, int sheetIndex, int rowIndex, int cellIndex) {
        this.workbook = workbook;
        this.fields = fields;
        this.sheetIndex = sheetIndex;
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
    }

    /**
     * 转换成对象集合
     *
     * @param clazz 对象类型
     * @return 集合
     */
    public List<E> parse(Class<E> clazz) {
        List<E> list = new ArrayList<E>();
        Sheet sheet = workbook.getSheetAt(sheetIndex);

        Iterator<Row> rt = sheet.iterator();
        int len = fields.length;
        while (rt.hasNext()) {
            Row row = rt.next();
            if (row.getRowNum() < rowIndex)
                continue;
            E e = BeanUtils.instantiate(clazz);
            for (Cell cell : row) {
                int cid = cell.getColumnIndex();
                // 从起始单元格开始查找
                if (cid < cellIndex)
                    continue;
                // 所需字段查找完成结束
                if (cid - cellIndex + 1 > len)
                    break;
                // 以字符串方式读取
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String _value = cell.getStringCellValue();
                // 空值跳过
                if (StringUtils.isEmpty(_value))
                    continue;
                Field field = ReflectionUtils.findField(clazz, fields[cid - cellIndex]);
                // 无匹配字段跳过
                if (field == null)
                    continue;
                // 处理值
                Object value = render(field, _value);
                // 设置值
                ConversionService cs = new DefaultConversionService();
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, e, cs.convert(value, field.getType()));
            }
            list.add(handle(e));
        }
        return list;
    }

    /**
     * 渲染方法
     *
     * @param field 字段
     * @param value 值
     * @return 最终值
     */
    protected abstract Object render(Field field, Object value);

    /**
     * 实体处理
     *
     * @param e 实体对象
     * @return 实体对象
     */
    protected abstract E handle(E e);
}
