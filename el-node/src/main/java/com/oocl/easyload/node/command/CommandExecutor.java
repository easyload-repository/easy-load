package com.oocl.easyload.node.command;

import com.oocl.easyload.node.pojo.ScriptDetail;
import org.apache.commons.exec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandExecutor {

  private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

  public static Map<String, ExecuteWatchdog> WATCHDOG_MAP = new ConcurrentHashMap<>();
  public static Map<String, OutputStream> OUTPUT_STREAM_MAP = new ConcurrentHashMap<>();
  public static Map<String, OutputStream> ERROR_STREAM_MAP = new ConcurrentHashMap<>();
  public static Map<String, ScriptDetail> DETAIL_MAP = new ConcurrentHashMap<>();

  public static boolean execute(ScriptDetail detail, int duration) throws Exception {
    String scriptId = detail.getScriptId();
    String command = detail.getScriptCommand();

    if (scriptId == null || command == null) {
      return false;
    }

    logger.info("CommandExecutor execute() -> scriptId [{}], command: {}", scriptId, command);

    CommandLine commandLine = CommandLine.parse(command);

    OutputStream out = new CommandExecOutputStream(scriptId, CommandExecOutputStream.Type.OUTPUT);
    OutputStream err = new CommandExecOutputStream(scriptId, CommandExecOutputStream.Type.ERROR);

    DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
    ExecuteWatchdog watchdog = new ExecuteWatchdog(duration * 1000);
    DefaultExecutor executor = new DefaultExecutor();

    executor.setStreamHandler(new PumpStreamHandler(out, err));

    WATCHDOG_MAP.put(scriptId, watchdog);
    OUTPUT_STREAM_MAP.put(scriptId, out);
    ERROR_STREAM_MAP.put(scriptId, err);
    DETAIL_MAP.put(scriptId, detail);

    executor.setWatchdog(watchdog);
    executor.execute(commandLine, resultHandler);

    //        executor.execute(commandLine, new DefaultExecuteResultHandler() {
    //            @Override
    //            public void onProcessComplete(int exitValue) {
    //                super.onProcessComplete(exitValue);
    //                logger.info("CommandExecutor execute() success");
    //                watchdog.destroyProcess();
    //            }
    //
    //            @Override
    //            public void onProcessFailed(ExecuteException e) {
    //                super.onProcessFailed(e);
    //                logger.info("CommandExecutor execute() fail");
    //                d="";
    //
    //            }
    //
    //        });
    resultHandler.waitFor(5);

    return watchdog.isWatching();
  }

  //    public static boolean isProcessAlive(ExecuteWatchdog watchdog) {
  //
  //        return watchdog.isWatching();
  //    }

  public static void main(String[] args) throws Exception {

    CommandLine commandLine = CommandLine.parse("java");

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayOutputStream err = new ByteArrayOutputStream();

    DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

    ExecuteWatchdog watchdog = new ExecuteWatchdog(1);

    DefaultExecutor executor = new DefaultExecutor();

    executor.setStreamHandler(new PumpStreamHandler(out, err));

    executor.execute(
        commandLine,
        new DefaultExecuteResultHandler() {
          @Override
          public void onProcessComplete(int exitValue) {
            super.onProcessComplete(exitValue);
            System.out.println(exitValue);
            System.out.println("success");
          }

          @Override
          public void onProcessFailed(ExecuteException e) {
            super.onProcessFailed(e);
            System.out.println(e.getCause());

            System.out.println(e.getMessage());

            System.out.println("fail");
          }
        });
    executor.execute(commandLine, resultHandler);

    resultHandler.waitFor(2000);

    System.out.println("--> wait result is : " + resultHandler.hasResult());
    //        System.out.println("--> exit value is : " + resultHandler.getExitValue());
    //        System.out.println("--> exception is : " + resultHandler.getException());
    System.out.println("out " + out.toString());
    System.out.println("err " + err.toString());

    System.out.println("--> Watchdog should have killed the process : " + watchdog.killedProcess());
    //        System.out.println("--> Watchdog is watching ? " + watchdog.isWatching());
    //        System.out.println("--> wait result is : " + resultHandler.hasResult());
    ////        System.out.println("--> exception is : " + resultHandler.getException());
    ////        System.out.println("--> exit value is : " + resultHandler.getExitValue());
    //        watchdog.destroyProcess();//终止进程
    //        System.out.println("--> destroyProcess done.");
    //        System.out.println("--> Watchdog is watching ? " + watchdog.isWatching());
    //        System.out.println("--> Watchdog should have killed the process : " +
    // watchdog.killedProcess());
    //        System.out.println("--> wait result is : " + resultHandler.hasResult());
    //        System.out.println("--> exit value is : " + resultHandler.getExitValue());
    //        System.out.println("--> exception is : " + resultHandler.getException());

  }
}
