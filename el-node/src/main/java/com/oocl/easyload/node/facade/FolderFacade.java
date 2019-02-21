package com.oocl.easyload.node.facade;

import com.oocl.easyload.node.command.NetShareCommandLine;
import com.oocl.easyload.node.dto.ScriptDto;
import com.oocl.easyload.node.dto.ScriptType;
import com.oocl.easyload.node.pojo.ScriptRootPath;
import com.oocl.easyload.node.util.FileUtil;
import org.apache.commons.exec.DefaultExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FolderFacade {
  @Autowired
  ScriptRootPath scriptRootPath;
  private static final String BACKSLASH = "\\";
  private CmdController executor;

  public FolderFacade() {
    executor = new CmdController();
  }

  public boolean initialFolders(List<String> domainNames) {
    this.checkAndCreateFolder(scriptRootPath.getRootPath());
    for (String name : domainNames) {
      if (name.length() <= 0) continue;
      String domainRootPathName = scriptRootPath.getRootPath() + BACKSLASH + name;
      this.checkAndCreateFolder(domainRootPathName);
      this.shareFolder(domainRootPathName, name);
      for (ScriptType type : ScriptType.values()) {
        checkAndCreateFolder(domainRootPathName + BACKSLASH + type.getValue());
      }
    }
    return true;
  }

  public List<ScriptDto> getAllScripts() {
    List<ScriptDto> scripts = new ArrayList<>();
    String targetPath = scriptRootPath.getRootPath();
    File targetFolder = new File(targetPath);
    if (this.isDirectoryLegal(targetFolder)) {
      for (File domainFolder : targetFolder.listFiles()) {
        List<ScriptDto> tmpList = this.getScriptsNameByDomain(domainFolder.getName());
        scripts.addAll(tmpList);
      }
    }
    return scripts;
  }

  public List<ScriptDto> getScriptsNameByDomain(String domainName) {
    List<ScriptDto> scripts = new ArrayList<>();
    String targetPath = scriptRootPath.getRootPath() + BACKSLASH + domainName;
    File targetFolder = new File(targetPath);
    if (this.isDirectoryLegal(targetFolder)) {
      for (ScriptType type : ScriptType.values()) {
        File typeFolder = new File(targetPath + BACKSLASH + type.getValue());
        if (this.isDirectoryLegal(typeFolder)) {
          for (File file : typeFolder.listFiles()) {
            ScriptDto dto = new ScriptDto();
            switch (type) {
              case JAVA:
                if (file.getName().endsWith(".jar")) dto.setScriptType(type.getValue());
                break;
              case NODEJS:
                if (file.getName().endsWith(".js")) dto.setScriptType(type.getValue());
                break;
              case SOAPUI:
                if (file.getName().endsWith(".xml")) dto.setScriptType(type.getValue());
                break;
              case LOADRUNNER:
                if (file.getName().endsWith(".lrs")) dto.setScriptType(type.getValue());
                break;
            }
            if (dto.getScriptType() != null) {
              dto.setScriptName(file.getName());
              dto.setDomainName(domainName);
              scripts.add(dto);
            }
          }
        }
      }
    }
    //        String targetPath = scriptRootPath.getRootPath() + BACKSLASH + activityName +
    // BACKSLASH + domainName;
    //        File targetFolder = new File(targetPath);
    //        if (this.isDirectoryLegal(targetFolder)) {
    //            for (ScriptType type : ScriptType.values()) {
    //                File typeFolder = new File(targetPath + BACKSLASH + type);
    //                if (this.isDirectoryLegal(targetFolder)) {
    //                    for (File scriptFile : typeFolder.listFiles()) {
    //                        ScriptDto script = new ScriptDto();
    //                        script.setScriptName(scriptFile.getName());
    //                        script.setScriptType(type.getValue());
    //                        script.setDomainName(domainName);
    //                        scripts.add(script);
    //                    }
    //                }
    //            }
    //        }
    return scripts;
  }

  public String shareFolder(String folderPath, String folderName) {
    if (FileUtil.isShareFolder(folderName)) return "Shared Folder exists";
    NetShareCommandLine commandLine =
        NetShareCommandLine.newInstance()
            .share(folderName, folderPath)
            .maxUsers(20)
            .grantEveryoneFullPermissions();
    DefaultExecutor executor = new DefaultExecutor();
    try {
      executor.execute(commandLine);
    } catch (IOException e) {
      e.printStackTrace();
      return "Shared Failed";
    }
    return "Shared Successfully";
  }
  //    public String shareRootFolder(String activityName) {
  //        try {
  //            NetShareCommandLine commandLine = NetShareCommandLine.newInstance()
  //                    .share(activityName, scriptRootPath.getRootPath() + BACKSLASH +
  // activityName)
  //                    .maxUsers(20).grantEveryoneFullPermissions();
  //            DefaultExecutor executor = new DefaultExecutor();
  //            executor.execute(commandLine);
  //        } catch (IOException e) {
  //            e.printStackTrace();
  //            return "Meet Exception when sharing Folder";
  //        }
  //        return "Shared Folder successfully";
  //    }

  private void checkAndCreateFolder(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdir();
    }
  }

  private boolean isDirectoryLegal(File target) {
    return target.exists() && target.isDirectory();
  }
}
