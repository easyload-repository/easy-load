package com.oocl.easyload.server.service;

import com.oocl.easyload.server.vo.ELWebServiceReportVo;

import java.util.Collection;

public interface ELSmartWsReportViewService {
  Collection<ELWebServiceReportVo> queryAll();
}
