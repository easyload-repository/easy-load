package com.oocl.easyload.server.controller;

import com.oocl.easyload.model.entity.ELAttender;
import com.oocl.easyload.server.dto.ELAttenderDTO;
import com.oocl.easyload.server.mapper.ELAttenderMapper;
import com.oocl.easyload.server.service.ELAttenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attender")
public class ELAttenderController {
  @Autowired private ELAttenderService elAttenderService;

  @GetMapping
  public List<ELAttenderDTO> getAllAttenderDTO() {
    List<ELAttender> elAttenders = elAttenderService.getAll();
    List<ELAttenderDTO> elAttenderDTOs =
        elAttenders.stream()
            .map(ELAttenderMapper.getInstance()::toELAttenderDTO)
            .collect(Collectors.toList());
    return elAttenderDTOs;
  }

  public ELAttenderService getElAttenderService() {
    return elAttenderService;
  }

  public void setElAttenderService(ELAttenderService elAttenderService) {
    this.elAttenderService = elAttenderService;
  }
}
