package com.oocl.easyload.node.util;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandCheckUtil {

  private CommandCheckUtil() {}

  public static boolean isAcceptableCommand(String command) {
    return getPattern(command) != null;
  }

  public static String extractFilePath(String command) {
    Pattern pattern = getPattern(command);
    if (pattern == null) {
      return StringUtil.EMPTY;
    }
    Matcher matcher = pattern.matcher(command);
    return matcher.matches() ? matcher.group(1) : StringUtil.EMPTY;
  }

  public static Pattern getPattern(String command) {
    if (StringUtil.isBlank(command)) {
      return null;
    }
    if (RegexPattern.JAVA_PATTERN.matcher(command).matches()) {
      return RegexPattern.JAVA_PATTERN;
    }
    if (RegexPattern.NODE_JS_PATTERN.matcher(command).matches()) {
      return RegexPattern.NODE_JS_PATTERN;
    }
    if (RegexPattern.SOAP_UI_PATTERN.matcher(command).matches()) {
      return RegexPattern.SOAP_UI_PATTERN;
    }
    if (RegexPattern.LOAD_RUNNER_CONTROLLER_PATTERN.matcher(command).matches()) {
      return RegexPattern.LOAD_RUNNER_CONTROLLER_PATTERN;
    }
    if (RegexPattern.LOAD_RUNNER_VUSER_GENERATOR_PATTERN.matcher(command).matches()) {
      return RegexPattern.LOAD_RUNNER_VUSER_GENERATOR_PATTERN;
    }
    return null;
  }

  public static ScriptType getType(String command) {
    if (StringUtil.isBlank(command)) {
      return ScriptType.OTHERS;
    }
    if (RegexPattern.JAVA_PATTERN.matcher(command).matches()) {
      return ScriptType.JAR;
    }
    if (RegexPattern.NODE_JS_PATTERN.matcher(command).matches()) {
      return ScriptType.JS;
    }
    if (RegexPattern.SOAP_UI_PATTERN.matcher(command).matches()) {
      return ScriptType.SOAP;
    }
    if (RegexPattern.LOAD_RUNNER_CONTROLLER_PATTERN.matcher(command).matches()) {
      return ScriptType.LRS;
    }
    if (RegexPattern.LOAD_RUNNER_VUSER_GENERATOR_PATTERN.matcher(command).matches()) {
      return ScriptType.USR;
    }
    return ScriptType.OTHERS;
  }

  @Getter
  public enum ScriptType {
    JAR("Java", "Java Jar"),
    JS("Node", "Node JS"),
    SOAP("Soap", "Soap UI LoadtestRunner"),
    USR("RoadRunner", "LoadRunner VUser Generator Project"),
    LRS("RoadRunner", "LoadRunner Controller Project"),
    OTHERS("Unknown", "Others");

    String value;
    String description;

    ScriptType(String value, String description) {
      this.value = value;
      this.description = description;
    }
  }

  private static class RegexPattern {

    public static final String JAVA_REGEX =
        "java -jar (((\\w+:)|(\\\\\\\\\\w+(-\\w+)*))\\\\(\\w+\\\\)*.*.jar)";
    public static final String NODE_JS_REGEX =
        "node (((\\w+:)|(\\\\\\\\\\w+(-\\w+)*))\\\\(\\w+\\\\)*.*.js)";
    public static final String SOAP_UI_REGEX =
        "loadtestrunner (((\\w+:)|(\\\\\\\\\\w+(-\\w+)*))\\\\(\\w+\\\\)*.*.xml)";
    public static final String LOAD_RUNNER_CONTROLLER_REGEX =
        "wlrun.exe -TestPath (((\\w+:)|(\\\\\\\\\\w+(-\\w+)*))\\\\(\\w+\\\\)*.*.lrs) -Run -Close";
    public static final String LOAD_RUNNER_VUSER_GENERATOR_REGEX =
        "mdrv.exe -usr (((\\w+:)|(\\\\\\\\\\w+(-\\w+)*))\\\\(\\w+\\\\)*.*.usr)";
    public static final String COMMON_REGEX =
        "((java -jar)|(node)|(loadtestrunner)) (((\\w+:)|(\\\\\\\\\\w+(-\\w+)*))\\\\(\\w+\\\\)*.*.((jar)|(js)|(xml)))";

    public static final Pattern JAVA_PATTERN = Pattern.compile(JAVA_REGEX);
    public static final Pattern NODE_JS_PATTERN = Pattern.compile(NODE_JS_REGEX);
    public static final Pattern SOAP_UI_PATTERN = Pattern.compile(SOAP_UI_REGEX);
    public static final Pattern LOAD_RUNNER_CONTROLLER_PATTERN =
        Pattern.compile(LOAD_RUNNER_CONTROLLER_REGEX);
    public static final Pattern LOAD_RUNNER_VUSER_GENERATOR_PATTERN =
        Pattern.compile(LOAD_RUNNER_VUSER_GENERATOR_REGEX);
  }
}
