package com.oocl.easyload.server.client;

import com.google.common.collect.ImmutableMap;

public class ClientUtil {
  public static final String BASE_URL = "http://{host}:{port}";

  /**
   * 该方法用于快速创建guava immutable map builder。 该方法用于快速创建guava immutable map builder。
   *
   * @param host 需要传入的host
   * @param port 需要传入的port
   * @return MapBuilder
   */
  public static ImmutableMap.Builder<String, Object> createMapBuilder(String host, int port) {
    return ImmutableMap.<String, Object>builder().put("host", host).put("port", port);
  }
}
