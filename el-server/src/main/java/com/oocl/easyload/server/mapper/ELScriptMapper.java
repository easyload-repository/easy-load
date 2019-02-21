package com.oocl.easyload.server.mapper;

import com.oocl.easyload.model.entity.ELScript;
import com.oocl.easyload.server.dto.ELScriptDTO;

public class ELScriptMapper {
  private static ELScriptMapper mapper = new ELScriptMapper();

  public ELScriptDTO toDTO(ELScript elScript) {
    ELScriptDTO dto = new ELScriptDTO();
    dto.setScriptId(elScript.getScriptId());
    dto.setName(elScript.getName());
    dto.setType(elScript.getType() != null ? elScript.getType().getType() : null);
    dto.setLastExecute(elScript.getLastExecute());
    return dto;
  }

  public static ELScriptMapper getInstance() {
    return mapper;
  }
}
