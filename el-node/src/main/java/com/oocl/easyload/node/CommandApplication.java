package com.oocl.easyload.node;

import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CommandApplication {
  public static void main(String[] args) throws IOException {
    String cmd = "java -jar D:\\loadtest\\baidu\\Java\\javakeeprun.jar";
    CommandLine commandLine = CommandLine.parse(cmd);

    DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
    ExecuteWatchdog watchdog = new ExecuteWatchdog(8000);
    DefaultExecutor executor = new DefaultExecutor();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayOutputStream err = new ByteArrayOutputStream();

    executor.setStreamHandler(new PumpStreamHandler(out, err));
    executor.setWatchdog(watchdog);
    executor.execute(commandLine, resultHandler);

    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(out);
      System.out.println(watchdog.isWatching());
      out.reset();
    }
  }
}
