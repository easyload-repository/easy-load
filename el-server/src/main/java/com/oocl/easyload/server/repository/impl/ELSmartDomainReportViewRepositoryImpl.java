package com.oocl.easyload.server.repository.impl;

import com.oocl.easyload.server.dto.smart.ELSmartDomainReportViewDTO;
import com.oocl.easyload.server.repository.ELSmartDomainReportViewRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ELSmartDomainReportViewRepositoryImpl implements ELSmartDomainReportViewRepository {

  @Resource private JdbcTemplate jdbcTemplate;

  @Override
  public List<ELSmartDomainReportViewDTO> queryAll() {
    String sql = "select * from el_smart_domain_report_view";
    RowMapper<ELSmartDomainReportViewDTO> rowMapper =
        new BeanPropertyRowMapper<>(ELSmartDomainReportViewDTO.class);
    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public boolean isReportReady(String roundId) {
    String sql = "select * from el_smart_report_current where round_id = ?";
    List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
    records = jdbcTemplate.queryForList(sql, roundId);
    return records.size() > 0;
  }
}
