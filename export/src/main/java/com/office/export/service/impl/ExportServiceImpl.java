package com.office.export.service.impl;

import com.office.export.controller.BaseFrontController;
import com.office.export.service.DataImportService;
import com.office.export.service.ExportService;
import com.office.export.util.ExcelFormatUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExportServiceImpl implements ExportService {

  @Autowired
      private DataImportService dataImportService;

  Logger logger = LoggerFactory.getLogger(ExportServiceImpl.class);

  @Override
  public ResponseEntity<byte[]> exportExcel(HttpServletRequest request,
      HttpServletResponse response, String excelName, String title, String titleLength,
      List<Map<String, Object>> dataList, List<String> mapKeyList, Integer total,String flag) {
    try {
      logger.info(">>>>>>>>>>开始导出excel>>>>>>>>>>");

      // 造几条数据
//      List<User> list = new ArrayList<>();
//      list.add(new User("唐三藏", "男", 30, "13411111111", "东土大唐", "取西经"));
//      list.add(new User("孙悟空", "男", 29, "13411111112", "菩提院", "打妖怪"));
//      list.add(new User("猪八戒", "男", 28, "13411111113", "高老庄", "偷懒"));
//      list.add(new User("沙悟净", "男", 27, "13411111114", "流沙河", "挑担子"));

      BaseFrontController baseFrontController = new BaseFrontController();
      //设置样式，下载表
      return baseFrontController.buildResponseEntity(export(flag,title, titleLength,
          dataList, mapKeyList, total), excelName + ".xls");

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(">>>>>>>>>>导出excel 异常，原因为：" + e.getMessage());
    }
    return null;
  }

  public List<Map<String,Object>> outData(Map<String,Object> params,String flag,int pageNum,int pageSize){

    switch (flag){
      case "0":
        return dataImportService.searchUser(pageNum,pageSize).getList();
      default:
        return new ArrayList<>();

    }



  }

  private InputStream export(String flag,String title, String titleLength,
      List<Map<String, Object>> dataList, List<String> mapKeyList, Integer total) {
    logger.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
    ByteArrayOutputStream output = null;
    InputStream inputStream1 = null;
    SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 保留1000条数据在内存中
//    SXSSFSheet sheet = wb.createSheet();

    // 设置报表头样式
    CellStyle header = ExcelFormatUtil.headSytle(wb);// cell样式
    CellStyle content = ExcelFormatUtil.contentStyle(wb);// 报表体样式

    // 每一列字段名
    String strs = title;

    // 字段名所在表格的宽度
    //设置每列宽度
    int[] ints = new int[titleLength.split(",").length];
    for (int i = 0; i < titleLength.split(",").length; i++) {
      String m = ObjectUtils.toString(titleLength.split(",")[i], "5000");
      if (m.isEmpty()) {
        m = "5000";
      }
      Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
      if (pattern.matcher(m).matches()){
        int value=Integer.parseInt(m);
        ints[i]=value;
      }else {
        ints[i]=5000;
      }

    }

    Integer max = 1048575;
    Integer size = total / max + 1;

    for (int m = 0; m < size; m++) {
      String limitSql = "limit ";
      limitSql += max * m + "," + max;
      int pageNum=max*m;
      int pageSize=max;
      Map<String,Object> params = new HashMap<>();//该处之后有前端传递查询条件

//      List<Map<String, Object>> list = dataList;//这里之手使用分页查询的数据查询方法
      List<Map<String ,Object>> list = outData(params,flag,pageNum,pageSize);
      SXSSFSheet sheet = wb.createSheet("sheet" + m);
      //创建sheet
      logger.info(">>>>>>>>>>>>>>>>>>>>创建sheet" + m + "完成>>>>>>>>>>");

      ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
      // 设置表头样式

      logger.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");

      if (list.size() == 0) {
        SXSSFRow row = sheet.createRow(0);
        SXSSFCell cell = row.createCell(0);
        cell.setCellValue("无数据");
        cell.setCellStyle(content);
      }

      if (list.size() > 0) {
        logger.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
        for (int i = 0; i < list.size(); i++) {
          Map<String, Object> map = list.get(i);
          SXSSFRow row = sheet.createRow(i + 1);
          int j = 0;

          for (String mapKey : mapKeyList) {
            SXSSFCell cell = row.createCell(j++);
            // 给定各列数据
            cell.setCellValue(ObjectUtils.toString(map.get(mapKey), ""));
            cell.setCellStyle(content);
          }

        }
        logger.info(">>>>>>>>>>>>>>>>>>>>结束遍历数据组装单元格内容>>>>>>>>>>");
      }
    }

    try {
      output = new ByteArrayOutputStream();
      wb.write(output);
      inputStream1 = new ByteArrayInputStream(output.toByteArray());
      output.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {

      try {
        if (output != null) {
          output.close();
          if (inputStream1 != null) {
            inputStream1.close();
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    return inputStream1;
  }
}
