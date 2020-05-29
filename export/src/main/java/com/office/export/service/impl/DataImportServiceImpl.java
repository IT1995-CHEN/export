package com.office.export.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.office.export.mapper.DataImportMapper;
import com.office.export.service.DataImportService;
import com.office.export.service.ExportService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DataImportServiceImpl implements DataImportService {

  @Autowired
  private ExportService exportService;
  @Autowired
  private DataImportMapper dataImportMapper;

  public Integer outDataTotal(Map<String, Object> params, String flag) {
    switch (flag) {
      case "0":
        return dataImportMapper.findAllUserCount();
      default:
        return 0;

    }
  }

  @Override
  public ResponseEntity<byte[]> importData(HttpServletRequest request,
      HttpServletResponse response, String keyRule, String titleRule, String titleLength,
      String excelName, String flag) {

    //造几条数据
    //初始化Map
//    Map<String, Object> map1 = new HashMap<String, Object>() {{
//      put("name", "暴躁程序员");
//      put("sex", "男");
//      put("age", 30);
//      put("phone", "13411111111");
//      put("adress", "");
//      put("work", "");
//    }};
//    Map<String, Object> map2 = new HashMap<String, Object>() {{
//      put("name", "码农");
//      put("sex", "男");
//      put("age", 29);
//      put("phone", "13411111112");
//      put("adress", "菩提院");
//      put("work", "改bug");
//    }};
//    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>() {{
//      add(map1);
//      add(map2);
//    }};
//    System.out.println(list);
//    Integer total = list.size();//这处之后使用查询语句查出查询所需查询数据的总数

    Map<String, Object> params = new HashMap<>();//该处之后有前端传递查询条件
    Integer total = outDataTotal(params, flag);
    System.out.println(total);
    List<String> mapKeyList = new ArrayList<String>();

    for (int i = 0; i < keyRule.split(",").length; i++) {
      mapKeyList.add(keyRule.split(",")[i]);
    }



    List<Map<String, Object>> listEmpty = new ArrayList<>();
    return exportService.exportExcel(request, response,
        excelName, titleRule, titleLength,
        listEmpty, mapKeyList, total, flag);


  }

  @Override
  public PageInfo searchUser(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Map<String, Object>> list = dataImportMapper.searchUser();

    return new PageInfo(list);
  }


}


