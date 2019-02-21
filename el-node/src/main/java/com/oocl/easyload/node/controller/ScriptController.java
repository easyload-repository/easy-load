package com.oocl.easyload.node.controller;

import com.oocl.easyload.node.dto.ScriptDto;
import com.oocl.easyload.node.facade.FolderFacade;
import com.oocl.easyload.node.response.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/script")
public class ScriptController {
  @Autowired FolderFacade folderFacade;

  @GetMapping("/all")
  public ResponseEntity getScriptByActivity() {
    List<ScriptDto> result = folderFacade.getAllScripts();
    return ResponseUtil.successInRestful(result);
  }

  @GetMapping("/domain/{domainName}")
  public ResponseEntity getScriptByActivityAndDomain(@PathVariable String domainName) {
    List<ScriptDto> result = folderFacade.getScriptsNameByDomain(domainName);
    return ResponseUtil.successInRestful(result);
  }
}
