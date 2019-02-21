package com.oocl.easyload.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SplunkQueueLatestDto {
  private String activityId;
  private String roundId;
  private String domain;
  private boolean domainStatus;
  private boolean roundStatus;

  public SplunkQueueLatestDto() {}
}
