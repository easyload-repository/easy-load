package com.oocl.easyload.server.repository;

import com.oocl.easyload.server.dto.smart.ELSmartDomainReportViewDTO;

import java.util.List;

public interface ELSmartDomainReportViewRepository {

  List<ELSmartDomainReportViewDTO> queryAll();

  boolean isReportReady(String roundId);
}
