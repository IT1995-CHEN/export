package com.office.export.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

public interface ExportService {

  ResponseEntity<byte[]> exportExcel(HttpServletRequest request, HttpServletResponse response,
      String excelName, String title, String titleLength,
      List<Map<String, Object>> dataList, List<String> mapKeyList, Integer total,String flag);

}
