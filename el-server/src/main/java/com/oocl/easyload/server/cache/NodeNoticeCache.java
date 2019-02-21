package com.oocl.easyload.server.cache;

import com.oocl.easyload.model.entity.ELScript;
import com.oocl.easyload.server.common.NodeConstatnce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NodeNoticeCache {
  private static Logger logger = LoggerFactory.getLogger(NodeNoticeCache.class);
  public static Map<String, Collection<String>> attenderCompleteScript = new ConcurrentHashMap<>();
  public static Map<String, Collection<String>> attenderErrorScript = new ConcurrentHashMap<>();

  public static void addScript(String attenderId, String scriptId, String status) {
    logger.debug(
        "add attenderId:{},scriptId:{},status:{} in to node notice cache",
        attenderId,
        scriptId,
        status);
    if (NodeConstatnce.COMPLETE.equals(status)) {
      if (!attenderCompleteScript.containsKey(attenderId)) {
        attenderCompleteScript.put(attenderId, new ConcurrentLinkedQueue<>());
      }
      attenderCompleteScript.get(attenderId).add(scriptId);
    } else if (NodeConstatnce.ERROR.equals(status)) {
      if (!attenderErrorScript.containsKey(attenderId)) {
        attenderErrorScript.put(attenderId, new ConcurrentLinkedQueue<>());
      }
      attenderErrorScript.get(attenderId).add(scriptId);
    }
  }

  public static boolean isAttenderReadyOrComplete(String attenderId, List<ELScript> elScripts) {
    Collection<String> completeScript = attenderCompleteScript.get(attenderId);
    return elScripts.stream()
        .filter(ELScript::getLastExecute)
        .allMatch(
            elScript -> {
              if (logger.isDebugEnabled()) {
                boolean contain = completeScript.contains(elScript.getScriptId());
                if (!contain) {
                  logger.debug("scriptId:{} have not in complete cache", elScript.getScriptId());
                } else {
                  logger.debug("attenderId:{} have complete", attenderId);
                }
                return contain;
              }
              return completeScript.contains(elScript.getScriptId());
            });
  }

  /** 当进入execute的时候，应该清除所有缓存。 */
  public static void clearAll() {
    logger.debug("clear cache");
    attenderCompleteScript.clear();
    attenderErrorScript.clear();
  }

  public static void clear(String attenderId) {
    logger.debug("clear attenderId:{} cache", attenderId);
    if (attenderCompleteScript.containsKey(attenderId)) {
      attenderCompleteScript.get(attenderId).clear();
    }
    if (attenderErrorScript.containsKey(attenderId)) {
      attenderErrorScript.get(attenderId).clear();
    }
  }
}
