package com.oocl.easyload.server.mapper;

import com.oocl.easyload.model.entity.ELServerFolder;
import com.oocl.easyload.server.dto.ELServerFolderDTO;

public class ELServerFolderMapper {
  private static ELServerFolderMapper instance = new ELServerFolderMapper();

  public ELServerFolderDTO toDTO(ELServerFolder elServerFolder) {
    ELServerFolderDTO dto = new ELServerFolderDTO();
    dto.setFolderId(elServerFolder.getFolderId());
    dto.setPath(
        "\\\\"
            + elServerFolder.getElServer().getHost()
            + "\\"
            + elServerFolder.getElAttender().getDomain());
    return dto;
  }

  private ELServerFolderMapper() {}

  public static ELServerFolderMapper getInstance() {
    return instance;
  }
}
