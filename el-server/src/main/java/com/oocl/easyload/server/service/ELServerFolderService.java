package com.oocl.easyload.server.service;

import com.oocl.easyload.model.entity.ELScript;
import com.oocl.easyload.model.entity.ELServerFolder;

import java.util.List;

public interface ELServerFolderService {
  /**
   * 获取该Attender的ElServerFolder信息。若Node还没有初始化，则让Node去进行初始化r
   *
   * @param attenderId
   * @return Attender唯一对应的ServerFolders
   */
  ELServerFolder getOrInitFolder(String attenderId);

  /**
   * 从Node获取serverFolder当前所拥有的script，并且存到DB。
   *
   * @param serverFolderId 对应的serverFolderId
   * @return
   */
  List<ELScript> getAndPersistElScript(String serverFolderId);
}
