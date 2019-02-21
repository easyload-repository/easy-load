package com.oocl.easyload.server.service.impl;

import com.oocl.easyload.server.dto.smart.ELSmartWsReportViewDTO;
import com.oocl.easyload.server.repository.ELSmartWsReportViewRepository;
import com.oocl.easyload.server.service.ELSmartWsReportViewService;
import com.oocl.easyload.server.vo.ELWebServiceReportVo;
import com.oocl.easyload.server.vo.ELWebServiceUrlReportVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ELSmartWsReportViewServiceImpl implements ELSmartWsReportViewService {
  @Autowired private ELSmartWsReportViewRepository repository;

  @Override
  public Collection<ELWebServiceReportVo> queryAll() {
    Map<String, ELWebServiceReportVo> groupByMap = new HashMap<>();
    List<ELSmartWsReportViewDTO> elSmartWsReportViewDTOS = repository.queryAll();
    for (ELSmartWsReportViewDTO wsView : elSmartWsReportViewDTOS) {
      String domain = this.getDomain(wsView.getUrl());
      if (groupByMap.get(domain) == null) {
        ELWebServiceReportVo reportVo = new ELWebServiceReportVo();
        reportVo.setDomain(domain);
        groupByMap.put(domain, reportVo);
      }
      ELWebServiceUrlReportVo urlReportVo = new ELWebServiceUrlReportVo();
      BeanUtils.copyProperties(wsView, urlReportVo);

      groupByMap.get(domain).getWsReports().add(urlReportVo);
    }

    return groupByMap.values();
  }

  private String getDomain(String url) {
    Pattern pattern = Pattern.compile("/(.+?)/.*");
    Matcher matcher = pattern.matcher(url);
    if (matcher.find()) {
      String domain = matcher.group(1);
      return domain;
    }
    return "";
  }
}
