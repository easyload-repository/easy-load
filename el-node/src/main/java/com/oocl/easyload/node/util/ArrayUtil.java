package com.oocl.easyload.node.util;

import org.apache.commons.lang3.ArrayUtils;

public final class ArrayUtil {

  private ArrayUtil() {}

  public static boolean isEmpty(Object[] array) {
    return ArrayUtils.isEmpty(array);
  }

  public static boolean isNotEmpty(Object[] array) {
    return ArrayUtils.isNotEmpty(array);
  }
}
