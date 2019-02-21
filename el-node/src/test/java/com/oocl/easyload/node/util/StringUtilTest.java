package com.oocl.easyload.node.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

  @Test
  public void isEmpty() {
    assertTrue(StringUtil.isEmpty(null));
    assertTrue(StringUtil.isEmpty(""));
    assertFalse(StringUtil.isEmpty(" "));
  }

  @Test
  public void isNotEmpty() {
    assertFalse(StringUtil.isNotEmpty(null));
    assertFalse(StringUtil.isNotEmpty(""));
    assertTrue(StringUtil.isNotEmpty(" "));
  }

  @Test
  public void isBlank() {
    assertTrue(StringUtil.isBlank(null));
    assertTrue(StringUtil.isBlank(""));
    assertTrue(StringUtil.isBlank(" "));
    assertFalse(StringUtil.isBlank("String"));
  }

  @Test
  public void isNotBlank() {
    assertFalse(StringUtil.isNotBlank(null));
    assertFalse(StringUtil.isNotBlank(""));
    assertFalse(StringUtil.isNotBlank(" "));
    assertTrue(StringUtil.isNotBlank("String"));
  }

  @Test
  public void endWith() {
    assertTrue(StringUtil.endWith("String", "ing"));
  }

  @Test
  public void appendSuffixIfMissing() {
    assertEquals("String", StringUtil.appendSuffixIfMissing("String", "ing"));
    assertEquals("String", StringUtil.appendSuffixIfMissing("Str", "ing"));
  }

  @Test
  public void join() {
    assertEquals("String,String", StringUtil.join(new String[] {"String", "String"}, ","));
  }
}
