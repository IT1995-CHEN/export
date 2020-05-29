package com.office.export.controller;

import com.github.pagehelper.PageInfo;
import com.office.export.mapper.DataImportMapper;
import com.office.export.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
@Autowired
private DataImportService dataImportService;

@Autowired
private DataImportMapper dataImportMapper;
  @GetMapping("/getAll")
  public PageInfo getAll(int pageNum, int pageSize){
    return dataImportService.searchUser(pageNum,pageSize);
  }

  @GetMapping("/getAllCount")
  public Integer getAllCount(){
    return dataImportMapper.findAllUserCount();
  }
}
