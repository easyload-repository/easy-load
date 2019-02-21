package com.oocl.easyload.node.extractor;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class LrsExtractor {
  private static final String TEST_CHIEF = "TestChief";
  private static final String GROUP_CHIEF = "GroupChief";
  private static final String LOC_ASSIGNED_TO_GROUP = "LocAssignedToGroup";
  private static final String LOC_ASSIGNED_TO_TEST = "LocAssignedToTest";
  private static final String GROUPS = "Groups";
  private static final String PATH = "Path";
  private static final String EXCEPTION = "Exception";
  private static final String[] EXTENSIONS = {"usr"};
  private static final String BACKSLASH = "\\";
  private static final String LEFT_BRACE = "{";
  private static final String RIGHT_BRACE = "}";

  public Map<String, String> extractLrsString(String origin, String path) {
    Map<String, String> res = new HashMap<>();

    res.put(TEST_CHIEF, this.extractBraceContent(origin, TEST_CHIEF));
    res.put(GROUP_CHIEF, this.extractBraceContent(origin, GROUP_CHIEF));
    res.put(LOC_ASSIGNED_TO_GROUP, this.extractDirectContent(origin, LOC_ASSIGNED_TO_GROUP));
    res.put(LOC_ASSIGNED_TO_TEST, this.extractDirectContent(origin, LOC_ASSIGNED_TO_TEST));
    res.put(
        GROUPS, this.extractAngleBracketContent(origin, "<GroupScheduler>", "</GroupScheduler>"));

    this.replaceTestChiefAbsolutePath(res, path);
    return res;
  }

  private String extractBraceContent(String origin, String keyWord) {
    int targetIndex = origin.indexOf(keyWord);
    int targetLeftBraceIndex = origin.substring(0, targetIndex).lastIndexOf(LEFT_BRACE);
    int targetRightBraceIndex = targetLeftBraceIndex;
    int flag = 1;
    targetRightBraceIndex = getWantedEnd(origin, targetIndex, targetRightBraceIndex, flag);

    String targetStr = origin.substring(targetLeftBraceIndex + 1, targetRightBraceIndex);
    int firstLeftBraceIndex = targetStr.indexOf(LEFT_BRACE);
    int lastRightBraceIndex = targetStr.lastIndexOf(RIGHT_BRACE);

    targetStr = targetStr.substring(firstLeftBraceIndex, lastRightBraceIndex + 1);
    return targetStr;
  }

  private String extractDirectContent(String origin, String keyWord) {
    int keyWordIndex = origin.indexOf(keyWord);
    int wantedStart = keyWordIndex + keyWord.length(), wantedEnd = wantedStart;
    int flag = 1;
    wantedEnd = getWantedEnd(origin, wantedStart, wantedEnd, flag);
    return origin.substring(wantedStart, wantedEnd - 1);
  }

  private int getWantedEnd(String origin, int wantedStart, int wantedEnd, int flag) {
    for (int i = wantedStart + 1; i < origin.length(); i++) {
      if ('{' == origin.charAt(i)) {
        flag++;
      } else if ('}' == origin.charAt(i)) {
        flag--;
      }
      if (flag == 0) {
        wantedEnd = i;
        break;
      }
    }
    return wantedEnd;
  }

  private String extractAngleBracketContent(String origin, String startKeyWord, String endKeyWord) {
    int wantedStart = origin.indexOf(startKeyWord);
    int wantedEnd = origin.lastIndexOf(endKeyWord) + endKeyWord.length();
    return origin.substring(wantedStart, wantedEnd);
  }

  private void replaceTestChiefAbsolutePath(Map<String, String> lrs, String path) {
    List<String> testChiefs = this.findBraceClosure(lrs.get(TEST_CHIEF));
    List<String> after = new ArrayList<>();
    List<String> fileNotFoundLists = new ArrayList<>();

    Collection<File> usrFiles = FileUtils.listFiles(new File(path), EXTENSIONS, true);

    for (String testChief : testChiefs) {
      String tmp = testChief.substring(testChief.indexOf(PATH));
      String oldPath = tmp.substring(5, tmp.indexOf('\n') - 1);
      int scriptNameStartIndex =
          oldPath.substring(0, oldPath.lastIndexOf(BACKSLASH)).lastIndexOf(BACKSLASH) + 1;
      String oldFileName = oldPath.substring(scriptNameStartIndex);

      boolean canFindUsrFile = false;
      for (File file : usrFiles) {
        if (file.getAbsolutePath().contains(oldFileName)) {
          after.add(testChief.replace(oldPath, file.getAbsolutePath()));
          canFindUsrFile = true;
          break;
        }
      }
      if (!canFindUsrFile) {
        fileNotFoundLists.add(oldFileName);
      }
    }

    if (!fileNotFoundLists.isEmpty())
      lrs.put(EXCEPTION, "Can't find script(s): " + String.join(", ", fileNotFoundLists));
    lrs.put(TEST_CHIEF, String.join("\n", after));
  }

  public List<String> findIncludingScriptPath(Map<String, String> lrs) {
    List<String> testChiefs = this.findBraceClosure(lrs.get(TEST_CHIEF));
    List<String> paths = new ArrayList<>();
    for (String testChief : testChiefs) {
      String tmp = testChief.substring(testChief.indexOf(PATH));
      paths.add(tmp.substring(5, tmp.indexOf('\n') - 1));
    }

    return paths;
  }

  private List<String> findBraceClosure(String whole) {
    List<String> closures = new ArrayList<>();
    int left = whole.indexOf(LEFT_BRACE), flag = 1;
    for (int i = left + 1; i < whole.length(); i++) {
      if (LEFT_BRACE.equals(whole.charAt(i) + "")) flag++;
      else if (RIGHT_BRACE.equals(whole.charAt(i) + "")) flag--;

      if (flag == 0) {
        closures.add(whole.substring(left, ++i));
        if (whole.indexOf(LEFT_BRACE, i) != -1) {
          left = whole.indexOf(LEFT_BRACE, i);
          i = left;
          flag++;
        } else break;
      }
    }
    return closures;
  }
}
