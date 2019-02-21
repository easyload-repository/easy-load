package com.oocl.easyload.server.client.mapper;

import com.oocl.easyload.model.entity.ELScript;
import com.oocl.easyload.server.client.dto.ScriptDTO;

public class ELScriptMapper {
  public static ELScript toEntity(ScriptDTO dto) {
    ELScript elScript = new ELScript();
    elScript.setName(dto.getScriptName());
    return elScript;
  }
}
