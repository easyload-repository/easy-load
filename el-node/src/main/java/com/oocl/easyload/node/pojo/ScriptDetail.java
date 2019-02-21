package com.oocl.easyload.node.pojo;

import com.oocl.easyload.node.dto.ScriptDto;
import com.oocl.easyload.node.util.CommandCheckUtil;
import com.oocl.easyload.node.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptDetail {

  private String scriptId;
  private String scriptCommand;
  private String scriptName;
  private String scriptType;
  private String domainName;

  public static ScriptDetail newInstance(ScriptDto dto) {
    ScriptDetail po = new ScriptDetail();
    po.setScriptId(dto.getScriptId());
    po.setScriptCommand(dto.getScriptCommand());
    po.setScriptName(dto.getScriptName());
    if (StringUtil.isNotBlank(dto.getScriptType())) {
      po.setScriptType(dto.getScriptType());
    } else {
      String command = dto.getScriptCommand();
      String type = CommandCheckUtil.getType(command).getValue();
      po.setScriptType(type);
    }
    po.setDomainName(dto.getDomainName());
    return po;
  }
}
