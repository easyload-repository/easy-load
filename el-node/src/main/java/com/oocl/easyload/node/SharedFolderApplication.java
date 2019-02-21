package com.oocl.easyload.node;

import com.oocl.easyload.node.dto.ScriptDto;
import com.oocl.easyload.node.facade.FolderFacade;

import java.io.IOException;
import java.util.List;

public class SharedFolderApplication {
  public static void main(String[] args) throws IOException, InterruptedException {
    FolderFacade commander = new FolderFacade();
    String activityName = "kkk";
    //        List<String> names = new ArrayList<>();
    //        names.add("sa");
    //        names.add("vid");
    //        names.add("tnm");
    //        names.add("pcm");
    //        facade.initialFolders(activityName,names);
    //        facade.shareRootFolder(activityName);

    List<ScriptDto> scripts = commander.getScriptsNameByDomain("sa");

    for (ScriptDto script : scripts) {
      System.out.println(script.getScriptName() + "====" + script.getScriptType());
    }
  }
}
