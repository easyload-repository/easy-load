package com.oocl.easyload.server.mapper;

import com.oocl.easyload.model.entity.ELActivity;
import com.oocl.easyload.model.entity.ELRound;
import com.oocl.easyload.model.entity.ELStatus;
import com.oocl.easyload.server.dto.ELActivityDTO;
import com.oocl.easyload.server.dto.ELActivityPageDTO;
import com.oocl.easyload.server.dto.ELRoundDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ELActivityMapper {
  private static ELActivityMapper elActivityMapper = new ELActivityMapper();

  public static ELActivityMapper getInstance() {
    return elActivityMapper;
  }

  public ELActivityDTO toDto(ELActivity elActivity) {
    if (elActivity == null) {
      return null;
    }
    ELActivityDTO elActivityDto = new ELActivityDTO();
    BeanUtils.copyProperties(elActivity, elActivityDto);
    elActivityDto.setRounds(mapperRounds(elActivity.getRounds()));
    return elActivityDto;
  }

  private List<ELRoundDTO> mapperRounds(List<ELRound> elRounds) {
    List<ELRoundDTO> elRoundDtos = new ArrayList<>();
    if (!CollectionUtils.isEmpty(elRounds)) {
      for (ELRound elRound : elRounds) {
        ELRoundDTO elRoundDto = ELRoundMapper.getInstance().toDto(elRound);
        elRoundDto.setActivityId(elRound.getElActivity().getActivityId());
        elRoundDto.setActivityName(elRound.getElActivity().getActivityName());
        elRoundDtos.add(elRoundDto);
      }
    }
    return elRoundDtos;
  }

  public List<ELActivityPageDTO> toDto(List<ELActivity> elActivities) {
    List<ELActivityPageDTO> elActivityPageDtos = new ArrayList<>();
    for (ELActivity elActivity : elActivities) {
      ELActivityPageDTO elActivityPageDto = new ELActivityPageDTO();
      BeanUtils.copyProperties(elActivity, elActivityPageDto);
      elActivityPageDto.setRoundCounts(elActivity.getRounds().size());
      if (!CollectionUtils.isEmpty(elActivity.getRounds())) {
        ELRoundDTO latestRound =
            ELRoundMapper.getInstance().toDto(getLatestRound(elActivity.getRounds()));
        elActivityPageDto.setLatestRound(latestRound);
      }
      elActivityPageDtos.add(elActivityPageDto);
    }
    return elActivityPageDtos;
  }

  public ELActivity toEntity(ELActivityDTO elActivityDto) {
    ELActivity elActivity = new ELActivity();
    BeanUtils.copyProperties(elActivityDto, elActivity);
    return elActivity;
  }

  private ELRound getLatestRound(List<ELRound> rounds) {
    List<ELRound> inProcessRounds =
        rounds.stream()
            .filter(round -> ELStatus.IN_PROCESS.equals(round.getStatus()))
            .collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(inProcessRounds)) {
      return inProcessRounds.get(0);
    }
    List<ELRound> newRounds =
        rounds.stream()
            .filter(round -> ELStatus.NEW.equals(round.getStatus()))
            .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(newRounds)) {
      return null;
    }
    Collections.sort(newRounds, Comparator.comparing(ELRound::getExpectedStartTime));
    return newRounds.get(newRounds.size() - 1);
  }
}
