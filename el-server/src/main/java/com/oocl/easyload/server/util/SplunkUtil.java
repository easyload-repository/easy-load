package com.oocl.easyload.server.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SplunkUtil {

  private static DateFormat format = new SimpleDateFormat("MM/dd/yyyy:HH:mm:ss");

  public static String getSplunkDate(Date date) {
    return format.format(date);
  }
}
