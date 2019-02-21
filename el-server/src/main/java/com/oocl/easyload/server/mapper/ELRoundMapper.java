package com.oocl.easyload.server.mapper;

import com.oocl.easyload.model.entity.ELRound;
import com.oocl.easyload.server.dto.ELRoundDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ELRoundMapper {

  private static ELRoundMapper elRoundMapper = new ELRoundMapper();

  public static ELRoundMapper getInstance() {
    return elRoundMapper;
  }

  public ELRoundDTO toDto(ELRound elRound) {
    if (elRound == null) {
      return null;
    }
    ELRoundDTO elRoundDto = new ELRoundDTO();
    BeanUtils.copyProperties(elRound, elRoundDto);
    elRoundDto.setActivityId(elRound.getElActivity().getActivityId());
    elRoundDto.setActivityName(elRound.getElActivity().getActivityName());
    return elRoundDto;
  }

  public List<ELRoundDTO> toDto(List<ELRound> elRounds) {
    List<ELRoundDTO> elRoundDTOS = new ArrayList<>();
    for (ELRound elRound : elRounds) {
      elRoundDTOS.add(this.toDto(elRound));
    }
    return elRoundDTOS;
  }

  public ELRound toEntity(ELRoundDTO elRoundDto) {
    if (elRoundDto == null) {
      return null;
    }
    ELRound elRound = new ELRound();
    BeanUtils.copyProperties(elRoundDto, elRound);
    return elRound;
  }
}
