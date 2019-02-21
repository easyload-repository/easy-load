package com.oocl.easyload.server.controller;

import com.oocl.easyload.server.dto.smart.ELSmartDomainReportViewDTO;
import com.oocl.easyload.server.service.ELSmartDomainReportViewService;
import com.oocl.easyload.server.service.ELSmartWsReportViewService;
import com.oocl.easyload.server.vo.ELWebServiceReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ELSmartReportViewController {
  @Autowired private ELSmartWsReportViewService wsService;

  @Autowired private ELSmartDomainReportViewService domainService;

  @GetMapping("/ws/all")
  public @ResponseBody Collection<ELWebServiceReportVo> getAllWsReports() {
    return wsService.queryAll();
  }

  @GetMapping("/domain/all")
  public List<ELSmartDomainReportViewDTO> getAllDomainReports() {
    List<ELSmartDomainReportViewDTO> reports = domainService.queryAll();
    return reports;
  }

  @GetMapping("/{roundId}/isReady")
  public boolean isReadyReport(@PathVariable String roundId) {
    return domainService.isReportReady(roundId);
  }
}
