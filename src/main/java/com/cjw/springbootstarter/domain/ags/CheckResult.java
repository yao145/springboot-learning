/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CheckResult
 * Author:   yao
 * Date:     2019/5/28 19:15
 * Description: 一张图审查结果
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags;

import com.cjw.springbootstarter.base.GlobeVarData;
import com.cjw.springbootstarter.domain.onemap.IOnemapType;
import com.cjw.springbootstarter.util.ExcelUtils;
import com.cjw.springbootstarter.util.Log4JUtils;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 〈一张图审查结果〉
 *
 * @author yao
 * @create 2019/5/28
 * @since 1.0.0
 */
@Data
public class CheckResult implements Serializable {


    /**
     * 结果代码
     */
    private String statusCode;

    /**
     * 结果展示名称
     */
    private String statusName;
    /**
     * 结果的综合性描述，根据模型不同进行区分
     */
    private String detail;

    public CheckResult() {
    }

    /**
     * 主动构建一个审查结果
     */
    public CheckResult(String statusCode) {
        this.statusCode = statusCode;
        this.statusName = JobStatus.getStatusName(statusCode);
    }

    /**
     * 根据模型类型，动态构建当前结果的描述信息
     */
    public void refreshResult(TArcGpserver gpserver, FeatureArray featureArray, String jobId) {

        //根据模型类型进行结果描述语言的构建
        //featureArray中存放着统计信息
        String modelName = gpserver.getName();

        List<FeatureItem> features = featureArray.getFeatures();
        if (IOnemapType.DLTB.equals(modelName)) {
            this.statusCode = JobStatus.RESULT_WARNING;
            this.detail = "请通过对项目范围内的用地现状构成进行分析，以确认报件出让方式、利用方向的合理性";
            //土地利用现状需要进行excel表格内容的构建
            generateDltbExcel(jobId, features);
        } else if (IOnemapType.TDGH.equals(modelName)) {
            this.statusCode = JobStatus.RESULT_WARNING;
            this.detail = "请通过对项目范围内的用地规划构成进行分析，以确认报件中土地地类、权属、面积在规划内";
        } else if (IOnemapType.JBNT.equals(modelName) || IOnemapType.TDZZ.equals(modelName) || IOnemapType.KCQ.equals(modelName)
                || IOnemapType.DISASTER.equals(modelName) || IOnemapType.TDWF.equals(modelName)) {
            String tarName = "";
            switch (modelName) {
                case IOnemapType.JBNT:
                    tarName = "基本农田数据";
                    break;
                case IOnemapType.TDZZ:
                    tarName = "土地整治项目";
                    break;
                case IOnemapType.KCQ:
                    tarName = "矿产资源数据";
                    break;
                case IOnemapType.DISASTER:
                    tarName = "地灾区域数据";
                    break;
                case IOnemapType.TDWF:
                    tarName = "土地违法地块";
                    break;
                default:
            }
            if (features.size() == 0) {
                this.statusCode = JobStatus.RESULT_SUCCESS;
            } else if (features.size() == 1) {
                Object area = features.get(0).getValueByName("AREA");
                String areaStr = convertM2HA(area);
                if ("".equals(areaStr)) {
                    this.statusCode = JobStatus.RESULT_SUCCESS;
                } else {
                    this.statusCode = JobStatus.RESULT_ERROR;
                    this.detail = "报件中的项目范围占用" + tarName + "，合计 " + areaStr;
                }
            } else {
                Log4JUtils.getLogger().error(tarName + "统计结果出现两条记录，代码存在逻辑错误");
            }
        } else if (IOnemapType.GZQ.equals(modelName)) {
            if (features.size() == 0) {
                this.statusCode = JobStatus.RESULT_ERROR;
                this.detail = "未发现有效数据，请检验传入坐标的有效性";
            } else {
                //需要逻辑写死，通过GZQLXDM字段信息判断是否符合建设用地要求
                FeatureItem featureItem = null;
                int flag = 0;
                for (FeatureItem item : features) {
                    String gzqDM = item.getValueByName("GZQLXDM").toString();
                    if ("020".equals(gzqDM) && featureItem == null) {
                        //有条件
                        featureItem = item;
                        flag = 1;
                    } else if ("030".equals(gzqDM)) {
                        //限制区
                        featureItem = item;
                        flag = 2;
                    }
                }
                if (flag == 0) {
                    this.statusCode = JobStatus.RESULT_SUCCESS;
                } else if (flag == 1) {
                    this.statusCode = JobStatus.RESULT_WARNING;
                    this.detail = "报件中的项目范围含有条件建设区";
                } else if (flag == 2) {
                    this.statusCode = JobStatus.RESULT_ERROR;
                    this.detail = "报件中的项目范围含限制建设区";
                }
            }
        }
        this.statusName = JobStatus.getStatusName(statusCode);
    }

