package com.office.export.controller;

import com.office.export.service.DataImportService;
import com.office.export.service.ExportService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExportController {

  @Autowired
  private ExportService exportService;

  @Autowired
  private DataImportService dataImportService;


  //导出excel
  @GetMapping("/exportExcel")
  public ResponseEntity<byte[]> exportExcel(HttpServletRequest request,
      HttpServletResponse response, String keyRule, String titleRule, String titleLength,
      String excelName,String flag) {
    //设置默认前端排序key值。该值用于测试，之后应由前端传递
    keyRule = "name,sex,age,phone,address,hobby";
    //设置默认的excel的头部中文名称。该值用于测试，之后应由前端传递
    titleRule = "原告名称,原告类型,原告证件号码,手机号码,地址,兴趣爱好";
    //设置默认的excel每列的宽度。该值用于测试，之后应由前端传递
    titleLength = "5000,6000,7000,5000,5000,500";
    //设置导出文件的默认名称。该值用于测试，之后应由前端传递
    excelName = "用户信息";
    //设置需要打印页面的固定标记符。该值用于测试，之后应由前端传递。用于后面逻辑方法调用判断
    flag ="0";
    try {
      return dataImportService.importData(request, response, keyRule, titleRule, titleLength,excelName,flag);
    } catch (Exception e) {
      return null;
    }
  }

}
