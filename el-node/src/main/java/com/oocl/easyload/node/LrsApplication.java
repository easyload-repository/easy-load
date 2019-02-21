package com.oocl.easyload.node;

import com.oocl.easyload.node.assembler.LrsAssembler;
import com.oocl.easyload.node.extractor.LrsExtractor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LrsApplication {
  public static void main(String[] args) throws Exception {
    LrsAssembler assembler = new LrsAssembler();
    LrsExtractor extractor = new LrsExtractor();

    List lrsInfos = new ArrayList();
    List<String> toRunList = new ArrayList<String>();
    String targetLrsAbsolutePath1 = "D:\\loadtest\\baidu\\LoadRunner\\4gods.lrs";
    String targetLrsAbsolutePath2 = "D:\\loadtest\\iris\\LoadRunner\\irishome3.lrs";
    toRunList.add(targetLrsAbsolutePath1);
    toRunList.add(targetLrsAbsolutePath2);

    for (String currPath : toRunList) {
      Map<String, String> lrs =
          extractor.extractLrsString(
              FileUtils.readFileToString(new File(currPath)),
              currPath.substring(0, currPath.lastIndexOf('\\')));
      lrsInfos.add(lrs);
    }
    //
    assembler.assembleNewLrs(lrsInfos);

    //        CommandExecutor.execute("wlrun -TestPath D:\\loadtest\\running.lrs -Run -DonClose",
    // "122");
  }
}
