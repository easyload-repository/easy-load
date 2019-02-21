package com.oocl.easyload.node.command;

import org.apache.commons.exec.CommandLine;

/**
 * sharename=drive:path [/GRANT:user,[READ | CHANGE | FULL]] [/USERS:number | /UNLIMITED]
 * [/REMARK:"text"] [/CACHE:Manual | Documents| Programs | BranchCache | None] sharename
 * [/USERS:number | /UNLIMITED] [/REMARK:"text"] [/CACHE:Manual | Documents | Programs | BranchCache
 * | None] {sharename | devicename | drive:path} /DELETE sharename \\computername /DELETE
 */
public class NetShareCommandLine extends CommandLine {

  public NetShareCommandLine() {
    super("net");
    addArgument("share");
  }

  public NetShareCommandLine share(String shareName, String drivePath) {
    addArgument(shareName + "=" + drivePath);
    return this;
  }

  public NetShareCommandLine maxUsers(int number) {
    addArgument("/user:" + number);
    return this;
  }

  public NetShareCommandLine grantEveryoneFullPermissions() {
    return grantPermissions("everyone", "full");
  }

  public NetShareCommandLine grantPermissions(String user, String permission) {
    addArgument("/grant:" + user + "," + permission);
    return this;
  }

  public static NetShareCommandLine newInstance() {
    return new NetShareCommandLine();
  }
}
