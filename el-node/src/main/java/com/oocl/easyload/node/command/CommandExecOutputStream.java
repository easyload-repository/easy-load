package com.oocl.easyload.node.command;

import com.oocl.easyload.node.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.LogOutputStream;

// @Component
@Slf4j
public class CommandExecOutputStream extends LogOutputStream {

  FeedbackManager manager = SpringUtil.getBean(FeedbackManager.class, "feedbackManager");

  private String scriptId;
  private int logLevel;

  public CommandExecOutputStream(String scriptId) {
    this(scriptId, Type.OUTPUT);
  }

  public CommandExecOutputStream(String scriptId, int logLevel) {
    super(logLevel);
    this.scriptId = scriptId;
    this.logLevel = logLevel;
  }

  @Override
  protected void processLine(String line, int logLevel) {
    switch (logLevel) {
      case Type.ERROR:
        {
          log.debug(line);
          manager.pushError(scriptId, line);
          break;
        }
      case Type.OUTPUT:
      default:
        {
          log.debug(line);
          manager.pushOutput(scriptId, line);
        }
    }
  }

  @Override
  public String toString() {
    String out;
    switch (logLevel) {
      case Type.ERROR:
        {
          out = manager.printError(scriptId);
          manager.printLogToFile(scriptId);
          break;
        }
      case Type.OUTPUT:
      default:
        {
          out = manager.popOutput(scriptId);
          manager.printLogToFile(scriptId);
        }
    }
    log.debug(out);
    return out;
  }

  public static class Type {
    public static final int OUTPUT = 1;
    public static final int ERROR = 2;
  }
}
