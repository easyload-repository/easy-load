package com.oocl.easyload.server.service.impl;

import com.oocl.easyload.model.entity.ELAttender;
import com.oocl.easyload.server.repository.ELAttenderRepository;
import com.oocl.easyload.server.service.ELAttenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ELAttenderServiceImpl implements ELAttenderService {
  @Autowired private ELAttenderRepository elAttenderRepository;

  @Override
  public List<ELAttender> getAll() {
    return elAttenderRepository.findAll();
  }
}
