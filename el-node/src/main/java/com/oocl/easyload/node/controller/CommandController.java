package com.oocl.easyload.node.controller;

import com.oocl.easyload.node.dto.ScriptDto;
import com.oocl.easyload.node.facade.CommandFacade;
import com.oocl.easyload.node.response.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/node")
public class CommandController {

  private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

  @Autowired private CommandFacade commandFacade;

  @PostMapping(value = "/command/execute", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity executeCommand(
      @RequestBody
          @ApiParam(
              name = "dtoList",
              value =
                  "[{  \"scriptId\": \"21\", \"scriptName\":\"dd.jar\", \"scriptCommand\": \"java -jar dd.jar\", \"scriptType\":\"Java\", \"domainName\":\"bcr\" }]",
              required = true)
          List<ScriptDto> dtoList,
      @RequestParam @ApiParam(name = "duration", value = "60 ( second(s) )") int duration) {

    return commandFacade.executeCommand(dtoList, duration);
  }

  @PostMapping(value = "/command/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity destroyCommand(
      @RequestBody @ApiParam(name = "dtoList", value = "[{ \"scriptId\": \"21\"}]", required = true)
          List<ScriptDto> dtoList) {

    return commandFacade.destroyCommand(dtoList);
  }

  @GetMapping(value = "/command/all/kill/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity clearAll(@PathVariable("token") String token) {

    return commandFacade.clearAll(token);
  }

  @PostMapping(value = "/command/feedback", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getFeedback(
      @RequestBody @ApiParam(name = "dtoList", value = "[{ \"scriptId\": \"21\"}]", required = true)
          List<ScriptDto> dtoList) {

    return commandFacade.getFeedback(dtoList);
  }

  @GetMapping(value = "/command/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getAllScripts() {

    return commandFacade.getAllScripts();
  }

  @PostMapping(value = "/command/lr/trail", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity trailRunLoadRunnerScripts(
      @RequestBody
          @ApiParam(
              name = "dtoList",
              value =
                  "[{\"scriptId\":\"1225\",\"scriptName\":\"Scenario1.lrs\",\"domainName\":\"bcr\"}]",
              required = true)
          List<ScriptDto> dtoList) {
    try {
      return commandFacade.trailRunLrScript(dtoList);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return ResponseUtil.fail("Trail Run Failed");
  }

  @PostMapping(value = "/command/lr/run", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity runLoadRunnerScripts(
      @RequestBody
          @ApiParam(
              name = "dtoList",
              value =
                  "[{\"scriptId\":\"1225\",\"scriptName\":\"Scenario1.lrs\",\"domainName\":\"bcr\"}]",
              required = true)
          List<ScriptDto> dtoList,
      @RequestParam @ApiParam(name = "duration", value = "60 ( second(s) )") int duration)
      throws IOException {
    if (dtoList.isEmpty()) {
      return ResponseUtil.fail("Empty DTO list");
    }
    return commandFacade.runLoadRunnerScripts(dtoList, duration);
  }

  @GetMapping(value = "/common/lr/stop", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity stopLoadRunnerScripts() {
    return commandFacade.stopLoadRunnerScripts();
  }
}