    /**
     * 生成《土地利用现状二级分类面积汇总表》
     */
    private void generateDltbExcel(String jobId, List<FeatureItem> features) {
        //1 将模板进行拷贝
        String orgDirPath = GlobeVarData.FileUploadFolderPath + File.separator + "onemap" + File.separator;
        String downDirPath = GlobeVarData.FileUploadFolderPath + File.separator + "onemap" + File.separator + jobId;
        File dirFile = new File(downDirPath);
        if (dirFile.exists() == false) {
            dirFile.mkdir();
        }
        File orgFile = new File(orgDirPath + GlobeVarData.excelTempTdly);
        File desFile = new File(downDirPath + File.separator + GlobeVarData.excelTempTdly);
        if (desFile.exists()) {
            Log4JUtils.getLogger().warn("统计表格已经创建，请勿重复创建-->" + jobId);
            return;
        }
        try {
            System.out.println(orgFile + "--->" + desFile);
            FileCopyUtils.copy(orgFile, desFile);
        } catch (IOException e) {
            Log4JUtils.getLogger().error("文件拷贝失败...");
        }
        if (desFile.exists() == false) {
            Log4JUtils.getLogger().error("未发现拷贝后的excel文件...");
            return;
        }
        //2 解析统计信息到键值对中
        HashMap<String, Double> codeToArea = new HashMap<String, Double>();
        for (FeatureItem featureItem : features) {
            Object codeObj = featureItem.getValueByName("DLBM");
            Object areaObj = featureItem.getValueByName("AREA");
            if (codeObj != null && areaObj != null) {
                String code = codeObj.toString();
                Double area = Double.parseDouble(areaObj.toString());
                DecimalFormat df = new DecimalFormat(".00");
                String areaHA = df.format(area / 10000);
                codeToArea.put(code, Double.parseDouble(areaHA));
            }
        }
        //3 根据指定结果更新excel中的数据内容
        try {
            Workbook workbook = ExcelUtils.openExcel(desFile.getCanonicalPath());
            //进行内容更新操作
            Sheet sheet = workbook.getSheetAt(0);
            //模板中的第五行为编码名称
            Row row = sheet.getRow(4);
            //模板中的第六行为待填入内容
            Row valRow = sheet.getRow(5);
            int columNos = row.getLastCellNum();
            for (int j = 0; j < columNos; j++) {
                Cell cell = row.getCell(j);
                String nameAndCode = cell.getStringCellValue();
                //通过正则方式取出编码
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(nameAndCode);
                String code = m.replaceAll("").trim();
                if (code.length() > 0) {
                    Cell valCell = valRow.getCell(j);
                    Double areaObj = codeToArea.get(code);
                    if (areaObj != null) {
                        valCell.setCellValue(areaObj);
                    }
                }
            }
            //刷新各个公式中的内容
            HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            //4 进行保存工作
            ExcelUtils.saveExcel(desFile.getCanonicalPath(), workbook);
            Log4JUtils.getLogger().info("土地利用面积统计表生成成功，任务编号为-->" + jobId);
        } catch (IOException e) {
            Log4JUtils.getLogger().error("土地利用面积统计表生成失败，任务编号为-->" + jobId);
            e.printStackTrace();
        }
    }

    /**
     * 平方米转公顷
     */
    private String convertM2HA(Object fieldValue) {
        try {
            double area = Double.parseDouble(fieldValue.toString());
            if (area <= 0.00001) {
                return "";
            } else if (area <= 100) {
                return area + " 平方米";
            } else {
                DecimalFormat df = new DecimalFormat(".00");
                String areaHA = df.format(area / 10000);
                return areaHA + " 公顷";
            }
        } catch (Exception ex) {
            return " -1 平方米";
        }
    }

}
