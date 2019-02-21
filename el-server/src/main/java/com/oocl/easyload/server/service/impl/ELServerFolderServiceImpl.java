package com.oocl.easyload.server.service.impl;

import com.oocl.easyload.model.entity.ELScript;
import com.oocl.easyload.model.entity.ELServerFolder;
import com.oocl.easyload.server.client.NodeClient;
import com.oocl.easyload.server.exception.IdInvalidException;
import com.oocl.easyload.server.exception.ServerFolderHaveNotConfigException;
import com.oocl.easyload.server.repository.ELServerFolderRepository;
import com.oocl.easyload.server.service.ELServerFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ELServerFolderServiceImpl implements ELServerFolderService {

  private ELServerFolderRepository elServerFolderRepository;
  private NodeClient nodeClient;

  @Override
  public ELServerFolder getOrInitFolder(String attenderId) {
    ELServerFolder folder = elServerFolderRepository.findByElAttender_AttenderId(attenderId);
    // 业务异常
    if (folder == null) {
      throw new ServerFolderHaveNotConfigException();
    }
    if (!folder.getInitFolder()) {
      nodeClient.initialFolder(
          folder.getElServer().getHost(),
          folder.getElServer().getPort(),
          folder.getElAttender().getDomain());

      folder.setInitFolder(true);
      elServerFolderRepository.save(folder);
    }
    return folder;
  }

  @Override
  public List<ELScript> getAndPersistElScript(String serverFolderId) {
    Optional<ELServerFolder> serverFolderOptional =
        elServerFolderRepository.findById(serverFolderId);
    // 如果没有，则意味着客户端传入的ID不合理
    if (!serverFolderOptional.isPresent()) {
      throw new IdInvalidException();
    }
    ELServerFolder serverFolder = serverFolderOptional.get();
    // 访问Node， 从Node获取新的script
    List<ELScript> scripts =
        nodeClient.scanScript(
            serverFolder.getElServer().getHost(),
            serverFolder.getElServer().getPort(),
            serverFolder.getElAttender().getDomain());
    // serverFolder.clearElScripts();
    // 填充executeCmd等信息
    for (ELScript script : scripts) {
      script.setElServerFolder(serverFolder);
      String absolutePath =
          serverFolder.getElServer().getPath()
              + "/"
              + serverFolder.getElAttender().getDomain()
              + "/"
              + script.getType().getType()
              + "/"
              + script.getName();
      String executeTemplate = script.getType().getExecuteTemplate();
      if (executeTemplate != null) {
        script.setExecuteCmd(MessageFormat.format(executeTemplate, absolutePath));
      }
      String stopTemplate = script.getType().getStopTemplate();
      if (stopTemplate != null) {
        script.setStopCmd(MessageFormat.format(stopTemplate, absolutePath));
      }
      serverFolder.getElScripts().add(script);
    }
    elServerFolderRepository.save(serverFolder);
    return serverFolder.getElScripts();
  }

  @Autowired
  public void setElServerFolderRepository(ELServerFolderRepository elServerFolderRepository) {
    this.elServerFolderRepository = elServerFolderRepository;
  }

  @Autowired
  public void setNodeClient(NodeClient nodeClient) {
    this.nodeClient = nodeClient;
  }
}
