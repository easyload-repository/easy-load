package com.oocl.easyload.server.mapper;

import com.oocl.easyload.model.entity.ELAttender;
import com.oocl.easyload.server.dto.ELAttenderDTO;

public class ELAttenderMapper {
  private static ELAttenderMapper elAttenderMapper = new ELAttenderMapper();

  public static ELAttenderMapper getInstance() {
    return elAttenderMapper;
  }

  public ELAttenderDTO toELAttenderDTO(ELAttender elAttender) {
    ELAttenderDTO dto = new ELAttenderDTO();
    dto.setId(elAttender.getAttenderId());
    dto.setDomain(elAttender.getDomain());
    dto.setOwner(elAttender.getOwner());
    dto.setCreateTime(elAttender.getCreateTime());
    dto.setUpdateTime(elAttender.getUpdateTime());
    dto.setCluster(elAttender.getCluster());
    return dto;
  }

  public ELAttender toELAttenderEntity(ELAttenderDTO elAttenderDTO) {
    ELAttender elAttender = new ELAttender();
    elAttender.setAttenderId(elAttenderDTO.getId());
    elAttender.setDomain(elAttenderDTO.getDomain());
    elAttender.setOwner(elAttenderDTO.getOwner());
    elAttender.setCreateTime(elAttenderDTO.getCreateTime());
    elAttender.setUpdateTime(elAttenderDTO.getUpdateTime());
    elAttender.setCluster(elAttenderDTO.getCluster());
    return elAttender;
  }
}
