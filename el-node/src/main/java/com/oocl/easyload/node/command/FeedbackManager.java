package com.oocl.easyload.node.command;

import com.oocl.easyload.node.jms.Producer;
import com.oocl.easyload.node.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Component("feedbackManager")
public class FeedbackManager {

  @Autowired private Producer producer;

  @Autowired private FileLogHelper logHelper;

  private Map<String, Queue<String>> output = new ConcurrentHashMap<>();
  private Map<String, Queue<String>> error = new ConcurrentHashMap<>();

  private static final int MESSAGE_MAX_LENGTH = 1024 * 32;
  private static final int OUTPUT_MESSAGE_SIZE = 32;
  private static final int ERROR_MESSAGE_SIZE = 16;

  public void pushOutput(String scriptId, String text) {
    logHelper.pushOutputLog(scriptId, text);
  }

  public void pushError(String scriptId, String text) {
    Queue<String> queue = getErrorQueue(scriptId);
    queue.add(text);
    logHelper.pushErrorLog(scriptId, text);
  }

  public String popOutput(String scriptId) {
    // do nothing
    return StringUtil.EMPTY;
  }

  public String popError(String scriptId) {
    Queue<String> queue = getErrorQueue(scriptId);
    StringBuilder builder = new StringBuilder();
    while (!queue.isEmpty()) {
      String text = queue.remove();
      builder.append(text).append('\n');
    }
    return builder.toString();
  }

  public String printError(String scriptId) {
    List<String> list = (LinkedList<String>) getErrorQueue(scriptId);
    StringBuilder builder = new StringBuilder();
    for (String str : list) {
      builder.append(str).append('\n');
    }
    return builder.toString();
  }

  public void printLogToFile(String scriptId) {
    logHelper.popAllLog(scriptId);
  }

  private Queue<String> getOutputQueue(String scriptId) {
    if (!output.containsKey(scriptId)) {
      output.put(scriptId, new LinkedList<>());
    }
    return output.get(scriptId);
  }

  private Queue<String> getErrorQueue(String scriptId) {
    if (!error.containsKey(scriptId)) {
      error.put(scriptId, new LinkedList<>());
    }
    return error.get(scriptId);
  }
}
