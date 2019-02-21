package com.oocl.easyload.server.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyload.model.entity.ELStatus;
import com.oocl.easyload.server.websocket.DomainRunnerStatusWebsocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class RunningStatusMonitorCache {

  private static Logger logger = LoggerFactory.getLogger(RunningStatusMonitorCache.class);
  public static ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

  private static void sendCacheToWebSocket() {
    try {
      DomainRunnerStatusWebsocket.sendInfo(new ObjectMapper().writeValueAsString(cache), null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void markToTrialRunInProgress(String domain) {
    logger.info("[Status change]" + domain + " " + ELStatus.TRIAL_RUN_IN_PROGRESS.name());
    cache.put(domain, ELStatus.TRIAL_RUN_IN_PROGRESS.name());
    sendCacheToWebSocket();
  }

  public static void markToComplete(String domain) {
    logger.info("[Status change]" + domain + " " + ELStatus.COMPLETE.name());
    cache.put(domain, ELStatus.COMPLETE.name());
    sendCacheToWebSocket();
  }

  public static void markToStop(String domain) {
    logger.info("[Status change]" + domain + " " + ELStatus.STOP.name());
    cache.put(domain, ELStatus.STOP.name());
    sendCacheToWebSocket();
  }

  public static void markToInProgress(String domain) {
    logger.info("[Status change]" + domain + " " + ELStatus.IN_PROCESS.name());
    cache.put(domain, ELStatus.IN_PROCESS.name());
    sendCacheToWebSocket();
  }

  public static void markToError(String domain) {
    logger.info("[Status change]" + domain + " " + ELStatus.ERROR.name());
    cache.put(domain, ELStatus.ERROR.name());
    sendCacheToWebSocket();
  }

  public static void markToReady(String domain) {
    logger.info("[Status change]" + domain + " " + ELStatus.READY.name());
    cache.put(domain, ELStatus.READY.name());
    sendCacheToWebSocket();
  }
}
