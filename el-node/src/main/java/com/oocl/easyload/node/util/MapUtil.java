package com.oocl.easyload.node.util;

import org.apache.commons.collections4.MapUtils;

import java.util.Map;

public final class MapUtil {

  private MapUtil() {}

  public static boolean isEmpty(Map<?, ?> map) {
    return MapUtils.isEmpty(map);
  }
}
