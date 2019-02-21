package com.oocl.easyload.server.controller;

import com.oocl.easyload.model.entity.ELRoundAttender;
import com.oocl.easyload.server.dto.ELRoundAttenderDTO;
import com.oocl.easyload.server.mapper.ELRoundAttenderMapper;
import com.oocl.easyload.server.service.ELRoundAttenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roundAttender")
public class ELRoundAttenderController {
  @Autowired private ELRoundAttenderService elRoundAttenderService;

  @GetMapping("/{elRoundId}")
  public List<ELRoundAttenderDTO> findByRoundId(@PathVariable String elRoundId) {
    List<ELRoundAttender> elRoundAttender = elRoundAttenderService.findByRoundId(elRoundId);
    return ELRoundAttenderMapper.getInstance().toDto(elRoundAttender);
  }
}
