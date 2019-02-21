package com.oocl.easyload.node.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayUtilTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void isEmpty() {
    Object[] objects = null;
    assertTrue(ArrayUtil.isEmpty(objects));
    objects = new Object[0];
    assertTrue(ArrayUtil.isEmpty(objects));
    objects = new Object[] {new Object()};
    assertFalse(ArrayUtil.isEmpty(objects));
  }

  @Test
  public void isNotEmpty() {
    Object[] objects = null;
    assertFalse(ArrayUtil.isNotEmpty(objects));
    objects = new Object[0];
    assertFalse(ArrayUtil.isNotEmpty(objects));
    objects = new Object[] {new Object()};
    assertTrue(ArrayUtil.isNotEmpty(objects));
  }
}
