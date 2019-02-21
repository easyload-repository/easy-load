package com.oocl.easyload.server.controller;

import com.oocl.easyload.model.entity.ELScript;
import com.oocl.easyload.model.entity.ELServerFolder;
import com.oocl.easyload.server.dto.ELScriptDTO;
import com.oocl.easyload.server.dto.ELServerFolderDTO;
import com.oocl.easyload.server.mapper.ELScriptMapper;
import com.oocl.easyload.server.mapper.ELServerFolderMapper;
import com.oocl.easyload.server.service.ELServerFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/server-folder")
public class ELServerFolderController {
  private ELServerFolderService serverFolderService;

  @GetMapping(path = "/search")
  public ELServerFolderDTO getELServerFolder(@RequestParam String attenderId) {
    ELServerFolder elServerFolder = serverFolderService.getOrInitFolder(attenderId);
    ELServerFolderDTO dto = ELServerFolderMapper.getInstance().toDTO(elServerFolder);
    for (ELScript elScript : elServerFolder.getElScripts()) {
      dto.getElScripts().add(ELScriptMapper.getInstance().toDTO(elScript));
    }
    return dto;
  }

  @GetMapping(path = "/{serverFolderId}/script")
  public List<ELScriptDTO> reScanScript(@PathVariable String serverFolderId) {
    List<ELScript> elScripts = serverFolderService.getAndPersistElScript(serverFolderId);
    List<ELScriptDTO> dtos =
        elScripts.stream()
            .map(elScript -> ELScriptMapper.getInstance().toDTO(elScript))
            .collect(Collectors.toList());
    return dtos;
  }

  @Autowired
  public void setServerFolderService(ELServerFolderService serverFolderService) {
    this.serverFolderService = serverFolderService;
  }
}
