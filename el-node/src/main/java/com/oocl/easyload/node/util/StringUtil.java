package com.oocl.easyload.node.util;

import org.apache.commons.lang3.StringUtils;

public final class StringUtil {

  public static final String EMPTY = "";

  private StringUtil() {}

  public static boolean isEmpty(String str) {
    return StringUtils.isEmpty(str);
  }

  public static boolean isNotEmpty(String str) {
    return StringUtils.isNotEmpty(str);
  }

  public static boolean isBlank(String str) {
    return StringUtils.isBlank(str);
  }

  public static boolean isNotBlank(String str) {
    return StringUtils.isNotBlank(str);
  }

  public static boolean endWith(String str, String suffix) {
    return StringUtils.endsWith(str, suffix);
  }

  public static String appendSuffixIfMissing(String str, String suffix) {
    return StringUtils.appendIfMissing(str, suffix);
  }

  public static String join(String[] strs, String separator) {
    return StringUtils.join(strs, separator);
  }
}
