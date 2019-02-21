package com.oocl.easyload.server.cache;

import com.oocl.easyload.model.entity.el_monitor.SplunkSearch;
import com.oocl.easyload.model.entity.el_monitor.SplunkWsException;
import com.oocl.easyload.server.dto.cache.ELExceptionEventDTO;
import com.oocl.easyload.server.dto.cache.ELExceptionSearchDTO;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ExceptionSearchCache {

  public static ConcurrentHashMap<String, ELExceptionSearchDTO> cache = new ConcurrentHashMap<>();

  public static void putIfAbsent(SplunkSearch splunkSearch) throws IOException {
    ELExceptionSearchDTO searchCache = cache.get(getKey(splunkSearch));
    if (searchCache == null) {
      searchCache = new ELExceptionSearchDTO();
      searchCache.setActivityId(splunkSearch.getActivityId());
      searchCache.setRoundId(splunkSearch.getRoundId());
      searchCache.setDomain(splunkSearch.getDomain());
      searchCache.setSplunkUrl(splunkSearch.getDetail().getRoundUrl());
      cache.put(getKey(splunkSearch), searchCache);
    }

    for (SplunkWsException event : splunkSearch.getExceptions()) {
      ELExceptionEventDTO cacheEvent = searchCache.getEvents().get(event.getExp_info());
      if (cacheEvent == null) {
        cacheEvent = new ELExceptionEventDTO();
        cacheEvent.setExceptionInfo(event.getExp_info());
        cacheEvent.setCount(event.getCount());
        cacheEvent.setHost(event.getHost());
        cacheEvent.setSource(event.getSource());
        searchCache.getEvents().put(cacheEvent.getExceptionInfo(), cacheEvent);
      } else {
        cacheEvent.setCount(cacheEvent.getCount() + event.getCount());
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
