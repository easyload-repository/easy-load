package com.oocl.easyload.monitor.convert;

import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;

public class ObjectConvert {

  public static Object toObject(HashMap<String, String> map, Class<?> beanClass) throws Exception {
    if (map == null) return null;
    Object obj = beanClass.newInstance();
    BeanUtils.populate(obj, map);
    return obj;
  }
}
