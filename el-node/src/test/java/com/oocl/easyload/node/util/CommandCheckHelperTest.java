package com.oocl.easyload.node.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandCheckHelperTest {

  @Test
  public void isAcceptableCommand() {
    String java_command_drive_path = "java -jar C:\\EasyLoad.jar";
    assertTrue(CommandCheckUtil.isAcceptableCommand(java_command_drive_path));
    String java_command_drive_and_path =
        "java -jar C:\\Users\\LIANGGA3\\Desktop\\EasyLoad\\EasyLoad.jar";
    assertTrue(CommandCheckUtil.isAcceptableCommand(java_command_drive_and_path));
    String java_command_network_machine = "java -jar \\\\LIANGGA3-W10\\EasyLoad.jar";
    assertTrue(CommandCheckUtil.isAcceptableCommand(java_command_network_machine));
    String java_command_network_machine_and_path =
        "java -jar \\\\LIANGGA3-W10\\EasyLoad\\EasyLoad.jar";
    assertTrue(CommandCheckUtil.isAcceptableCommand(java_command_network_machine_and_path));
    String java_command_network_special_machine =
        "java -jar \\\\LIANGGA3-W10-2\\EasyLoad\\EasyLoad.jar";
    assertTrue(CommandCheckUtil.isAcceptableCommand(java_command_network_special_machine));
  }

  @Test
  public void extractFilePath() {
    String java_command_drive_path = "java -jar C:\\EasyLoad.jar";
    assertEquals("C:\\EasyLoad.jar", CommandCheckUtil.extractFilePath(java_command_drive_path));
    String java_command_drive_and_path =
        "java -jar C:\\Users\\LIANGGA3\\Desktop\\EasyLoad\\EasyLoad.jar";
    assertEquals(
        "C:\\Users\\LIANGGA3\\Desktop\\EasyLoad\\EasyLoad.jar",
        CommandCheckUtil.extractFilePath(java_command_drive_and_path));
    String java_command_network_machine = "java -jar \\\\LIANGGA3-W10\\EasyLoad.jar";
    assertEquals(
        "\\\\LIANGGA3-W10\\EasyLoad.jar",
        CommandCheckUtil.extractFilePath(java_command_network_machine));
    String java_command_network_machine_and_path =
        "java -jar \\\\LIANGGA3-W10\\EasyLoad\\EasyLoad.jar";
    assertEquals(
        "\\\\LIANGGA3-W10\\EasyLoad\\EasyLoad.jar",
        CommandCheckUtil.extractFilePath(java_command_network_machine_and_path));
    String java_command_network_special_machine =
        "java -jar \\\\LIANGGA3-W10-2\\EasyLoad\\EasyLoad.jar";
    assertEquals(
        "\\\\LIANGGA3-W10-2\\EasyLoad\\EasyLoad.jar",
        CommandCheckUtil.extractFilePath(java_command_network_special_machine));

    String usr_command =
        "mdrv.exe -usr D:\\loadtest\\SHP\\LoadRunner\\BKG_MERGE_JS\\BKG_MERGE(JS).usr";
    assertEquals(
        "D:\\loadtest\\SHP\\LoadRunner\\BKG_MERGE_JS\\BKG_MERGE(JS).usr",
        CommandCheckUtil.extractFilePath(usr_command));
  }
}
