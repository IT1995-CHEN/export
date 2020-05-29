package com.office.export.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface DataImportMapper {

  List<Map<String,Object>> searchUser();

  Integer findAllUserCount();

}
