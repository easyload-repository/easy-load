package com.oocl.easyload.server.client;

import com.oocl.easyload.model.entity.ELScript;

import java.util.List;

public interface NodeClient {
  boolean isActivity(String host, int port);

  boolean initialFolder(String host, int port, String domain);

  List<ELScript> scanScript(String host, int port, String domain);

  void trailRun(String host, int port, List<ELScript> elScripts);

  void execute(String host, int port, int durationSecond, List<ELScript> elScripts);

  void stop(String host, int port, List<ELScript> elScripts);

  void stopAll(String host, int port);
}
