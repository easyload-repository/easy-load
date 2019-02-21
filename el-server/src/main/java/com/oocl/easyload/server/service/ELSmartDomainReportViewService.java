package com.oocl.easyload.server.service;

import com.oocl.easyload.server.dto.smart.ELSmartDomainReportViewDTO;

import java.util.List;

public interface ELSmartDomainReportViewService {

  List<ELSmartDomainReportViewDTO> queryAll();

  boolean isReportReady(String roundId);
}
