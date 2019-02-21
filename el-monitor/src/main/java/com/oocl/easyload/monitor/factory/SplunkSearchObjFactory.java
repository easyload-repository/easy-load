package com.oocl.easyload.monitor.factory;

import com.oocl.easyload.model.entity.el_monitor.SplunkWsEvent;
import com.oocl.easyload.model.entity.el_monitor.SplunkWsException;

import java.util.Objects;

public enum SplunkSearchObjFactory {
  ws {
    @Override
    public SplunkWsEvent create() {
      return new SplunkWsEvent();
    }
  },
  exception {
    @Override
    public SplunkWsException create() {
      return new SplunkWsException();
    }
  };

  public abstract Object create();

  public static Object of(String type) {
    for (SplunkSearchObjFactory splunkSearchObjFactory : values()) {
      if (Objects.equals(splunkSearchObjFactory.name(), type)) {
        return splunkSearchObjFactory.create();
      }
    }
    return null;
  }

  public static Class classOf(String type) {
    for (SplunkSearchObjFactory splunkSearchObjFactory : values()) {
      if (Objects.equals(splunkSearchObjFactory.name(), type)) {
        return splunkSearchObjFactory.create().getClass();
      }
    }
    return null;
  }
}
