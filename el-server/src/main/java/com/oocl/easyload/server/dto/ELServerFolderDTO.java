package com.oocl.easyload.server.dto;

import java.util.ArrayList;
import java.util.List;

public class ELServerFolderDTO {
  private String folderId;
  /** 用户应该拷贝script到的sharefolder路径 */
  private String path;

  private List<ELScriptDTO> elScripts = new ArrayList<>();

  public String getFolderId() {
    return folderId;
  }

  public void setFolderId(String folderId) {
    this.folderId = folderId;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public List<ELScriptDTO> getElScripts() {
    return elScripts;
  }

  public void setElScripts(List<ELScriptDTO> elScripts) {
    this.elScripts = elScripts;
  }
}
