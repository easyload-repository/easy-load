package com.oocl.easyload.node.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringUtil.applicationContext = applicationContext;
  }

  public static <T> T getBean(Class<T> clazz, String beanName) {
    return applicationContext.getBean(clazz, beanName);
  }
}
