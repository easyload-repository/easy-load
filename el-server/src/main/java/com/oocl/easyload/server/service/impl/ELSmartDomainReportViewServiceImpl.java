package com.oocl.easyload.server.service.impl;

import com.oocl.easyload.server.dto.smart.ELSmartDomainReportViewDTO;
import com.oocl.easyload.server.repository.ELSmartDomainReportViewRepository;
import com.oocl.easyload.server.service.ELSmartDomainReportViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ELSmartDomainReportViewServiceImpl implements ELSmartDomainReportViewService {

  @Autowired private ELSmartDomainReportViewRepository repository;

  @Override
  public List<ELSmartDomainReportViewDTO> queryAll() {
    return repository.queryAll();
  }

  @Override
  public boolean isReportReady(String roundId) {
    return repository.isReportReady(roundId);
  }
}
