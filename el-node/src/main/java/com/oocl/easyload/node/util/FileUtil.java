package com.oocl.easyload.node.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class FileUtil {

  private FileUtil() {}

  public static boolean isFileExist(String path) {
    File file = new File(path);
    return file.exists();
  }

  public static boolean isDirectory(String path) {
    File file = new File(path);
    return file.exists() && file.isDirectory();
  }

  public static boolean isShareFolder(String path) {
    try {
      InetAddress address = InetAddress.getLocalHost();
      String hostname = address.getHostName();
      String netPath = "\\\\" + hostname + "\\" + path;
      return isDirectory(netPath);
    } catch (UnknownHostException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public static void appendToFile(String pathname, String text) {
    File file = new File(pathname);
    try (FileWriter fw = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(fw)) {
      pw.println(text);
      pw.flush();
      fw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
