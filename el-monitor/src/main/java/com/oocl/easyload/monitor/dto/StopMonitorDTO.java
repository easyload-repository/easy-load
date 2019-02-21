package com.oocl.easyload.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StopMonitorDTO {
  String activeId;
  String roundId;
  List<String> domains;
}
