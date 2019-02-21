package com.oocl.easyload.node.controller;

import com.oocl.easyload.node.dto.ScriptDto;
import com.oocl.easyload.node.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/node")
public class NodeActiveController {

  @GetMapping("/alive")
  public ResponseEntity isNodeAlive() {

    ScriptDto dto = new ScriptDto();
    dto.setScriptId("7");
    System.out.println("7777");
    return ResponseUtil.successInRestful(dto);
  }

  //    @GetMapping("/execute/jar/{name}")
  //    public ResponseEntity isNodeAlive12(@PathVariable("name") String name) throws Exception {
  //
  //        ScriptDto dto = new ScriptDto();
  //                String cmd ="java -jar D:\\ir4_projects\\el_node\\" + name + ".jar";
  //
  //        System.out.println(ResponseUtil.exeCmd2(cmd,"1").toString().trim());
  //
  //        System.out.println("7777");
  //        return ResponseUtil.successInRestful(dto);
  //    }
  //
  //    @GetMapping("/ping")
  //    public ResponseEntity isNodeAlive1() throws Exception {
  //
  //        ScriptDto dto = new ScriptDto();
  //
  //        String cmd = "ping www.baidu.com";
  //
  //        System.out.println(ResponseUtil.exeCmd2(cmd,"2").toString().trim());
  //
  //        return ResponseUtil.successInRestful(dto);
  //    }
  //
  //    @GetMapping("/kill/{id}")
  //    public ResponseEntity stop(@PathVariable("id") String id) throws Exception {
  //
  //
  //        ResponseUtil.WATCHDOG_MAP.get(id).destroyProcess();
  //        ResponseUtil.WATCHDOG_MAP.remove(id);
  //        ResponseUtil.OUT_MAP.remove(id);
  //
  //        ResponseUtil.ERROR_MAP.remove(id);
  //
  //
  //
  //        ScriptDto dto = new ScriptDto();
  //        dto.setScriptId("43");
  //
  //        return ResponseUtil.successInRestful(dto);
  //
  //    }
  //
  //    @GetMapping("/log/{id}")
  //    public ResponseEntity log(@PathVariable("id") String id) throws Exception {
  //
  //
  //        System.out.println(ResponseUtil.OUT_MAP.get(id).toString().trim());
  //        System.out.println("ddd");
  //        System.out.println(ResponseUtil.ERROR_MAP.get(id).toString().trim());
  //
  //
  //
  //                ScriptDto dto = new ScriptDto();
  //        dto.setScriptId("43");
  //
  //        return ResponseUtil.successInRestful(dto);
  //
  //    }

}
