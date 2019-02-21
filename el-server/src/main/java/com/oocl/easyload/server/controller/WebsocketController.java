package com.oocl.easyload.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyload.server.websocket.DomainRunnerStatusWebsocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ws")
public class WebsocketController {

  @RequestMapping("/hello")
  public @ResponseBody String hello(@RequestParam String key, @RequestParam String value) {
    try {
      Map<String, String> map = new HashMap<>();
      map.put(key, value);
      DomainRunnerStatusWebsocket.sendInfo(new ObjectMapper().writeValueAsString(map), null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "success";
  }
}
