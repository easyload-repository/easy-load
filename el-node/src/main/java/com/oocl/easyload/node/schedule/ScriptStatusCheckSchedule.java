package com.oocl.easyload.node.schedule;

import com.oocl.easyload.node.command.CommandExecutor;
import com.oocl.easyload.node.constant.MQConst;
import com.oocl.easyload.node.dto.NoticeDto;
import com.oocl.easyload.node.dto.NoticeStatus;
import com.oocl.easyload.node.dto.ScriptType;
import com.oocl.easyload.node.jms.Producer;
import com.oocl.easyload.node.pojo.ScriptDetail;
import com.oocl.easyload.node.util.CommandCheckUtil;
import com.oocl.easyload.node.util.MapUtil;
import com.oocl.easyload.node.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class ScriptStatusCheckSchedule {

  @Autowired Producer producer;

  @Scheduled(fixedDelay = 5 * 1000)
  public void scanIfActive() {
    Map<String, ExecuteWatchdog> watchdogs = CommandExecutor.WATCHDOG_MAP;
    Map<String, ScriptDetail> details = CommandExecutor.DETAIL_MAP;
    if (MapUtil.isEmpty(watchdogs)) {
      return;
    }
    for (Map.Entry<String, ExecuteWatchdog> entry : watchdogs.entrySet()) {
      String scriptId = entry.getKey();
      ScriptDetail scriptDetail = details.get(scriptId);
      ExecuteWatchdog watchdog = entry.getValue();
      if (watchdog.isWatching() || !watchdogs.containsKey(scriptId)) { // Running
        continue;
      }

      if (ScriptType.LOADRUNNER.getValue().equals(scriptDetail.getScriptType())) {
        String scriptFileAbsolutePath =
            CommandCheckUtil.extractFilePath(scriptDetail.getScriptCommand());
        String outputFileAbsolutePath =
            scriptFileAbsolutePath.substring(0, scriptFileAbsolutePath.lastIndexOf("\\") + 1)
                + "output.txt";
        File outputFile = new File(outputFileAbsolutePath);
        String parentId = scriptId.substring(0, scriptId.lastIndexOf("-"));
        if (!outputFile.exists()) {
          if (!this.checkIfThereIsOtherUsrs(watchdogs, scriptId, parentId)) {
            this.noticeComplete(parentId);
          }
        } else {
          try {
            String outputText = FileUtils.readFileToString(outputFile);
            if (outputText.contains(" Error")) {
              this.noticeError(
                  parentId,
                  "There is error when running "
                      + scriptDetail.getScriptName()
                      + ", please check!");
              this.removeOtherUsrs(watchdogs, scriptId, parentId);
            } else {
              if (!this.checkIfThereIsOtherUsrs(watchdogs, scriptId, parentId)) {
                this.noticeComplete(parentId);
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } else {
        String errorInfo = CommandExecutor.ERROR_STREAM_MAP.get(scriptId).toString();
        if (StringUtil.isBlank(errorInfo)) {
          noticeComplete(scriptId);
        } else {
          noticeError(scriptId, errorInfo);
        }
      }
      releaseResource(scriptId);
    }
  }

  private void releaseResource(String scriptId) {
    CommandExecutor.WATCHDOG_MAP.remove(scriptId);
    CommandExecutor.OUTPUT_STREAM_MAP.remove(scriptId);
    CommandExecutor.ERROR_STREAM_MAP.remove(scriptId);
    CommandExecutor.DETAIL_MAP.remove(scriptId);
  }

  private void noticeComplete(String scriptId) {
    log.info("Notice Complete -> [ScriptId]: {}, [Status]: {}", scriptId, NoticeStatus.COMPLETE);
    NoticeDto dto = new NoticeDto();
    dto.setScriptId(scriptId);
    dto.setScriptStatus(NoticeStatus.COMPLETE);
    producer.sendJsonWhenEnumUsingToString(MQConst.Queue.EL_NODE_NOTICE_QUEUE, dto);
  }

  private void noticeError(String scriptId, String errorInfo) {
    log.info(
        "Notice Error -> [ScriptId]: {}, [Status]: {}, [Error Info]: {}",
        scriptId,
        NoticeStatus.ERROR,
        errorInfo);
    NoticeDto dto = new NoticeDto();
    dto.setScriptId(scriptId);
    dto.setScriptStatus(NoticeStatus.ERROR);
    dto.setErrorInfo(errorInfo);
    producer.sendJsonWhenEnumUsingToString(MQConst.Queue.EL_NODE_NOTICE_QUEUE, dto);
  }

  private boolean checkIfThereIsOtherUsrs(
      Map<String, ExecuteWatchdog> watchDogs, String myId, String parentId) {
    for (Map.Entry<String, ExecuteWatchdog> watchDog : watchDogs.entrySet()) {
      if (!myId.equals(watchDog.getKey()) && watchDog.getKey().contains(parentId)) {
        return true;
      }
    }
    return false;
  }

  private void removeOtherUsrs(
      Map<String, ExecuteWatchdog> watchDogs, String myId, String parentId) {
    for (Map.Entry<String, ExecuteWatchdog> watchDog : watchDogs.entrySet()) {
      if (!myId.equals(watchDog.getKey()) && watchDog.getKey().contains(parentId)) {
        this.releaseResource(watchDog.getKey());
      }
    }
  }
}
