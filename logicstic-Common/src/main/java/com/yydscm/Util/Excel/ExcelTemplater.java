package com.yydscm.Util.Excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengyuanke
 */
public class ExcelTemplater {

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param headEN  表格属性列名数组(属性)
     * @param headCN  表格属性列名数组(中文描述)
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                <p>
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */

    public void exportExcel(String title, String[] headEN, String[] headCN, List<?> dataset,

                            OutputStream out, String pattern) {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (dataset != null && dataset.size() > 0) {
            //让每个工作表，最多产生50000 的数据
            int size = 50000;
            int sum = (dataset.size() / size + 1);
            List<?> dataset1 = new ArrayList<>();
            int start = 0, end = 0;
            for (int z = 0; z < sum; z++) {
                start = size * z;
                end = size * (z + 1) > dataset.size() ? dataset.size() : size * (z + 1);
                dataset1 = dataset.subList(start, end);

                // 生成一个表格

                HSSFSheet sheet = workbook.createSheet(title + (z + 1));

                // 设置表格默认列宽度为15个字节

                sheet.setDefaultColumnWidth(15);

                // 生成一个样式

                HSSFCellStyle style = workbook.createCellStyle();

                // 设置这些样式

                style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

                style.setBorderRight(HSSFCellStyle.BORDER_THIN);

                style.setBorderTop(HSSFCellStyle.BORDER_THIN);

                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

                // 生成一个字体

                HSSFFont font = workbook.createFont();

                font.setColor(HSSFColor.VIOLET.index);

                font.setFontHeightInPoints((short) 12);

                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

                // 把字体应用到当前的样式

                style.setFont(font);

                // 生成并设置另一个样式

                HSSFCellStyle style2 = workbook.createCellStyle();

                style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);

                style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

                style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);

                style2.setBorderRight(HSSFCellStyle.BORDER_THIN);

                style2.setBorderTop(HSSFCellStyle.BORDER_THIN);

                style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

                style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

                // 生成另一个字体

                HSSFFont font2 = workbook.createFont();

                font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

                // 把字体应用到当前的样式

                style2.setFont(font2);

                // 声明一个画图的顶级管理器

                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

                //产生表格标题行

                HSSFRow row = sheet.createRow(0);

                // 定义注释的大小和位置,详见文档


                for (int i = 0; i < headCN.length; i++) {

                    HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));

                    // 设置注释内容

                    comment.setString(new HSSFRichTextString(headCN[i]));

                    // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.

                    comment.setAuthor("易易递");

                    HSSFCell cell = row.createCell(i);

                    cell.setCellStyle(style);

                    HSSFRichTextString text = new HSSFRichTextString(headCN[i]);

                    cell.setCellValue(text);

                    cell.setCellComment(comment);

                }
                JSON.DEFFAULT_DATE_FORMAT = pattern;
                String json = JSON.toJSONString(dataset1, SerializerFeature.WriteDateUseDateFormat);
                JSONArray jsonArr = JSON.parseArray(json);
                if (jsonArr != null && jsonArr.size() > 0) {
                    for (int j = 0; j < jsonArr.size(); j++) {
                        Object o = jsonArr.get(j);
                        JSONObject obj = null;
                        try {
                            obj = JSONObject.parseObject(o.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        HSSFRow rows = sheet.createRow(j + 1);
                        for (int k = 0; k < headEN.length; k++) {
                            String head = headEN[k];
                            Object data = null;
                            String val = "";
                            if (!StringUtils.isEmpty(head)) {
                                if (head.indexOf(".") >= 0) {
                                    JSONObject jsonObject = JSONArray.parseObject(obj.get(head.split("\\.")[0]).toString());
                                    data = jsonObject.get(head.split("\\.")[1]);
                                } else {
                                    data = obj.get(head);
                                }
                            }
                            if (data == null) {
                                data = "";
                                val = "";
                            } else {
                                val = data.toString();
                            }

                            HSSFCell cell = rows.createCell(k);
                            cell.setCellValue(new HSSFRichTextString(val));
                        }
                    }
                }
            }
        }
        try {
            workbook.write(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}












  