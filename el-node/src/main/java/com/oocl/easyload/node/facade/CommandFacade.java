package com.oocl.easyload.node.facade;

import com.oocl.easyload.node.assembler.LrsAssembler;
import com.oocl.easyload.node.command.CommandExecutor;
import com.oocl.easyload.node.dto.ScriptDto;
import com.oocl.easyload.node.dto.ScriptStatus;
import com.oocl.easyload.node.dto.ScriptType;
import com.oocl.easyload.node.extractor.LrsExtractor;
import com.oocl.easyload.node.pojo.ScriptDetail;
import com.oocl.easyload.node.pojo.ScriptRootPath;
import com.oocl.easyload.node.response.ResponseUtil;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommandFacade {

  private static final String BACKSLASH = "\\";
  private static final String RUNNING_LOAD_RUNNER_PROJECT_ID = "Runner_Load_Runner";
  private static final String EXCEPTION = "Exception";
  private static final Logger logger = LoggerFactory.getLogger(CommandFacade.class);
  @Autowired
  ScriptRootPath scriptRootPath;
  @Autowired
  LrsAssembler lrsAssembler;

  public ResponseEntity executeCommand(List<ScriptDto> dtoList, int duration) {

    logger.info("CommandFacade executeCommand()");

    for (ScriptDto dto : dtoList) {
      if (dto == null) {
        continue;
      }
      String scriptId = dto.getScriptId();
      ScriptDetail detail = ScriptDetail.newInstance(dto);
      ExecuteWatchdog watchdog = CommandExecutor.WATCHDOG_MAP.get(scriptId);
      try {
        if (watchdog != null && watchdog.isWatching()) {
          dto.setScriptStatus(ScriptStatus.RUNNING);
        } else {

          boolean isProcessAlive = CommandExecutor.execute(detail, duration);
          dto.setScriptStatus(isProcessAlive ? ScriptStatus.RunSuccess : ScriptStatus.RunFail);
        }
        dto.setOutputLog(CommandExecutor.OUTPUT_STREAM_MAP.get(scriptId).toString());
        dto.setErrorLog(CommandExecutor.ERROR_STREAM_MAP.get(scriptId).toString());
      } catch (Exception e) {
        logger.error(
            "CommandFacade executeCommand() scriptId [{}]: {}", dto.getScriptId(), e.getMessage());
        dto.setScriptStatus(ScriptStatus.RunFail);
        dto.setExceptionLog(e.getMessage());
      }
    }
    return ResponseUtil.success(dtoList);
  }

  public ResponseEntity destroyCommand(List<ScriptDto> dtoList) {

    logger.info("CommandFacade destroyCommand()");

    for (ScriptDto dto : dtoList) {
      String scriptId = dto.getScriptId();
      ExecuteWatchdog watchdog = CommandExecutor.WATCHDOG_MAP.get(scriptId);
      try {
        if (watchdog == null) {
          dto.setScriptStatus(ScriptStatus.DESTROYED);
        } else {
          if (!watchdog.isWatching()) {
            dto.setScriptStatus(ScriptStatus.DESTROYED);
          } else {
            watchdog.destroyProcess();
            CommandExecutor.WATCHDOG_MAP.remove(scriptId);
            CommandExecutor.OUTPUT_STREAM_MAP.remove(scriptId);
            CommandExecutor.ERROR_STREAM_MAP.remove(scriptId);
            dto.setScriptStatus(ScriptStatus.DestroySuccess);

            //                        FeedbackManager manager =
            // SpringUtil.getBean(FeedbackManager.class, "feedbackManager");
            //                        manager.popAndSendAllMessage(scriptId);
          }
        }
      } catch (Exception e) {
        logger.error(
            "CommandFacade destroyCommand() scriptId [{}]: {}", dto.getScriptId(), e.getMessage());
        dto.setScriptStatus(ScriptStatus.DestroyFail);
        dto.setExceptionLog(e.getMessage());
      }
    }
    return ResponseUtil.success(dtoList);
  }

  public ResponseEntity clearAll(String token) {

    logger.info("CommandFacade clearAll()");

    if (token.equals("7")) {
      //            FeedbackManager manager = SpringUtil.getBean(FeedbackManager.class,
      // "feedbackManager");
      for (Map.Entry<String, ExecuteWatchdog> entry : CommandExecutor.WATCHDOG_MAP.entrySet()) {
        //                String scriptId = entry.getKey();
        ExecuteWatchdog watchdog = entry.getValue();
        //                manager.popAndSendAllMessage(scriptId);
        watchdog.destroyProcess();
      }
      CommandExecutor.WATCHDOG_MAP.clear();
      CommandExecutor.OUTPUT_STREAM_MAP.clear();
      CommandExecutor.ERROR_STREAM_MAP.clear();

      return ResponseUtil.success(null);
    }
    return ResponseUtil.fail("token not right");
  }

  public ResponseEntity getFeedback(List<ScriptDto> dtoList) {

    logger.info("CommandFacade getFeedback()");

    for (ScriptDto dto : dtoList) {
      ExecuteWatchdog watchdog = CommandExecutor.WATCHDOG_MAP.get(dto.getScriptId());
      String scriptId = dto.getScriptId();
      OutputStream out = CommandExecutor.OUTPUT_STREAM_MAP.get(scriptId);
      OutputStream err = CommandExecutor.ERROR_STREAM_MAP.get(scriptId);
      if (out != null) {
        dto.setOutputLog(out.toString());
      }
      if (err != null) {
        dto.setErrorLog(err.toString());
      }
      if (watchdog != null && watchdog.isWatching()) {
        dto.setScriptStatus(ScriptStatus.RUNNING);
      } else {
        dto.setScriptStatus(ScriptStatus.DESTROYED);
      }
    }
    return ResponseUtil.success(dtoList);
  }

  public ResponseEntity getAllScripts() {

    logger.info("CommandFacade getAllScripts()");

    List<ScriptDto> responseDtoList = new ArrayList<>();

    for (Map.Entry<String, ExecuteWatchdog> entry : CommandExecutor.WATCHDOG_MAP.entrySet()) {

      String scriptId = entry.getKey();

      ExecuteWatchdog watchdog = entry.getValue();

      if (scriptId != null) {
        ScriptDto dto = new ScriptDto();
        dto.setScriptId(scriptId);
        dto.setOutputLog(CommandExecutor.OUTPUT_STREAM_MAP.get(scriptId).toString());
        dto.setErrorLog(CommandExecutor.ERROR_STREAM_MAP.get(scriptId).toString());
        dto.setScriptStatus(watchdog.isWatching() ? ScriptStatus.RUNNING : ScriptStatus.DESTROYED);
        responseDtoList.add(dto);
      }
    }
    return ResponseUtil.success(responseDtoList);
  }

  public ResponseEntity trailRunLrScript(List<ScriptDto> dtoList) throws IOException {

    logger.info("CommandFacade trailRunLrScript()");

    LrsExtractor extractor = new LrsExtractor();
    List<ScriptDto> usrScripts = new ArrayList<>();

    for (ScriptDto dto : dtoList) {
      if (this.isLrsScriptRunning(dto.getScriptId())) {
        dto.setScriptStatus(ScriptStatus.RUNNING);
        continue;
      }

      String currPath =
          scriptRootPath.getRootPath()
              + BACKSLASH
              + dto.getDomainName()
              + BACKSLASH
              + ScriptType.LOADRUNNER.getValue()
              + BACKSLASH
              + dto.getScriptName();
      Map<String, String> lrs =
          extractor.extractLrsString(
              FileUtils.readFileToString(new File(currPath)),
              currPath.substring(0, currPath.lastIndexOf(BACKSLASH)));

      if (lrs.containsKey(EXCEPTION)) {
        return ResponseUtil.fail(lrs.get(EXCEPTION));
      }

      int i = 0;
      for (String scriptPath : extractor.findIncludingScriptPath(lrs)) {
        File currScript = new File(scriptPath);
        ScriptDto subDto = new ScriptDto();
        subDto.setScriptId(dto.getScriptId() + "-" + i++);
        subDto.setScriptName(currScript.getName());
        subDto.setDomainName(dto.getDomainName());
        subDto.setScriptType(ScriptType.LOADRUNNER.getValue());
        subDto.setScriptCommand("mdrv.exe -usr " + scriptPath);
        usrScripts.add(subDto);
      }
    }

    return this.executeCommand(usrScripts, 5000);
  }

  public ResponseEntity runLoadRunnerScripts(List<ScriptDto> dtoList, int duration)
      throws IOException {

    logger.info("CommandFacade runLoadRunnerScripts()");

    LrsExtractor extractor = new LrsExtractor();
    List<Map<String, String>> lrsInfos = new ArrayList<>();
    for (ScriptDto dto : dtoList) {
      if (dto == null) {
        continue;
      }
      if (this.isLrsScriptRunning(dto.getScriptId())) {
        dto.setScriptStatus(ScriptStatus.RUNNING);
        continue;
      }
      String dtoFilePath =
          scriptRootPath.getRootPath()
              + BACKSLASH
              + dto.getDomainName()
              + BACKSLASH
              + ScriptType.LOADRUNNER.getValue()
              + BACKSLASH
              + dto.getScriptName();
      File dtoFile = new File(dtoFilePath);
      Map<String, String> lrs =
          extractor.extractLrsString(
              FileUtils.readFileToString(dtoFile),
              scriptRootPath.getRootPath() + BACKSLASH + dto.getDomainName());
      lrsInfos.add(lrs);
    }

    lrsAssembler.assembleNewLrs(lrsInfos);
    List<ScriptDto> finalDtoList = new ArrayList<>();
    ScriptDto dto = new ScriptDto();
    dto.setScriptId(RUNNING_LOAD_RUNNER_PROJECT_ID);
    dto.setScriptCommand(
        "wlrun -TestPath " + scriptRootPath.getRootPath() + BACKSLASH + "running.lrs -Run -Close");
    finalDtoList.add(dto);
    this.executeCommand(finalDtoList, duration);

    return ResponseUtil.success(dtoList);
  }

  public ResponseEntity stopLoadRunnerScripts() {

    logger.info("CommandFacade stopLoadRunnerScripts()");

    List<ScriptDto> dtos = new ArrayList<>();
    ScriptDto dto = new ScriptDto();
    dto.setScriptId(RUNNING_LOAD_RUNNER_PROJECT_ID);
    dtos.add(dto);
    return this.destroyCommand(dtos);
  }

  private boolean isLrsScriptRunning(String parentId) {
    for (Map.Entry<String, ExecuteWatchdog> watchDog : CommandExecutor.WATCHDOG_MAP.entrySet()) {
      if (watchDog.getKey().equals(parentId)) {
        return true;
      }
    }
    return false;
  }
}
