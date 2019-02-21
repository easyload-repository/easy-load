package com.oocl.easyload.node.controller;

import com.oocl.easyload.node.facade.FolderFacade;
import com.oocl.easyload.node.response.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/folder")
public class FolderController {
  @Autowired FolderFacade folderFacade;

  @PostMapping("/initial")
  public ResponseEntity initialFolders(
      @RequestBody @ApiParam(name = "domainNames") List<String> domainNames) {
    boolean result = folderFacade.initialFolders(domainNames);
    return ResponseUtil.successInRestful(result);
  }

  //    @GetMapping("/shareRootFolder")
  //    public ResponseEntity shareAllFolder(@RequestParam("activityName")
  //                                             @ApiParam(name = "activityName", value =
  // "/shareAll/{activityName}", required = true)
  //                                                     String activityName) {
  //        String result = folderFacade.shareRootFolder();
  //        return ResponseUtil.successInRestful(result);
  //    }
}
