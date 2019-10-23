/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ExcelUtils
 * Author:   yao
 * Date:     2019/7/9 11:24
 * Description: Excel操作工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;

/**
 * 〈Excel操作工具类〉
 *
 * @author yao
 * @create 2019/7/9
 * @since 1.0.0
 */
public class ExcelUtils {
    public static final String OFFICE_EXCEL_XLS = "xls";
    public static final String OFFICE_EXCEL_XLSX = "xlsx";

    /**
     * 打开一个excel文件，并获取其对象Workbook
     */
    public static Workbook openExcel(String excelPath) {
        Workbook workbook = null;
        File excelFile = new File(excelPath);
        if (excelFile.exists()) {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(excelFile);
                workbook = new HSSFWorkbook(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Log4JUtils.getLogger().error("excel文件不存在-->" + excelPath);
        }
        return workbook;
    }

    /**
     * 保存excel内容
     */
    public static void saveExcel(String excelPath, Workbook workbook) {
        //写数据到这个路径上
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(excelPath);
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}