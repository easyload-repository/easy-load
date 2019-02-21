package com.oocl.easyload.node.util;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class MapUtilTest {

  @Test
  public void isEmpty() {
    assertTrue(MapUtil.isEmpty(null));
    assertTrue(MapUtil.isEmpty(new HashMap<>()));
  }
}
