package com.oocl.easyload.node.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {

  String scriptId;
  NoticeStatus scriptStatus;
  String errorInfo;
}
