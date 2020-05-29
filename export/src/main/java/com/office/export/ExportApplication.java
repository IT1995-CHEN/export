package com.office.export;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;


@MapperScan(basePackages = {"com.office.export.mapper"})
@SpringBootApplication
public class ExportApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExportApplication.class, args);
  }

}
