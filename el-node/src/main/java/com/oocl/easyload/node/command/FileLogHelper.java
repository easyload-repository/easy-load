package com.oocl.easyload.node.command;

import com.oocl.easyload.node.pojo.ScriptDetail;
import com.oocl.easyload.node.util.CommandCheckUtil;
import com.oocl.easyload.node.util.FileUtil;
import com.oocl.easyload.node.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Help write log to file system */
@Component
public class FileLogHelper {

  private Map<String, StringBuilder> outputLogCache = new ConcurrentHashMap<>();
  private Map<String, StringBuilder> errorLogCache = new ConcurrentHashMap<>();

  private static final int MAX_LENGTH = 64 * 1024;

  public void pushOutputLog(String scriptId, String text) {
    StringBuilder builder = getOutStringBuilder(scriptId);
    builder.append(text).append('\n');
    if (builder.length() > MAX_LENGTH) {
      popOutputLog(scriptId);
    }
  }

  public void pushErrorLog(String scriptId, String text) {
    StringBuilder builder = getErrStringBuilder(scriptId);
    builder.append(text).append('\n');
    if (builder.length() > MAX_LENGTH) {
      popErrorLog(scriptId);
    }
  }

  public void popAllLog(String scriptId) {
    popOutputLog(scriptId);
    popErrorLog(scriptId);
  }

  public void popOutputLog(String scriptId) {
    StringBuilder builder = getOutStringBuilder(scriptId);
    String pathname = getScriptFileName(scriptId) + ".out";
    writeLog(pathname, builder);
  }

  public void popErrorLog(String scriptId) {
    StringBuilder builder = getErrStringBuilder(scriptId);
    String pathname = getScriptFileName(scriptId) + ".err";
    writeLog(pathname, builder);
  }

  private void writeLog(String pathname, StringBuilder builder) {
    if (builder.length() < 1 || StringUtil.isBlank(pathname)) {
      return;
    }
    FileUtil.appendToFile(pathname, builder.toString());
    builder.delete(0, builder.length());
  }

  private String getScriptFileName(String scriptId) {
    ScriptDetail detail = CommandExecutor.DETAIL_MAP.get(scriptId);
    if (detail == null) {
      return StringUtil.EMPTY;
    }
    return CommandCheckUtil.extractFilePath(detail.getScriptCommand());
  }

  private StringBuilder getOutStringBuilder(String scriptId) {
    if (!outputLogCache.containsKey(scriptId)) {
      outputLogCache.put(scriptId, new StringBuilder());
    }
    return outputLogCache.get(scriptId);
  }

  private StringBuilder getErrStringBuilder(String scriptId) {
    if (!errorLogCache.containsKey(scriptId)) {
      errorLogCache.put(scriptId, new StringBuilder());
    }
    return errorLogCache.get(scriptId);
  }
}
