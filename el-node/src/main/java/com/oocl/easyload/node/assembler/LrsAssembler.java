package com.oocl.easyload.node.assembler;

import com.oocl.easyload.node.pojo.ScriptRootPath;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class LrsAssembler {
  @Autowired ScriptRootPath scriptRootPath;

  private static final String SAMPLE_PROJECT_NAME = "sample.lrs";
  private static final String RUNNING_PROJECT_NAME = "running.lrs";
  private static final String BACKSLASH = "\\";
  private static final String TEST_CHIEF = "TestChief";
  private static final String GROUP_CHIEF = "GroupChief";
  private static final String LOC_ASSIGNED_TO_GROUP = "LocAssignedToGroup";
  private static final String LOC_ASSIGNED_TO_TEST = "LocAssignedToTest";
  private static final String GROUPS = "Groups";

  public void assembleNewLrs(List<Map<String, String>> lrsInfos) throws IOException {
    File runningFile = this.cloneNewEmptyFile();
    String currStr = FileUtils.readFileToString(runningFile);
    StringBuilder dest = new StringBuilder();
    dest.append(currStr);
    this.assembleThem(dest, lrsInfos);

    FileUtils.writeStringToFile(runningFile, dest.toString());
  }

  public File cloneNewEmptyFile() throws IOException {
    File sampleFile = new File(scriptRootPath.getRootPath() + BACKSLASH + SAMPLE_PROJECT_NAME);
    File runningFile = new File(scriptRootPath.getRootPath() + BACKSLASH + RUNNING_PROJECT_NAME);
    FileUtils.copyFile(sampleFile, runningFile);
    return runningFile;
  }

  public void assembleThem(StringBuilder dest, List<Map<String, String>> lrsInfos) {
    int testChiefStart = dest.indexOf(TEST_CHIEF) + 9;
    // TestChief
    for (Map<String, String> lrs : lrsInfos) {
      if (lrs.containsKey(TEST_CHIEF)) {
        dest.insert(testChiefStart, "\n" + lrs.get(TEST_CHIEF));
      }
    }

    int groupChiefStart = dest.indexOf(GROUP_CHIEF) + 10;
    // GroupChief
    for (Map<String, String> lrs : lrsInfos) {
      if (lrs.containsKey(GROUP_CHIEF)) {
        dest.insert(groupChiefStart, "\n" + lrs.get(GROUP_CHIEF));
      }
    }

    int groupsStart = dest.indexOf("</InitAllBeforeStart>") + 21;
    for (Map<String, String> lrs : lrsInfos) {
      if (lrs.containsKey(GROUPS)) {
        dest.insert(groupsStart, "\n" + lrs.get(GROUPS));
      }
    }

    int locAssignedToGroupStart = dest.indexOf(LOC_ASSIGNED_TO_GROUP) + 18;
    for (Map<String, String> lrs : lrsInfos) {
      if (lrs.containsKey(LOC_ASSIGNED_TO_GROUP)) {
        dest.insert(locAssignedToGroupStart, "\n" + lrs.get(LOC_ASSIGNED_TO_GROUP));
      }
    }

    int locAssignedToTestStart = dest.indexOf(LOC_ASSIGNED_TO_TEST) + 17;
    for (Map<String, String> lrs : lrsInfos) {
      if (lrs.containsKey(LOC_ASSIGNED_TO_TEST)) {
        dest.insert(locAssignedToTestStart, "\n" + lrs.get(LOC_ASSIGNED_TO_TEST));
      }
    }
  }
}
