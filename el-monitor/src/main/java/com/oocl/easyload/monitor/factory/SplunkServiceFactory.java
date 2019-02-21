package com.oocl.easyload.monitor.factory;

import com.oocl.easyload.monitor.splunk.PrdSplunkService;
import com.oocl.easyload.monitor.splunk.QaSplunkService;
import com.splunk.Service;

import java.util.Objects;

public enum SplunkServiceFactory {
  prod {
    @Override
    public Service create() {
      return PrdSplunkService.create();
    }
  },
  qa1 {
    @Override
    public Service create() {
      return QaSplunkService.create();
    }
  },
  qa2 {
    @Override
    public Service create() {
      return QaSplunkService.create();
    }
  },
  qa3 {
    @Override
    public Service create() {
      return QaSplunkService.create();
    }
  },
  qa4 {
    @Override
    public Service create() {
      return QaSplunkService.create();
    }
  };

  public abstract Service create();

  public static Service of(String type) {
    for (SplunkServiceFactory service : values()) {
      if (Objects.equals(service.name(), type)) {
        return service.create();
      }
    }
    return null;
  }
}
