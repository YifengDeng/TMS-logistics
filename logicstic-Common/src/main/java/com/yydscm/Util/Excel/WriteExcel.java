package com.yydscm.Util.Excel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 */
public class WriteExcel {

    /**
     * 导出数据
     *
     * @param tableName excel表格的名字（就是excel文件的名字）
     * @param sheetName excel中单张表格的名字
     * @param headEN    英文title（必填，且必须与实体类中的属性保持一致，即：大小写必须一致）
     * @param headCN    中文title，在excel中要显示哪些标题（必填，与英文title需一一对应）
     * @param list      数据源
     * @param response  输出流
     * @param pattern   日期格式
     * @return
     * @throws IOException
     */
    public static String deriveExcel(String tableName, String sheetName,
                                     String[] headEN, String[] headCN, List<?> list, HttpServletResponse response, String pattern) {
        ExcelTemplater e = new ExcelTemplater();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");

        try {
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String((tableName + ".xls").getBytes("GB2312"),
                    "iso8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        try {
            OutputStream out = response.getOutputStream();
            e.exportExcel(sheetName, headEN, headCN, list, out, pattern);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }


}