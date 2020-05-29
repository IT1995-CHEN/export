package com.office.export.service;

import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface DataImportService {

  ResponseEntity<byte[]> importData(HttpServletRequest request,
      HttpServletResponse response, String keyRule, String titleRule, String titleLength,
      String excelName, String flag);


  PageInfo searchUser(int pageNum, int pageSize);
}
