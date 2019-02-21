package com.oocl.easyload.server.cache;

import com.oocl.easyload.model.entity.el_monitor.SplunkSearch;
import com.oocl.easyload.model.entity.el_monitor.SplunkWsEvent;
import com.oocl.easyload.server.dto.cache.ELWebServiceEventDTO;
import com.oocl.easyload.server.dto.cache.ELWebServiceSearchDTO;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class WebServiceSearchCache {

  public static ConcurrentHashMap<String, ELWebServiceSearchDTO> cache = new ConcurrentHashMap<>();

  public static void putIfAbsent(SplunkSearch splunkSearch) throws IOException {
    ELWebServiceSearchDTO searchCache = cache.get(getKey(splunkSearch));
    if (searchCache == null) {
      searchCache = new ELWebServiceSearchDTO();
      searchCache.setActivityId(splunkSearch.getActivityId());
      searchCache.setRoundId(splunkSearch.getRoundId());
      searchCache.setDomain(splunkSearch.getDomain());
      searchCache.setSplunkUrl(splunkSearch.getDetail().getRoundUrl());
      cache.put(getKey(splunkSearch), searchCache);
    }

    for (SplunkWsEvent event : splunkSearch.getEvents()) {
      ELWebServiceEventDTO cacheEvent = searchCache.getEvents().get(event.getUrl());
      if (cacheEvent == null) {
        cacheEvent = new ELWebServiceEventDTO();
        cacheEvent.setUrl(event.getUrl());
        cacheEvent.setServer(event.getServer());
        cacheEvent.setHitcount(event.getHitcount());
        cacheEvent.setAvgTime(event.getAvg_time());
        searchCache.getEvents().put(cacheEvent.getUrl(), cacheEvent);
      } else {
        cacheEvent.setHitcount(cacheEvent.getHitcount() + event.getHitcount());
        cacheEvent.setAvgTime(
            (cacheEvent.getAvgTime() * cacheEvent.getHitcount()
                    + event.getAvg_time() * event.getHitcount())
                / (cacheEvent.getHitcount() + event.getHitcount()));
      }
    }
  }

  public static String getKey(SplunkSearch splunkSearch) {
    return splunkSearch.getActivityId()
        + "_"
        + splunkSearch.getRoundId()
        + "_"
        + splunkSearch.getDomain();
  }
}
