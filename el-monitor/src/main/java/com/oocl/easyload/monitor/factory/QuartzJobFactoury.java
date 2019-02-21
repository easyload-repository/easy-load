package com.oocl.easyload.monitor.factory;

import com.oocl.easyload.monitor.jobs.WsEventsMonitorJob;
import com.oocl.easyload.monitor.jobs.WsExceptionsMonitorJob;
import org.quartz.Job;

public enum QuartzJobFactoury {
  ws {
    @Override
    public Class<WsEventsMonitorJob> getClz() {
      return WsEventsMonitorJob.class;
    }
  },
  exception {
    @Override
    public Class<WsExceptionsMonitorJob> getClz() {
      return WsExceptionsMonitorJob.class;
    }
  };

  public abstract Class<? extends Job> getClz();

  public static Class of(String type) {
    for (QuartzJobFactoury job : QuartzJobFactoury.values()) {
      if (job.name().equals(type)) {
        return job.getClz();
      }
    }
    return null;
  }
}
