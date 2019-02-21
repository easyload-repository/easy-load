package com.oocl.easyload.server.repository;

import com.oocl.easyload.server.dto.smart.ELSmartWsReportViewDTO;

import java.util.List;

public interface ELSmartWsReportViewRepository {

  List<ELSmartWsReportViewDTO> queryAll();
}
