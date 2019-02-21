package com.oocl.easyload.server.repository.impl;

import com.oocl.easyload.server.dto.smart.ELSmartWsReportViewDTO;
import com.oocl.easyload.server.repository.ELSmartWsReportViewRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ELSmartWsReportViewRepositoryImpl implements ELSmartWsReportViewRepository {

  @Resource private JdbcTemplate jdbcTemplate;

  @Override
  public List<ELSmartWsReportViewDTO> queryAll() {
    String sql = "select * from el_smart_ws_report_view";
    RowMapper<ELSmartWsReportViewDTO> rowMapper =
        new BeanPropertyRowMapper<>(ELSmartWsReportViewDTO.class);
    return jdbcTemplate.query(sql, rowMapper);
  }
}
