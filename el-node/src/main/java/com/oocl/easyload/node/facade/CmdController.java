package com.oocl.easyload.node.facade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CmdController {
  String prefixLine = "cmd.exe /c ";

  public void runCmd(String cmdLine) throws IOException {
    Runtime rt = Runtime.getRuntime();
    Process process = rt.exec(prefixLine + cmdLine);

    InputStream in = process.getInputStream();
    InputStreamReader isReader = new InputStreamReader(in);
    BufferedReader iReader = new BufferedReader(isReader);

    System.out.println(iReader.readLine());

    iReader.close();
    in.close();
    process.destroy();
  }
}
