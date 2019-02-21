package com.oocl.easyload.node.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Queue;

public class QueueUtil {

  public static boolean isEmpty(Queue<?> queue) {
    return CollectionUtils.isEmpty(queue);
  }

  public static boolean isNotEmpty(Queue<?> queue) {
    return CollectionUtils.isNotEmpty(queue);
  }
}
