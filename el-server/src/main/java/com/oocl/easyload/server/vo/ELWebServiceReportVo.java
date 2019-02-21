package com.oocl.easyload.server.vo;

import java.util.ArrayList;
import java.util.List;

public class ELWebServiceReportVo {

  private String domain;
  private List<ELWebServiceUrlReportVo> wsReports;

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public List<ELWebServiceUrlReportVo> getWsReports() {
    if (this.wsReports == null) {
      this.wsReports = new ArrayList<>();
    }
    return wsReports;
  }
}
