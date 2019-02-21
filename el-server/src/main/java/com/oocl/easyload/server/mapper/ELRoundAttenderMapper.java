package com.oocl.easyload.server.mapper;

import com.oocl.easyload.model.entity.ELRoundAttender;
import com.oocl.easyload.server.dto.ELRoundAttenderDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ELRoundAttenderMapper {

  private static ELRoundAttenderMapper elRoundAttenderMapper = new ELRoundAttenderMapper();

  public static ELRoundAttenderMapper getInstance() {
    return elRoundAttenderMapper;
  }

  public ELRoundAttenderDTO toDto(ELRoundAttender elRoundAttender) {
    ELRoundAttenderDTO elRoundAttenderDTO = new ELRoundAttenderDTO();
    BeanUtils.copyProperties(elRoundAttender, elRoundAttenderDTO);
    elRoundAttenderDTO.setElAttenderDto(
        ELAttenderMapper.getInstance().toELAttenderDTO(elRoundAttender.getAttender()));
    elRoundAttenderDTO.setRoundId(elRoundAttender.getElRound().getRoundId());
    elRoundAttenderDTO.setActivityId(elRoundAttender.getElRound().getElActivity().getActivityId());
    return elRoundAttenderDTO;
  }

  public List<ELRoundAttenderDTO> toDto(List<ELRoundAttender> elRoundAttenders) {
    List<ELRoundAttenderDTO> elRoundAttenderDTOs = new ArrayList<>();
    for (ELRoundAttender elRoundAttender : elRoundAttenders) {
      elRoundAttenderDTOs.add(this.toDto(elRoundAttender));
    }
    return elRoundAttenderDTOs;
  }

  public ELRoundAttender toEntity(ELRoundAttenderDTO elRoundAttenderDto) {
    ELRoundAttender elRoundAttender = new ELRoundAttender();
    BeanUtils.copyProperties(elRoundAttenderDto, elRoundAttender);
    return elRoundAttender;
  }
}
